package com.example.coffeproject2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.coffeproject2.R
import com.example.coffeproject2.databinding.FragmentRegisterBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {
    private  lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        //Bottom bar invis yapma

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.visibility = View.GONE


        auth = FirebaseAuth.getInstance()
        navController = findNavController()

        binding.registerButton.setOnClickListener {

            val email =  binding.registerMail.text.toString()
            val password = binding.registerPassword.text.toString()

            signUp(email, password)

        }

        return binding.root
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate(R.id.registerToLogin)
                } else {
                    Toast.makeText(requireContext(), "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}