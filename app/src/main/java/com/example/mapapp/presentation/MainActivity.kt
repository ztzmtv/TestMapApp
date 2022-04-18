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
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.R
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.domain.entity.Item
import com.example.mapapp.helper.AppTextWatcher
import com.example.mapapp.presentation.adapter.PanelItemAdapter


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MapAppViewModel::class.java] }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var panelRecyclerView: RecyclerView

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
            }
        }
    }

    private fun setupRecyclerView() {
        panelRecyclerView = binding.rvPanel
        val panelItemAdapter = PanelItemAdapter()
        with(panelItemAdapter) {
            onSwitchChangeListener = {
                viewModel.changeItem(it)
            }
            onDetailsClickListener = {
                log("$it")
                it.isExpanded = !it.isExpanded
            }
            onSliderTouchListener = {
                viewModel.changeItem(it)
            }
        }
        panelRecyclerView.adapter = panelItemAdapter
        viewModel.itemsList.observe(this) { itemsList ->
            panelItemAdapter.submitList(itemsList)
            setBottomSwitchValue(itemsList)
        }
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
