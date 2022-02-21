package com.nerinos.flightlist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nerinos.flightlist.R
import com.nerinos.flightlist.databinding.FlightTypeItemBinding
import com.nerinos.flightlist.entities.ChooseTypeEntity
import com.nerinos.flightlist.entities.FlightTypeEntity
import com.nerinos.flightlist.utils.toCurrencyString
import com.nerinos.flightlist.utils.toTypeString

class PriceAdapter(
    val onTypeSelected: ((FlightTypeEntity) -> Unit)
) : RecyclerView.Adapter<PriceAdapter.FlightTypeViewHolder>() {

    private var lastCheckedPosition: Int = -1

    class FlightTypeViewHolder(val binding: FlightTypeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ChooseTypeEntity>() {
        override fun areItemsTheSame(
            oldItem: ChooseTypeEntity,
            newItem: ChooseTypeEntity
        ): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: ChooseTypeEntity,
            newItem: ChooseTypeEntity
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var types: List<ChooseTypeEntity>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightTypeViewHolder {
        val binding = FlightTypeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FlightTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(holder: FlightTypeViewHolder, position: Int) {
        val typeEntity = types[position]
        holder.apply {
            if (lastCheckedPosition == -1 && typeEntity.isChecked) {
                lastCheckedPosition = adapterPosition
            }
            binding.tvFlightType.text = typeEntity.type.toTypeString()
            binding.tvFlightTypePrice.text = itemView.resources.getString(
                R.string.flight_cost_template,
                typeEntity.price,
                typeEntity.currency.toCurrencyString()
            )
            binding.cbFlightType.isChecked = typeEntity.isChecked
            itemView.setOnClickListener {
                if (lastCheckedPosition > -1) {
                    types[lastCheckedPosition].isChecked = false
                    notifyItemChanged(lastCheckedPosition)
                }
                typeEntity.isChecked = true
                notifyItemChanged(adapterPosition)
                lastCheckedPosition = adapterPosition
                onTypeSelected(typeEntity.type)
            }
        }
    }
}