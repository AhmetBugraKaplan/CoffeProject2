package com.example.coffeproject2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeproject2.R
import com.example.coffeproject2.data.Coffees

class CoffeDetailsAdapterr(val mContext: Context,
                            val coffeList:ArrayList<Coffees>,
                            val itemClickListener: (Coffees) -> Unit)
    :RecyclerView.Adapter<CoffeDetailsAdapterr.CardViewHolder>(){

    inner class CardViewHolder(view:View):RecyclerView.ViewHolder(view){

        var imageView :ImageView
        var textViewCName:TextView
        var textViewCDetails:TextView
        var textViewCPrice:TextView
        var ImageButton:ImageButton

        init {
            imageView = view.findViewById(R.id.imageView)
            textViewCName= view.findViewById(R.id.textViewCName)
            textViewCDetails = view.findViewById(R.id.textViewCDetails)
            textViewCPrice = view.findViewById(R.id.textViewCPrice)
            ImageButton = view.findViewById(R.id.imageButton)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.card_design_coffe_designn,parent,false)

        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coffeList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val coffe = coffeList[position]

        holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        holder.textViewCName.text =coffe.CoffeName
        holder.textViewCDetails.text = coffe.CoffeDetails
        holder.textViewCPrice.text = coffe.CoffePrice.toString()

        holder.ImageButton.setOnClickListener {
            itemClickListener(coffe)
        }

    }




}