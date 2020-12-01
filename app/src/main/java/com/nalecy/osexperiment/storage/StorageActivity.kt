package com.nalecy.osexperiment.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nalecy.osexperiment.Constants
import com.nalecy.osexperiment.Constants.STORAGE_TYPE_BEST
import com.nalecy.osexperiment.Constants.STORAGE_TYPE_FIRST
import com.nalecy.osexperiment.R
import com.nalecy.osexperiment.databinding.ActivityStorageBinding
import com.nalecy.osexperiment.process.ProcessController

class StorageActivity : AppCompatActivity() {
    private var type: Int = -1
    private lateinit var binding: ActivityStorageBinding

    private val allocSpace = mutableListOf<Space>()
    private val allocAdapter = SpaceAdapter(R.layout.item_space, allocSpace)

    private val freeSpace = mutableListOf<Space>()
    private val freeAdapter = SpaceAdapter(R.layout.item_space, freeSpace)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = intent.getIntExtra("type", STORAGE_TYPE_FIRST)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage)
        binding.title.text = when (type) {
            STORAGE_TYPE_FIRST -> "首次适应算法"
            STORAGE_TYPE_BEST -> "最佳适应算法"
            else -> ""
        }
        binding.spaceAllocList.layoutManager = LinearLayoutManager(this)
        binding.spaceAllocList.adapter = allocAdapter


        binding.spaceFreeList.layoutManager = LinearLayoutManager(this)
        binding.spaceFreeList.adapter = freeAdapter

        val algorithm = when (type) {
            STORAGE_TYPE_FIRST -> AlgorithmFactory.getFirstFitAlgorithm()
            STORAGE_TYPE_BEST -> AlgorithmFactory.getBestFitAlgorithm()
            else -> throw IllegalArgumentException("type错误")
        }
        freeSpace.addAll(algorithm.getFreeSpaceList())
        notifyChange()
        binding.allocBtn.setOnClickListener {
            val size = binding.allocSizeEdt.text.toString().toLong()
            val alloc = algorithm.alloc(size)
            alloc?.let {
                allocSpace.add(it)
                freeSpace.clear()
                freeSpace.addAll(algorithm.getFreeSpaceList())
                notifyChange()
            } ?: run {
                Toast.makeText(this, "分配失败", Toast.LENGTH_SHORT).show()
            }
        }
        binding.freeBtn.setOnClickListener {
            val free: Space? = run {
                allocSpace.forEach {
                    if (binding.freeAddressEdt.text.toString().toLong() == it.startAddress) {
                        return@run it
                    }
                }
                null
            }
            free?.let {
                algorithm.free(free)
                allocSpace.remove(free)
                freeSpace.clear()
                freeSpace.addAll(algorithm.getFreeSpaceList())
                notifyChange()
            } ?: run {
                Toast.makeText(this, "找不到该空间", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun notifyChange() {
        allocAdapter.notifyDataSetChanged()
        freeAdapter.notifyDataSetChanged()
    }
}