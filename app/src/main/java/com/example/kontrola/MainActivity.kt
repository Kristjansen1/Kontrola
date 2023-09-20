package com.example.kontrola

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.kontrola.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

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
        applog(exists.toString())

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
            val exists = File(filesDir,"tabela.xlsx").exists()
            if (!exists) {
                val outfile = File(filesDir,"/tabela.xlsx")
                val fileOutStream = FileOutputStream(outfile)
                assets.open("tabela.xlsx").copyTo(fileOutStream)
            }
            viewModel.writeDataStore("firstRun","false")
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val addIcon = Observer<Boolean> {
            menu.findItem(R.id.addError).isVisible = viewModel.menuAddIcon.value!!
        }

        val updateIcon = Observer<Boolean> {
            menu.findItem(R.id.update).isVisible = viewModel.menuUpdateIcon.value!!
        }

        val settingsIcon = Observer<Boolean> {
            menu.findItem(R.id.settings).isVisible = viewModel.menuSettingsIcon.value!!
        }
        val exportIcon = Observer<Boolean> {
            menu.findItem(R.id.export).isVisible = viewModel.menuExportIcon.value!!
        }

        val deleteIcon = Observer<Boolean> {
            menu.findItem(R.id.deleteItem).isVisible = viewModel.menuDeleteIcon.value!!
        }
        val editItemIcon = Observer<Boolean> {
            menu.findItem(R.id.editItem).isVisible = viewModel.menuEditItemIcon.value!!
        }
        viewModel.menuAddIcon.observe(this,addIcon)
        viewModel.menuUpdateIcon.observe(this,updateIcon)
        viewModel.menuSettingsIcon.observe(this,settingsIcon)
        viewModel.menuExportIcon.observe(this,exportIcon)
        viewModel.menuDeleteIcon.observe(this,deleteIcon)
        viewModel.menuEditItemIcon.observe(this,editItemIcon)


        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = navHostFragment.navController
        return when (item.itemId) {

            R.id.settings -> {
                navController.navigate(R.id.action_homeFragment_to_settingsFragment)
                true
            }

            else -> return false
        }
    }
}