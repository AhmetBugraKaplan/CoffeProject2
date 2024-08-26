package com.example.coffeproject2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeproject2.R
import com.example.coffeproject2.data.Coffees

class CoffeCartAdapter(val mContext : Context,
                       val CoffeCartList:ArrayList<Coffees>,
                       val itemClickListener : (Coffees) -> Unit)
    :RecyclerView.Adapter<CoffeCartAdapter.CardViewHolder>(){

    inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var ImageViewCoffee : ImageView = view.findViewById(R.id.imageViewCoffe)
        var textViewType : TextView = view.findViewById(R.id.textViewCoffeType)
        var textViewName : TextView = view.findViewById(R.id.textViewCoffeName)
        var textViewPrice : TextView = view.findViewById(R.id.textViewPrice)
        var ImageButtonDelete : ImageButton = view.findViewById(R.id.imageButtonDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.card_design_coffe_cart,parent,false)

        return  CardViewHolder(view)
    }

    override fun getItemCount(): Int {
       return CoffeCartList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val coffee = CoffeCartList[position]

        holder.ImageViewCoffee.setImageResource(R.drawable.kahvee)
        holder.textViewType.text = coffee.CoffeType
        holder.textViewName.text = coffee.CoffeName
        holder.textViewPrice.text = coffee.CoffePrice.toString()

        holder.ImageButtonDelete.setOnClickListener {
            itemClickListener(coffee)
        }

    }


}