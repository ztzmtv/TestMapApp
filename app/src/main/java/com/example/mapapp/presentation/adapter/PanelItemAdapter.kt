package com.example.mapapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.R
import com.example.mapapp.databinding.PanelItemInvisibleBinding
import com.example.mapapp.databinding.PanelItemVisibleBinding
import com.example.mapapp.domain.entity.Item
import com.example.mapapp.presentation.MainActivity

class PanelItemAdapter(
    private val list: List<Item>,
    private val context: Context
) : RecyclerView.Adapter<PanelItemViewHolder>() {
    var onDetailsClickListener: ((position: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelItemViewHolder {
        val view = PanelItemVisibleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PanelItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PanelItemViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvPanelItem.text = list[position].text
        holder.binding.clInvisiblePart.visibility = if (item.isExpanded) {
            View.GONE
        } else {
            View.VISIBLE
        }
        holder.binding.ivArrowPopup.setOnClickListener {
            val expanded = item.isExpanded
            item.isExpanded = !(item.isExpanded)
            notifyItemChanged(position)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}