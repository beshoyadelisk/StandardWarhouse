package com.gargour.warehouse.view.orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gargour.warehouse.databinding.LayoutOrderBinding
import com.gargour.warehouse.domain.model.Order

class OrdersAdapter(
    private var dataList: MutableList<Order>,
    private val myListener: OrderListener,
) : RecyclerView.Adapter<OrdersAdapter.OrdersAdapterVH>() {
    interface OrderListener {
        fun onItemClick(order: Order, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapterVH {
        return OrdersAdapterVH(
            LayoutOrderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OrdersAdapterVH, position: Int) {
        holder.setData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class OrdersAdapterVH internal constructor(
        private val binding: LayoutOrderBinding,
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            this.binding.root.setOnClickListener(this)
        }

        fun setData(order: Order) {
            binding.tvOrderCode.text = order.id.toString()
            binding.tvOrderDate.text = order.date.toString()
        }

        override fun onClick(v: View) {
            myListener.onItemClick(dataList[adapterPosition], adapterPosition)
        }


    }
}