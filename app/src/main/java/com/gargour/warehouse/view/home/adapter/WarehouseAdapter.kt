package com.gargour.warehouse.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gargour.warehouse.databinding.LayoutWarehouseItemBinding
import com.gargour.warehouse.domain.model.Warehouse

class WarehouseAdapter(private val onClick: (Warehouse) -> Unit) :
    ListAdapter<Warehouse, WarehouseAdapter.WarehousesViewHolder>(WarehousesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarehousesViewHolder {
        return WarehousesViewHolder(
            LayoutWarehouseItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: WarehousesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object WarehousesDiffCallback : DiffUtil.ItemCallback<Warehouse>() {
        override fun areItemsTheSame(oldItem: Warehouse, newItem: Warehouse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Warehouse, newItem: Warehouse): Boolean {
            return oldItem.code == newItem.code && oldItem.name == newItem.name
        }


    }

    class WarehousesViewHolder(
        val binding: LayoutWarehouseItemBinding,
        val onClick: (Warehouse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentWarehouse: Warehouse? = null
        private val warehouseCode = binding.tvCode
        private val warehouseDescription = binding.tvDescription

        init {
            binding.root.setOnClickListener {
                currentWarehouse?.let {
                    onClick(it)
                }
            }

        }

        fun bind(warehouse: Warehouse) {
            currentWarehouse = warehouse
            warehouseCode.text = warehouse.code
            warehouseDescription.text = warehouse.name
        }

    }
}