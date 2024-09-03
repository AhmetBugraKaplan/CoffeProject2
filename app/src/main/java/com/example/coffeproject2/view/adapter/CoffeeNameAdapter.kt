package com.example.coffeproject2.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeproject2.R
import com.example.coffeproject2.data.CoffeeType
import com.example.coffeproject2.databinding.CardDesignCoffeNameBinding

class CoffeeNameAdapter(
    private val mContext: Context,
    private val listCoffeeType: ArrayList<CoffeeType>,
    val itemClickListener: (CoffeeType) -> Unit
) : RecyclerView.Adapter<CoffeeNameAdapter.CardViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class CardViewHolder(val binding: CardDesignCoffeNameBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardDesignCoffeNameBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCoffeeType.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val coffeeType = listCoffeeType[position]

        holder.binding.buttonCaffeType.text = coffeeType.coffeeType

        if (selectedPosition == position) {
            holder.binding.buttonCaffeType.setBackgroundColor(mContext.getColor(R.color.selected_button_color))
        } else {
            holder.binding.buttonCaffeType.setBackgroundColor(mContext.getColor(R.color.default_button_color))
        }

        holder.binding.buttonCaffeType.setOnClickListener {
            // Reset the color of the previously selected button
            notifyItemChanged(selectedPosition)
            // Update the selected position
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)

            itemClickListener(coffeeType)
        }
    }
}