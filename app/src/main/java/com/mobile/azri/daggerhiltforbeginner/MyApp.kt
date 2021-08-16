package com.mobile.azri.daggerhiltforbeginner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//TODO 3 - Buat class extend Application dengan annotation @HiltAndroidApp
//Pada bagian ini akan generate Semua Component yang dibutuhkan pada setiap pembuatan depedency seperti
//AppComponent, ActivityComponent, FragmentComponent
@HiltAndroidApp
class MyApp : Application(){
}