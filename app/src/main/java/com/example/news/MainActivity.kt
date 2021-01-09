package com.example.news

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Log.d(TAG, "onCreate: ")

        if(instance == null){
            instance = this;
        }


        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment? ?:return
        // assign nav controller with host
        val navController = host.navController

        //navController.navigate(R.id.SportsFragment)

        /*val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment, NewsFragment())*/
        
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting
    }

    companion object{


        private var instance: MainActivity? = null

        fun hasNetwork(): Boolean {
            return instance?.isNetworkConnected()!!
        }

    }

}