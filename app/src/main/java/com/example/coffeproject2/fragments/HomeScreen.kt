package com.example.coffeproject2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeproject2.adapters.CoffeNameAdapter
import com.example.coffeproject2.data.CoffessType
import com.example.coffeproject2.databinding.FragmentHomeScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var adapter : CoffeNameAdapter
    private lateinit var coffeTypeList : ArrayList<CoffessType>


    //Firebase
    private lateinit var referanceCoffes:DatabaseReference
    private lateinit var referanceCoffesType:DatabaseReference



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater,container,false)

        coffeTypeList = ArrayList()


        binding.recViewCoffeName.setHasFixedSize(true)
        binding.recViewCoffeName.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        adapter = CoffeNameAdapter(requireContext(),coffeTypeList)

        binding.recViewCoffeName.adapter = adapter

        //Firebase

        val database = FirebaseDatabase.getInstance()
        referanceCoffes = database.getReference("coffees")
        referanceCoffesType = database.getReference("types")


        getAllTypes()

        return binding.root
    }


    private fun getAllTypes() {
        //***//
        referanceCoffesType.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                coffeTypeList.clear()
                for (c in snapshot.children) {
                    val coffeType = c.getValue(CoffessType::class.java)
                    if (coffeType != null) {
                        coffeTypeList.add(coffeType)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Hata yönetimi yapılabilir
            }
        })
    }
}