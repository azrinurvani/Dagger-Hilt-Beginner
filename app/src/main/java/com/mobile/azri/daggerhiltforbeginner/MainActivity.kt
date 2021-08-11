package com.mobile.azri.daggerhiltforbeginner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //ini dinamakan field injection
    @Inject
    lateinit var injectSomeClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(injectSomeClass.doAThing())
        println(injectSomeClass.doSomeOtherThing())
    }
}

//ini dinamakan constructor injection
class SomeClass @Inject constructor(
    private val someOtherClass: SomeOtherClass
){

    fun doAThing(): String {
        return "Look I did A thing!"
    }

    fun doSomeOtherThing():String{
        return someOtherClass.doSomeOtherThing()
    }
}

class SomeOtherClass @Inject constructor(){

    fun doSomeOtherThing(): String{
        return "Look I did Some Other Thing"
    }
}