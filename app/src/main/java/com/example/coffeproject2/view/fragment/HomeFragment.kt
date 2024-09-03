package com.example.coffeproject2.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coffeproject2.R
import com.example.coffeproject2.view.adapter.CoffeeDetailAdapter
import com.example.coffeproject2.view.adapter.CoffeeNameAdapter
import com.example.coffeproject2.data.Coffee
import com.example.coffeproject2.data.CoffeeType
import com.example.coffeproject2.databinding.FragmentHomeScreenBinding
import com.example.coffeproject2.view.viewmodel.CoffeeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var adapterCoffeeName: CoffeeNameAdapter
    private lateinit var adapterCoffeeDetail: CoffeeDetailAdapter
    private lateinit var coffeeTypeList: ArrayList<CoffeeType>
    private lateinit var coffeeList: ArrayList<Coffee>
    private lateinit var clickedType: String
    private lateinit var viewModel: CoffeeViewModel
    private lateinit var navController: NavController

    //Firebase
    private lateinit var referenceCoffee: DatabaseReference
    private lateinit var referenceCoffeeType: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.recViewCDetails.visibility = View.VISIBLE

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.visibility = View.VISIBLE

        viewModel = ViewModelProvider(requireActivity()).get(CoffeeViewModel::class.java)

        coffeeTypeList = ArrayList()
        coffeeList = ArrayList()

        //Kahve Türlerinin bulunduğu recview
        binding.recViewCoffeName.setHasFixedSize(true)
        binding.recViewCoffeName.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterCoffeeName = CoffeeNameAdapter(requireContext(), coffeeTypeList) { c ->
            clickedType = c.coffeeType.toString()
            getClickedCoffee()
        }

        binding.recViewCoffeName.adapter = adapterCoffeeName

        //Seçilen Kahve türüne göre filtreleme ile gelen kahve çeşitlerinin bulunduğu recView

        binding.recViewCDetails.setHasFixedSize(true)
        binding.recViewCDetails.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapterCoffeeDetail = CoffeeDetailAdapter(requireContext(), coffeeList,
            onImageButtonClick = { c ->
                viewModel.listCoffee.add(c)
                Toast.makeText(requireContext(), "${c.coffeeName} added to cart", Toast.LENGTH_SHORT).show()
                viewModel.totalPrice += c.coffeePrice!!
            },
            onImageViewClick = { c ->
                viewModel.selectedCoffee = c
                navController.navigate(R.id.homeToDetail)
            }
        )
        binding.recViewCDetails.adapter = adapterCoffeeDetail


        //Firebase
        val database = FirebaseDatabase.getInstance()
        referenceCoffee = database.getReference("coffees")
        referenceCoffeeType = database.getReference("types")

        binding.searchView.queryHint = "Search for coffee"

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) searchCoffee(query)
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

        return binding.root
    }

    // Search bar ile kahve araması yapma işlemi
    private fun searchCoffee(query: String) {
        referenceCoffee.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                coffeeList.clear()
                for (c in snapshot.children) {
                    val coffee = c.getValue(Coffee::class.java)
                    if (coffee != null && coffee.coffeeName?.contains(query, ignoreCase = true) == true) {
                        coffeeList.add(coffee)
                    }
                }
                adapterCoffeeDetail.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeScreen", error.message)
            }
        })
    }


    private fun getAllTypes() {
        referenceCoffeeType.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                coffeeTypeList.clear()
                for (c in snapshot.children) {
                    val coffeeType = c.getValue(CoffeeType::class.java)
                    if (coffeeType != null) coffeeTypeList.add(coffeeType)

                }
                adapterCoffeeName.notifyDataSetChanged()
                if (coffeeTypeList.isNotEmpty()) {
                    clickedType = coffeeTypeList[0].coffeeType.toString()
                    getClickedCoffee()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeScreen", error.message)
            }
        })
    }

    private fun getClickedCoffee() {
        referenceCoffee.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                coffeeList.clear()
                for (c in snapshot.children) {
                    val coffee = c.getValue(Coffee::class.java)
                    if (coffee != null && coffee.coffeeType.equals(clickedType, ignoreCase = true)) {
                        coffeeList.add(coffee)
                    }
                }
                adapterCoffeeDetail.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeScreen", error.message)
            }
        })
    }
}