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
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class CoffeCartAdapter(val mContext : Context,
                       val CoffeCartList:ArrayList<Coffees>,
                       val itemClickListener : (Coffees) -> Unit)
    :RecyclerView.Adapter<CoffeCartAdapter.CardViewHolder>(){

    var storage: FirebaseStorage = FirebaseStorage.getInstance()


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

        // Firebase Storage'dan resmin URL'sini al ve Picasso ile ImageView'e yükle
        val imageRef = storage.reference.child("images/${coffee.imageView}.jpg")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ref) // Yüklenirken gösterilecek resim
                .into(holder.ImageViewCoffee)
        }.addOnFailureListener {
            // Hata durumunda varsayılan bir resim göster
            holder.ImageViewCoffee.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.textViewType.text = coffee.CoffeType
        holder.textViewName.text = coffee.CoffeName
        holder.textViewPrice.text = coffee.CoffePrice.toString()

        holder.ImageButtonDelete.setOnClickListener {
            itemClickListener(coffee)
        }

    }


}