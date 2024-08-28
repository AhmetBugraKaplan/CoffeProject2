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

class CoffeDetailsAdapterr(
    val mContext: Context,
    val coffeList: ArrayList<Coffees>,
    val itemClickListener: (Coffees) -> Unit
) : RecyclerView.Adapter<CoffeDetailsAdapterr.CardViewHolder>() {

    var storage: FirebaseStorage = FirebaseStorage.getInstance()

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageView: ImageView = view.findViewById(R.id.imageView)
        var textViewCName: TextView = view.findViewById(R.id.textViewCName)
       // var textViewCDetails: TextView = view.findViewById(R.id.textViewCDetails)
        var textViewCPrice: TextView = view.findViewById(R.id.textViewCPrice)
        var ImageButton: ImageButton = view.findViewById(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.card_design_coffe_designn, parent, false)

        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coffeList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val coffe = coffeList[position]

        // Firebase Storage'dan resmin URL'sini al ve Picasso ile ImageView'e yükle
        val imageRef = storage.reference.child("images/${coffe.imageView}.jpg")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground) // Yüklenirken gösterilecek resim
                .into(holder.imageView)
        }.addOnFailureListener {
            // Hata durumunda varsayılan bir resim göster
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.textViewCName.text = coffe.CoffeName
        holder.textViewCPrice.text = "${coffe.CoffePrice.toString()} $"

        holder.ImageButton.setOnClickListener {
            itemClickListener(coffe)
        }
    }
}
