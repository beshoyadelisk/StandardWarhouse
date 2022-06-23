package com.gargour.warehouse.view.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gargour.warehouse.databinding.OrderDetailLayoutBinding
import com.gargour.warehouse.domain.model.OrderDetails

class ScanAdapter(private val onClick: (OrderDetails) -> Unit) :
    ListAdapter<OrderDetails, ScanAdapter.DetailsViewHolder>(DetailsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return DetailsViewHolder(
            OrderDetailLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    object DetailsDiffCallback : DiffUtil.ItemCallback<OrderDetails>() {
        override fun areItemsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
            return oldItem.itemCode == newItem.itemCode &&
                    oldItem.orderHeaderId == newItem.orderHeaderId &&
                    oldItem.qty == newItem.qty
        }

        override fun areContentsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
            return (oldItem.orderHeaderId == newItem.orderHeaderId) &&
                    (oldItem.itemCode == newItem.itemCode) &&
                    (oldItem.qty == newItem.qty)
        }


    }

    class DetailsViewHolder(
        binding: OrderDetailLayoutBinding,
        val onClick: (OrderDetails) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentOrderDetails: OrderDetails? = null
        private val itemCode = binding.tvItemCode
        private val itemDescription = binding.tvItemName
        private val itemQty = binding.tvQty

        init {
            binding.root.setOnClickListener {
                currentOrderDetails?.let {
                    onClick(it)
                }
            }
        }

        fun bind(orderDetails: OrderDetails) {
            currentOrderDetails = orderDetails
            itemCode.text = orderDetails.itemCode
            itemDescription.text = orderDetails.name
            itemQty.text = orderDetails.qty.toString()
        }

    }
}