package com.example.coffeproject2.viewModels

import androidx.lifecycle.ViewModel
import com.example.coffeproject2.data.Coffees


class ViewModel : ViewModel() {

    var imageView:String = ""
    var CoffeName: String = ""
    var CoffeDetails:String = ""
    var CoffeType:String = ""
    var CoffePrice : Double = 0.0
    var CoffeId:Int = 0

    var CartCoffeList = ArrayList<Coffees>()

    var totalPrice:Double = 0.0

    var selectedCoffee : Coffees = Coffees()

    var adress : String = ""
    var PhoneNumber : String = ""

}