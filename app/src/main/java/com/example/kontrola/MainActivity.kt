package com.example.kontrola

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.kontrola.databinding.ActivityMainBinding

fun applog(s: String) {
    Log.d("applog",s)
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val viewModel: ViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
   /*     menu.findItem(R.id.addError).isVisible = false
        menu.findItem(R.id.update).isVisible = false
        menu.findItem(R.id.settings).isVisible = true
        menu.findItem(R.id.export).isVisible = true*/
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val n = navHostFragment.navController
        return when (item.itemId) {

            R.id.settings -> {
                n.navigate(R.id.action_homeFragment_to_settingsFragment)
                true
            }
            R.id.editItem -> {
                Log.d("applog","edit clicked")
                return true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}