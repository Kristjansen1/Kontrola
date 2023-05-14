package com.example.kontrola.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import com.example.kontrola.R

class AddErrorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_error, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.invalidateMenu()

        menuHost.addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //menuInflater.inflate(R.menu.options_menu, menu)
                menu.findItem(R.id.update).isVisible = false
                menu.findItem(R.id.addError).isVisible = true
                menu.findItem(R.id.settings).isVisible = false
                menu.findItem(R.id.export).isVisible = false
                menu.findItem(R.id.editItem).isVisible = false

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.addError -> {
                        Log.d("applog","add clicked")
                        return true
                    }
                    R.id.update -> {
                        Log.d("applog","update clicked")
                        return true
                    }
                }
                return false
            }

            } )
    }


}