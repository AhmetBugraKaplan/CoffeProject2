package com.example.coffeproject2.adapters

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeproject2.R
import com.example.coffeproject2.data.Coffees
import org.w3c.dom.Text

class CoffeDetailsAdapter(val mContext : Context,
                            val coffeNameList:ArrayList<Coffees>)
    :RecyclerView.Adapter<CoffeDetailsAdapter.CardViewHolder>() {



    inner class CardViewHolder(view: View):RecyclerView.ViewHolder(view) {

        // imageView
        // CoffeName
        // CoffeDetail
        //addButton
        var imageView: ImageView
        var CoffeName: TextView
        var CoffeDetail: TextView
        var addButton: ImageButton

        init {
            imageView = view.findViewById(R.id.imageView)
            CoffeName = view.findViewById(R.id.CoffeName)
            CoffeDetail = view.findViewById(R.id.CoffeDetail)
            addButton = view.findViewById(R.id.addButton)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.card_design_coffe_details,parent,false)

        return CardViewHolder(view)
        }

    override fun getItemCount(): Int {
        return coffeNameList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
    val coffe = coffeNameList[position]
        holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        holder.CoffeDetail.text = "with milk"
        holder.CoffeName.text= coffe.CoffeName

        }

}

