package com.example.coffeproject2.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.coffeproject2.data.Coffee

class CoffeeViewModel : ViewModel() {
    var listCoffee = ArrayList<Coffee>()
    var totalPrice:Double = 0.0
    var selectedCoffee : Coffee = Coffee()
    var dataAddress : String? = null
    var dataPhoneNumber : String? = null
}