package com.example.coffeproject2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeproject2.view.adapter.CoffeeCartAdapter
import com.example.coffeproject2.data.Coffee
import com.example.coffeproject2.databinding.FragmentCartBinding
import com.example.coffeproject2.view.viewmodel.CoffeeViewModel
import android.app.AlertDialog
import android.graphics.Color
import android.view.Gravity

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartCoffeeList: ArrayList<Coffee>
    private lateinit var cartAdapter: CoffeeCartAdapter
    private lateinit var viewModel: CoffeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CoffeeViewModel::class.java)

        binding.toolbar.foregroundGravity = Gravity.CENTER_HORIZONTAL
        binding.toolbar.setBackgroundColor(Color.parseColor("#9B5844"))

        binding.textViewCartTotalPrice.text = "${viewModel.totalPrice} $"


        if (viewModel.totalPrice > 10.0) {
            binding.textViewCartDeliveryFee.text = "Free"
        } else if (viewModel.totalPrice < 10.0 && viewModel.listCoffee.isNotEmpty()) {
            binding.textViewCartDeliveryFee.text = "1.99 $"
        } else {
            binding.textViewCartDeliveryFee.visibility = View.INVISIBLE
        }

        binding.textView4.text = viewModel.dataAddress

        binding.recViewCart.setHasFixedSize(true)
        binding.recViewCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        cartCoffeeList = viewModel.listCoffee

        cartAdapter = CoffeeCartAdapter(requireContext(), cartCoffeeList) { c ->
            cartCoffeeList.remove(c)
            cartAdapter.notifyDataSetChanged()

            viewModel.totalPrice -= c.coffeePrice!!

            binding.textViewCartTotalPrice.text = "${viewModel.totalPrice.toString()} $"

            if (viewModel.totalPrice < 10.0) {
                binding.textViewCartDeliveryFee.text = "1.99 $"
            }
            if (viewModel.listCoffee.isEmpty()) {
                binding.textViewCartDeliveryFee.visibility = View.INVISIBLE
            }
        }

        binding.recViewCart.adapter = cartAdapter


        binding.button.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Order")
            builder.setMessage("Are you sure you want to confirm the order?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                viewModel.listCoffee.clear()
                cartAdapter.notifyDataSetChanged()
                viewModel.totalPrice = 0.0
                binding.textViewCartTotalPrice.text = "0.0"
                binding.textViewCartDeliveryFee.visibility = View.INVISIBLE
                dialog.dismiss()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                // İptal butonuna basıldığında yapılacak işlemler
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        return binding.root
    }

}