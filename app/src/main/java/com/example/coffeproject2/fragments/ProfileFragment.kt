package com.example.coffeproject2.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coffeproject2.R
import com.example.coffeproject2.databinding.FragmentProfileBinding
import com.example.coffeproject2.viewModels.ViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var viewModel: ViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)


        binding.toolbar2.foregroundGravity = Gravity.CENTER_HORIZONTAL
        binding.toolbar2.setBackgroundColor(Color.parseColor("#9B5844"))




        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        binding.buttonConfirm.setOnClickListener {

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Profile")
            builder.setMessage("Are you sure you want to confirm the adress and number?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                viewModel.adress = binding.editTextAdress.text.toString()
                viewModel.PhoneNumber = binding.editTextNumber.text.toString()

                binding.textViewAdress.text = viewModel.adress
                binding.textViewTelNumber.text = viewModel.PhoneNumber

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