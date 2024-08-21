package com.example.coffeproject2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeproject2.R
import com.example.coffeproject2.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater,container,false)



        return binding.root
    }

}