package com.mobile.azri.daggerhiltforbeginner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

//yang menggunakan depedency itu diberikan annotation @AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //ini dinamakan field injection
    @Inject
    lateinit var injectSomeClass: SomeClass

//    @Inject
//    lateinit var injectInterfaceImpl: SomeClassDepedency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(injectSomeClass.doAThing())
        println(injectSomeClass.doSomeOtherThing())

//        println(injectInterfaceImpl.doInterfaceImpl())
    }
}

@AndroidEntryPoint
class MyFragment : Fragment(){

    @Inject
    lateinit var someClass : SomeClass
}


//TODO 5 - Buat depedency agar dipanggil pada class lain (ex:MainActivity)
//ini dinamakan constructor injection
//TODO 6 - Tambahkan scope ActivityScope
//karena digunakan pada Activity, maka pada inject yang ada di MyFragment tidak terjadi error
//dikarenakan Tier Activity lebih tinggi dari Fragment, sehingga masih bisa digunakan pada Activity dan Fragment
@ActivityScoped
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

//Pembuatan depedency cukup menggunakan annotation @Inejct constructor pada class sebagai penyedia depdency (Provider)
@ActivityScoped
class SomeOtherClass @Inject constructor(){

    fun doSomeOtherThing(): String{
        return "Look I did Some Other Thing"
    }
}


/*Bagian ini menunjukkan issue pada Inject Constructor*/
//@ActivityScoped
//class SomeClassDepedency @Inject constructor(
//    private val someInterface: SomeInterface
//){
//    fun doInterfaceImpl(): String{
//        return someInterface.exampleInterface()
//    }
//}
//
////Ini tidak bisa diterapkan (akan error), karena interface ticdak bisa di implement di injection constructor,
///Mesti dibuatkan suatu module @Binds agar Interface sudah dinyatakan bisa di build menggunakan Hilt
//class SomeInterfaceImpl @Inject constructor() : SomeInterface{
//    override fun exampleInterface():String {
//        return "Interface implement"
//    }
//
//}
//
//interface SomeInterface{
//
//    fun exampleInterface():String
//}