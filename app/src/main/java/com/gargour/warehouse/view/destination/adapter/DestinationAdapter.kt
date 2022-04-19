package com.gargour.warehouse.view.destination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gargour.warehouse.R
import com.gargour.warehouse.databinding.LayoutDestinationBinding
import com.gargour.warehouse.domain.model.Customer
import com.gargour.warehouse.domain.model.IDestination
import com.gargour.warehouse.domain.model.Supplier
import com.gargour.warehouse.domain.model.Warehouse

class DestinationAdapter(
    private var dataList: MutableList<IDestination>,
    private val myListener: DestinationListener,
) : RecyclerView.Adapter<DestinationAdapter.DestinationAdapterVH>() {
    interface DestinationListener {
        fun onItemClick(destination: IDestination, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationAdapterVH {
        return DestinationAdapterVH(
            LayoutDestinationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DestinationAdapterVH, position: Int) {
        holder.setData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class DestinationAdapterVH internal constructor(
        private val binding: LayoutDestinationBinding,
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            this.binding.root.setOnClickListener(this)
        }

        fun setData(destination: IDestination) {
            binding.tvDestinationCode.text = destination.code
            binding.tvDestinationName.text = destination.name
            val image =
                when (destination) {
                    is Customer -> {
                        ContextCompat.getDrawable(binding.root.context, R.drawable.person)
                    }
                    is Supplier -> {
                        ContextCompat.getDrawable(binding.root.context, R.drawable.person)
                    }
                    is Warehouse -> {
                        ContextCompat.getDrawable(binding.root.context, R.drawable.location)
                    }
                    else -> {
                        ContextCompat.getDrawable(binding.root.context, R.drawable.person)
                    }
                }
            binding.ivDestination.setImageDrawable(image)
        }

        override fun onClick(v: View) {
            myListener.onItemClick(dataList[adapterPosition], adapterPosition)
        }


    }
}