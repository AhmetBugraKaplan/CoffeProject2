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
import android.app.AlertDialog
import android.graphics.Color


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var cartCoffeList : ArrayList<Coffees>
    private lateinit var cartAdapter: CoffeCartAdapter
    private lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(com.example.coffeproject2.viewModels.ViewModel::class.java)

        binding.toolbar.title = "Order"
        binding.toolbar.setBackgroundColor(Color.parseColor("#a6653f"))

        binding.textViewCartTotalPrice.text = viewModel.totalPrice.toString()

        if(viewModel.totalPrice > 10.0){
            binding.textViewCartDeliveryFee.text = "Free"
        }else if (viewModel.totalPrice < 10.0 && viewModel.CartCoffeList.isNotEmpty()){
            binding.textViewCartDeliveryFee.text = "1.99 $"
        }else{
            binding.textViewCartDeliveryFee.visibility = View.INVISIBLE
        }

        binding.recViewCart.setHasFixedSize(true)
        binding.recViewCart.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)


        cartCoffeList = viewModel.CartCoffeList

        cartAdapter = CoffeCartAdapter(requireContext(),cartCoffeList){ c->
            cartCoffeList.remove(c)
            cartAdapter.notifyDataSetChanged()

            viewModel.totalPrice -= c.CoffePrice!!

            binding.textViewCartTotalPrice.text = "${viewModel.totalPrice.toString()} $"

            if (viewModel.totalPrice < 10.0){
                binding.textViewCartDeliveryFee.text = "1.99 $"
            }
            if(viewModel.CartCoffeList.isEmpty()){
                binding.textViewCartDeliveryFee.visibility = View.INVISIBLE
            }
        }

        binding.recViewCart.adapter = cartAdapter


        binding.button.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Order")
            builder.setMessage("Are you sure you want to confirm the order?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                viewModel.CartCoffeList.clear()
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