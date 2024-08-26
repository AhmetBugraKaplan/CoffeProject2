package com.example.coffeproject2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeproject2.R
import com.example.coffeproject2.adapters.CoffeCartAdapter
import com.example.coffeproject2.data.Coffees
import com.example.coffeproject2.databinding.FragmentCartBinding
import com.example.coffeproject2.viewModels.ViewModel


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var cartCoffeList : ArrayList<Coffees>
    private lateinit var cartAdapter: CoffeCartAdapter
    private lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)

        binding.recViewCart.setHasFixedSize(true)
        binding.recViewCart.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)


        viewModel = ViewModelProvider(requireActivity()).get(com.example.coffeproject2.viewModels.ViewModel::class.java)

        cartCoffeList = viewModel.CartCoffeList



        cartAdapter = CoffeCartAdapter(requireContext(),cartCoffeList){ c->
            cartCoffeList.remove(c)
            cartAdapter.notifyDataSetChanged()
        }

        binding.recViewCart.adapter = cartAdapter



        return binding.root
    }

}