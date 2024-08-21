package com.example.coffeproject2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeproject2.R
import com.example.coffeproject2.adapters.CoffeDetailsAdapter
import com.example.coffeproject2.adapters.CoffeNameAdapter
import com.example.coffeproject2.data.Coffees
import com.example.coffeproject2.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var adapter : CoffeNameAdapter
    private lateinit var adapterr: CoffeDetailsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater,container,false)


        val c1 = Coffees("asdas", "latte", 12.50)
        val c2 = Coffees("asdas", "latte", 12.50)
        val c3 = Coffees("asdas", "latte", 12.50)
        val c4 = Coffees("asdas", "latte", 12.50)
        val c5 = Coffees("asdas", "latte", 12.50)
        val c6 = Coffees("asdas", "latte", 12.50)
        val c7 = Coffees("asdas", "latte", 12.50)
        val c8 = Coffees("asdas", "latte", 12.50)
        val c9 = Coffees("asdas", "latte", 12.50)
        val c10 = Coffees("asdas", "latte", 12.50)


        var coffeList =  ArrayList<Coffees>()


        binding.recCoffeDetails.setHasFixedSize(true)
        binding.recViewCoffeName.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        adapterr = CoffeDetailsAdapter( requireContext(),coffeList)

        binding.recCoffeDetails.adapter = adapterr




        binding.recViewCoffeName.setHasFixedSize(true)
        binding.recViewCoffeName.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        coffeList.add(c1)
        coffeList.add(c2)
        coffeList.add(c3)
        coffeList.add(c4)
        coffeList.add(c4)
        coffeList.add(c5)
        coffeList.add(c6)
        coffeList.add(c7)
        coffeList.add(c8)
        coffeList.add(c9)
        coffeList.add(c10)


        adapter = CoffeNameAdapter(requireContext(),coffeList)

        binding.recViewCoffeName.adapter = adapter




        return binding.root
    }

}