package com.example.coffeproject2.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeproject2.R
import com.example.coffeproject2.data.Coffee
import com.example.coffeproject2.databinding.CardDesignCoffeDesignnBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class CoffeeDetailAdapter(
    private val mContext: Context,
    private val listCoffee: ArrayList<Coffee>,
    val onImageButtonClick: (Coffee) -> Unit,
    val onImageViewClick: (Coffee) -> Unit
) : RecyclerView.Adapter<CoffeeDetailAdapter.CardViewHolder>() {

    private var storage: FirebaseStorage = FirebaseStorage.getInstance()

    inner class CardViewHolder(val binding: CardDesignCoffeDesignnBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardDesignCoffeDesignnBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCoffee.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val coffee = listCoffee[position]

        // Firebase Storage'dan resmin URL'sini al ve Picasso ile ImageView'e yükle
        val imageRef = storage.reference.child("images/${coffee.coffeeImageUrl}.jpg")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ref) // Yüklenirken gösterilecek resim
                .into(holder.binding.imageView)
        }.addOnFailureListener {
            // Hata durumunda varsayılan bir resim göster
            holder.binding.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.binding.textViewCName.text = coffee.coffeeName
        holder.binding.textViewCPrice.text = "${coffee.coffeePrice.toString()} $"

        holder.binding.imageButton.setOnClickListener {
            onImageButtonClick(coffee)
        }

        holder.binding.imageView.setOnClickListener {
            onImageViewClick(coffee)
        }
    }
}
