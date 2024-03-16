package com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.database.schedule.Schedule
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.databinding.AirlineItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class AirLineAdapter(
    private val onItemClicked: (Schedule) -> Unit
) : ListAdapter<Schedule, AirLineAdapter.AirlineViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlineViewHolder {
        val viewHolder = AirlineViewHolder(
            AirlineItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AirlineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AirlineViewHolder(
        private var binding: AirlineItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(schedule: Schedule) {
            binding.stopNameTextView.text = schedule.airlineName
            binding.terminalTextView.text = schedule.terminalNumber
            binding.arrivalTimeTextView.text = SimpleDateFormat(
                "h:mm a").format(
                Date(schedule.arrivalTime.toLong() * 1000)
            )
        }
    }
}