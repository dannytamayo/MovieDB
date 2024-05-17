package com.example.proyectoawm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Declaramos la instancia de los fragmentos que estaran en esa actividad
        var homeFragment = HomeFragment()
        var dashboardFragment = DashboardFragment()

        //Optenemos la barra de navegacion
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.nav_dashboard -> {
                    setCurrentFragment(dashboardFragment)
                    true
                }
                else -> false
            }
        }



    }



    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView, fragment)
            commit()
        }
    }
}