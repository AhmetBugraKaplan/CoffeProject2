package com.example.coffeproject2.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeproject2.R
import com.example.coffeproject2.data.Coffee
import com.example.coffeproject2.databinding.CardDesignCoffeCartBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class CoffeeCartAdapter(
    private val mContext: Context,
    private val listCoffeeCart: ArrayList<Coffee>,
    val itemClickListener: (Coffee) -> Unit
) : RecyclerView.Adapter<CoffeeCartAdapter.CardViewHolder>() {

    private var storage: FirebaseStorage = FirebaseStorage.getInstance()

    inner class CardViewHolder(val binding: CardDesignCoffeCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardDesignCoffeCartBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCoffeeCart.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val coffee = listCoffeeCart[position]

        // Firebase Storage'dan resmin URL'sini al ve Picasso ile ImageView'e yükle
        val imageRef = storage.reference.child("images/${coffee.coffeeImageUrl}.jpg")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ref) // Yüklenirken gösterilecek resim
                .into(holder.binding.imageViewCoffe)
        }.addOnFailureListener {
            // Hata durumunda varsayılan bir resim göster
            holder.binding.imageViewCoffe.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.binding.textViewCoffeType.text = coffee.coffeeType
        holder.binding.textViewCoffeName.text = coffee.coffeeName
        holder.binding.textViewPrice.text = coffee.coffeePrice.toString()
        holder.binding.imageButtonDelete.setOnClickListener {
            itemClickListener(coffee)
        }
    }
}
