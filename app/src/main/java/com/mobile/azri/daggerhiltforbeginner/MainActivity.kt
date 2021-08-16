package com.mobile.azri.daggerhiltforbeginner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

//yang menggunakan depedency itu diberikan annotation @AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //ini dinamakan field injection
    @Inject
    lateinit var injectSomeClass: SomeClass

    @Inject
    lateinit var injectInterfaceImpl: SomeClassDepedency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(injectSomeClass.doAThing())
        println(injectSomeClass.doSomeOtherThing())

        println(injectInterfaceImpl.doInterfaceImpl())
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

//Pembuatan depedency cukup menggunakan annotation @Inject constructor pada class sebagai penyedia depdency
@ActivityScoped
class SomeOtherClass @Inject constructor(){

    fun doSomeOtherThing(): String{
        return "Look I did Some Other Thing"
    }
}


@ActivityScoped
class SomeClassDepedency @Inject constructor(
    private val someInterface: SomeInterface
){
    fun doInterfaceImpl(): String{
        return someInterface.exampleInterface()
    }
}

////Ini tidak bisa diterapkan (akan error), karena interface ticdak bisa di implement di injection constructor,
///Mesti dibuatkan suatu module @Binds agar Interface sudah dinyatakan bisa di build menggunakan Hilt
class SomeInterfaceImpl @Inject constructor(
    private val someDepedency:String
) : SomeInterface{
    override fun exampleInterface():String {
        return "Interface implement : $someDepedency"
    }

}

interface SomeInterface{

    fun exampleInterface():String
}

//TODO 7 Buat module untuk depedency, lebih diutamakan menggunakan @Provides daripada @Binds
//Module yang digunakan untuk Binds Inteface
//SingletonComponent maksudnya adalah ApplicationComponent, yang mana module dibuatkan dengan scope Singleton
// atau pada level Application, bisa diganti dengan scope ActivityComponent, dll, namun function atau depdency pada module
// mesti menyesuaikan
//@InstallIn(ActivityComponent::class)
//@Module
//abstract class MyModule{
//
//    @ActivityScoped
//    @Binds
//    abstract fun bindInterfaceDepedency(someInterfaceImpl: SomeInterfaceImpl):SomeInterface
//}

@InstallIn(ActivityComponent::class)
@Module
class MyModule{

    @ActivityScoped
    @Provides
    fun provideSomeString():String{
        return "some string"
    }

    @ActivityScoped
    @Provides
    fun provideInterfaceDepedency(
        someString:String
    ):SomeInterface{
        return SomeInterfaceImpl(someString)
    }

    @ActivityScoped
    @Provides
    fun providesGsonDepedency():Gson{
        return Gson()
    }

}