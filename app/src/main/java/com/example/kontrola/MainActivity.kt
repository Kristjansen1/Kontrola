package com.example.kontrola

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.kontrola.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import java.io.File

fun applog(s: String) {
    Log.d("applog",s)
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val viewModel: com.example.kontrola.viewmodel.ViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(navController)
        //val file = getExternalFilesDir("")
        //val file1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        //applog(file1.toString())

        //val a = assets
        //val outfile = File(filesDir,"/tabela.xlsx")
        //val foutstream = FileOutputStream(outfile)

        val exists = File(filesDir,"tabela.xlsx").exists()
        //applog(exists.toString())

        checkForFirstRun()


        //val b = assets.open("tabela.xlsx").copyTo(foutstream)

        
     /*   val fl = a.list("")
        val n = fl?.size
        applog(n.toString())
        if (fl != null) {
            for (x in fl) {

            }
        }*/


    }


    private fun checkForFirstRun() {
        val firsRun = viewModel.readDataStore("firstRun")

        if (firsRun == "false") {
            applog("first run false")
        } else {
            applog("first run true,...writing")
            viewModel.writeDataStore("firstRun","false")
        }

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
/*    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val n = navHostFragment.navController
        return when (item.itemId) {

            R.id.settings -> {
                n.navigate(R.id.action_homeFragment_to_settingsFragment)
                true
            }
            else -> return false
        }
    }*/
}