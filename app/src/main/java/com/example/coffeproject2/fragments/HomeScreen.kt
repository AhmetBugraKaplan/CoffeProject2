package com.example.coffeproject2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coffeproject2.adapters.CoffeDetailsAdapterr
import com.example.coffeproject2.adapters.CoffeNameAdapter
import com.example.coffeproject2.data.Coffees
import com.example.coffeproject2.data.CoffessType
import com.example.coffeproject2.databinding.FragmentHomeScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var adapterCoffeName : CoffeNameAdapter
    private lateinit var adapterCDetails:CoffeDetailsAdapterr
    private lateinit var coffeTypeList : ArrayList<CoffessType>
    private lateinit var coffeList : ArrayList<Coffees>
    private lateinit var clickedType:String

    //Firebase
    private lateinit var referanceCoffes:DatabaseReference
    private lateinit var referanceCoffesType:DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        coffeTypeList = ArrayList()
        coffeList = ArrayList()

        //Kahve Türlerinin bulunduğu recview
        binding.recViewCoffeName.setHasFixedSize(true)
        binding.recViewCoffeName.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterCoffeName = CoffeNameAdapter(requireContext(), coffeTypeList) { c ->
            clickedType = c.type.toString()
            getAllCoffee()
        }

        binding.recViewCoffeName.adapter = adapterCoffeName

        //Seçilen Kahve türüne göre filtreleme ile gelen kahve çeşitlerinin bulunduğu recView

        binding.recViewCDetails.setHasFixedSize(true)
        binding.recViewCDetails.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )

        adapterCDetails = CoffeDetailsAdapterr(requireContext(), coffeList)

        binding.recViewCDetails.adapter = adapterCDetails


        //Firebase

        val database = FirebaseDatabase.getInstance()
        referanceCoffes = database.getReference("coffees")
        referanceCoffesType = database.getReference("types")


        getAllTypes()
        //getAllCoffee()

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
                adapterCoffeName.notifyDataSetChanged()

                if (coffeTypeList.isNotEmpty()) {
                    clickedType = coffeTypeList[0].type.toString()
                    getAllCoffee()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Hata yönetimi yapılabilir
            }
        })
    }

    private fun getAllCoffee() {
        //***//

        referanceCoffes.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                coffeList.clear()
                for (c in snapshot.children) {
                    val coffee = c.getValue(Coffees::class.java)
                    if (coffee != null && coffee.CoffeType == clickedType) {
                        coffeList.add(coffee)
                        println(coffee.CoffeName)
                    }
                }
                adapterCDetails.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Hata yönetimi yapılabilir
            }
        })
    }

}