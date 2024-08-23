package com.example.coffeproject2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeproject2.R
import com.example.coffeproject2.data.CoffessType

class CoffeNameAdapter(val mContext: Context,
                        val coffeNameList:ArrayList<CoffessType>,
                        val itemClickListener:(CoffessType) -> Unit)
    :RecyclerView.Adapter<CoffeNameAdapter.CardViewHolder>(){

    inner class CardViewHolder(view: View):RecyclerView.ViewHolder(view){


        var button : Button


        init {

            button = view.findViewById(R.id.buttonCaffeType)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.card_design_coffe_name,parent,false)

        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coffeNameList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val coffe = coffeNameList[position]

        holder.button.text = coffe.type
        holder.button.setOnClickListener {
            itemClickListener(coffe)
        }


    }


}