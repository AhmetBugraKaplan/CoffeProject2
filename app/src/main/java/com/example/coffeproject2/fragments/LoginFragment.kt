package com.example.coffeproject2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.coffeproject2.R
import com.example.coffeproject2.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        //Bottom bar invis yapma

       val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
       bottomNavigationView.visibility = View.GONE



        auth = FirebaseAuth.getInstance()
        navController = findNavController()

        binding.signInButton.setOnClickListener {
            val email = binding.mailText.text.toString()
            val password = binding.PasswordText.text.toString()

            signIn(email,password)
        }


        binding.signUpButton.setOnClickListener {
            Log.e("hata","girdik")
            navController.navigate(R.id.LoginToRegister)
        }



        return binding.root
    }


    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate(R.id.LogInToHome)
                } else {
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }



    }


}