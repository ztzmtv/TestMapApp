package com.example.mapapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.R
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.domain.entity.Item
import com.example.mapapp.helper.AppTextWatcher
import com.example.mapapp.helper.Mapper
import com.example.mapapp.presentation.adapter.BindingPanelItem
import com.example.mapapp.presentation.adapter.PanelItemAdapter
import com.mikepenz.fastadapter.FastAdapter


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MapAppViewModel::class.java] }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var panelRecyclerView: RecyclerView
    private val panelItemAdapter by lazy { PanelItemAdapter() }
    private var isDragMode = true
    //private val mapper by lazy { Mapper() }
    //private lateinit var fastAdapter: FastAdapter<BindingPanelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDrawerLayout()
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomPanelListeners()
        setFabClickListener()
        setEtFindTextListener()

    }

    private fun setEtFindTextListener() {
        binding.etFindText.addTextChangedListener(
            AppTextWatcher {
                val string = binding.etFindText.text.toString()
                viewModel.findItems(string)
            }
        )
    }

    private fun setFabClickListener() {
        binding.fab.setOnClickListener {
            val itemCount = panelRecyclerView.adapter?.itemCount ?: 1
            panelRecyclerView.smoothScrollToPosition(itemCount)
        }
    }

    private fun setupBottomPanelListeners() {
        with(binding) {
            includeLayout.btnAddItem.setOnClickListener {
                viewModel.addDefaultItem()
                panelItemAdapter.notifyItemInserted(panelItemAdapter.currentList.size)
            }
            includeLayout.btnFindItem.setOnClickListener {
                if (tilFindPanel.visibility == View.GONE) {
                    tilFindPanel.visibility = View.VISIBLE
                } else {
                    tilFindPanel.visibility = View.GONE
                }
            }
            includeLayout.btnDelete.setOnClickListener {
                viewModel.deleteLastItem()
                panelItemAdapter.notifyItemRemoved(panelItemAdapter.currentList.size)
            }
            includeLayout.btnDragItem.setOnClickListener {
                isDragMode = !isDragMode
                viewModel.getSortedList(isDragMode)
            }
        }
    }

    private fun setupRecyclerView() {
        panelRecyclerView = binding.rvPanel
        with(panelItemAdapter) {
            val callback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPos = viewHolder.absoluteAdapterPosition
                    val toPos = target.absoluteAdapterPosition
                    log("$fromPos $toPos")
                    viewModel.moveItem(fromPos, toPos)
                    notifyItemMoved(fromPos, toPos)
                    log("$currentList")
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = currentList[viewHolder.absoluteAdapterPosition]
                    viewModel.deleteItem(item)
                    notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                }

                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    val dragFlags: Int
                    val swipeFlags: Int
                    if (isDragMode) {
                        dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                        swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    } else {
                        dragFlags = 0
                        swipeFlags = 0
                    }
                    return makeMovementFlags(dragFlags, swipeFlags)
                }
            }
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(panelRecyclerView)

            onSwitchChangeListener = {
                viewModel.changeItem(it)
            }
            onDetailsClickListener = {

            }
            onSliderTouchListener = {
                viewModel.changeItem(it)
            }
        }
        panelRecyclerView.adapter = panelItemAdapter

        panelItemAdapter.submitList(
            viewModel.itemsList
        )

//        val itemAdapter = ItemAdapter<BindingPanelItem>()
//        fastAdapter = FastAdapter.with(itemAdapter)
//        panelRecyclerView.adapter = fastAdapter
        //panelRecyclerView.adapter = panelItemAdapter

//        viewModel.itemsListLiveData.observe(this) {
//            val list = mapper.mapItemToBindingPanelItem(it)
//            itemAdapter.set(list)
//            FastAdapterDiffUtil[itemAdapter] = list
//            panelItemAdapter.submitList(null)
//            panelItemAdapter.submitList(itemsList.toList())
//            setBottomSwitchValue(itemsList)

//        }
    }

    private fun setBottomSwitchValue(itemsList: List<Item>) {
        val isCheckedList = mutableListOf<Boolean>()
        for (item in itemsList) {
            isCheckedList.add(item.isChecked)
        }
        val isCheckedItems = when {
            isCheckedList.all { it } -> CHECKED_ALL_TRUE
            isCheckedList.none { it } -> CHECKED_ALL_FALSE
            else -> CHECKED_ALL_DIFF
        }
        binding.includeLayout.swPanelBottom.isChecked = when (isCheckedItems) {
            CHECKED_ALL_TRUE -> true
            CHECKED_ALL_FALSE -> false
            else -> false //TODO("Придумать промежуточный вариант на switch")
        }
    }

    private fun setupDrawerLayout() {
        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "MainActivity_TAG"
        private const val CHECKED_ALL_TRUE = 1
        private const val CHECKED_ALL_FALSE = -1
        private const val CHECKED_ALL_DIFF = 0

        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}
