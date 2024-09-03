package com.example.coffeproject2.view.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.coffeproject2.databinding.FragmentProfileBinding
import com.example.coffeproject2.view.viewmodel.CoffeeViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: CoffeeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.toolbar2.foregroundGravity = Gravity.CENTER_HORIZONTAL
        binding.toolbar2.setBackgroundColor(Color.parseColor("#9B5844"))

        viewModel = ViewModelProvider(requireActivity()).get(CoffeeViewModel::class.java)

        binding.buttonConfirm.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Profile")
            builder.setMessage("Are you sure you want to confirm the adress and number?")
            builder.setPositiveButton("Yes") { dialog, _ ->
                viewModel.dataAddress = binding.editTextAdress.text.toString()
                viewModel.dataPhoneNumber = binding.editTextNumber.text.toString()
                binding.textViewAdress.text = viewModel.dataAddress
                binding.textViewTelNumber.text = viewModel.dataPhoneNumber

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