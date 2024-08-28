package com.example.coffeproject2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coffeproject2.adapters.CoffeDetailsAdapterr
import com.example.coffeproject2.adapters.CoffeNameAdapter
import com.example.coffeproject2.data.Coffees
import com.example.coffeproject2.data.CoffessType
import com.example.coffeproject2.databinding.FragmentHomeScreenBinding
import com.example.coffeproject2.viewModels.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var adapterCoffeName: CoffeNameAdapter
    private lateinit var adapterCDetails: CoffeDetailsAdapterr
    private lateinit var coffeTypeList: ArrayList<CoffessType>
    private lateinit var coffeList: ArrayList<Coffees>
    private lateinit var clickedType: String
    private lateinit var viewModel: ViewModel

    //Firebase
    private lateinit var referanceCoffes: DatabaseReference
    private lateinit var referanceCoffesType: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.recViewCDetails.visibility = View.VISIBLE


        binding.searchView

        coffeTypeList = ArrayList()
        coffeList = ArrayList()

        //Kahve Türlerinin bulunduğu recview
        binding.recViewCoffeName.setHasFixedSize(true)
        binding.recViewCoffeName.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterCoffeName = CoffeNameAdapter(requireContext(), coffeTypeList) { c ->
            clickedType = c.type.toString()
            getClickedCoffee()
        }

        binding.recViewCoffeName.adapter = adapterCoffeName

        //Seçilen Kahve türüne göre filtreleme ile gelen kahve çeşitlerinin bulunduğu recView

        binding.recViewCDetails.setHasFixedSize(true)
        binding.recViewCDetails.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )

        adapterCDetails = CoffeDetailsAdapterr(requireContext(), coffeList){c->
            viewModel = ViewModelProvider(requireActivity()).get(com.example.coffeproject2.viewModels.ViewModel::class.java)
            viewModel.CartCoffeList.add(c)

            Toast.makeText(requireContext(),"${c.CoffeName} added to cart",Toast.LENGTH_SHORT).show()

            viewModel.totalPrice += c.CoffePrice!!
        }

        binding.recViewCDetails.adapter = adapterCDetails


        //Firebase

        val database = FirebaseDatabase.getInstance()
        referanceCoffes = database.getReference("coffees")
        referanceCoffesType = database.getReference("types")

        binding.searchView.queryHint = "Search for coffee"

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    searchCoffee(query)
                }


                return true
            }
            // onQueryTextChange içinde
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    binding.searchView.queryHint = "Search for coffee"
                    binding.recViewCoffeName.visibility = View.VISIBLE
                    getClickedCoffee() // Arama bittiğinde eski verileri getir
                } else {
                    binding.recViewCoffeName.visibility = View.GONE
                    searchCoffee(newText)
                }
                return true
            }

        })


        getAllTypes()
        //getAllCoffee()

        return binding.root
    }

    // Search bar ile kahve araması yapma işlemi
    private fun searchCoffee(query: String) {
        referanceCoffes.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                coffeList.clear()
                for (c in snapshot.children) {
                    val coffee = c.getValue(Coffees::class.java)
                    if (coffee != null &&
                        coffee.CoffeName!!.contains(query, ignoreCase = true)
                    ) {
                        coffeList.add(coffee)
                    }
                }
                adapterCDetails.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata yönetimi yapılabilir
            }
        })
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
                    getClickedCoffee()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Hata yönetimi yapılabilir
            }
        })
    }

    private fun getClickedCoffee() {
        //***//

        referanceCoffes.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                coffeList.clear()
                for (c in snapshot.children) {
                    val coffee = c.getValue(Coffees::class.java)
                    if (coffee != null && coffee.CoffeType == clickedType) {
                        coffeList.add(coffee)
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