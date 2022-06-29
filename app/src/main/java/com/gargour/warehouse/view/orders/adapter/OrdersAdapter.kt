package com.gargour.warehouse.view.orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gargour.warehouse.R
import com.gargour.warehouse.databinding.LayoutOrderBinding
import com.gargour.warehouse.domain.model.OrderHeader

class OrdersAdapter(
    private var dataList: MutableList<OrderHeader>,
    private val myListener: OrderListener,
) : RecyclerView.Adapter<OrdersAdapter.OrdersAdapterVH>() {
    interface OrderListener {
        fun onItemClick(orderHeader: OrderHeader, position: Int, view: View)
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
            this.binding.orderLayout.setOnClickListener(this)
            this.binding.ivDelete.setOnClickListener(this)
        }

        fun setData(orderHeader: OrderHeader) {
            binding.tvOrderCode.text =
                binding.root.context.getString(R.string.orderNumb, orderHeader.id)
            binding.tvOrderDate.text = orderHeader.date
            binding.tvDestinationName.text = orderHeader.typeId
        }

        override fun onClick(v: View) {
            myListener.onItemClick(dataList[adapterPosition], adapterPosition, v)
        }


    }
}