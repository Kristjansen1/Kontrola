package com.example.kontrola.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kontrola.R
import com.example.kontrola.adapter.MainRVAdapter
import com.example.kontrola.databinding.FragmentHomeBinding
import com.example.kontrola.model.Error1
import com.example.kontrola.util.Poi
import com.example.kontrola.viewmodel.ViewModel

class HomeFragment : Fragment(),MainRVAdapter.OnItemClickListener {

    private val viewModel: ViewModel by activityViewModels()
    private lateinit var menuHost: MenuHost
    private lateinit var menuProvider: MenuProvider
    private lateinit var thisMenu: Menu
    private lateinit var recyclerView: RecyclerView
    private var lastLongClickSelection = -1
    var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        //populateDB()
        recyclerView = FragmentHomeBinding.bind(view).rvErrorLst
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))

        recyclerView.setHasFixedSize(true)
        val adapter = MainRVAdapter(this)
        recyclerView.adapter = adapter
        Log.d("applog last",lastLongClickSelection.toString())

        viewModel.allError.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        val fab = FragmentHomeBinding.bind(view).fab
        fab.setOnClickListener {
            viewModel.setItemAddEdit(null)
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_addErrorFragment)
        }

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMenu(view)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        menuHost.removeMenuProvider(menuProvider)
    }
    override fun onItemLongClick(item: Error1, position: Int) {

        val holder = recyclerView.findViewHolderForAdapterPosition(position)
        val oldHolder = recyclerView.findViewHolderForAdapterPosition(lastLongClickSelection)
        lateinit var oldItemLayout: LinearLayout

        if (oldHolder != null) {
                oldItemLayout = (oldHolder as MainRVAdapter.ViewHolder).itemLayout
        }
        val itemLayout = (holder as MainRVAdapter.ViewHolder).itemLayout

        if (lastLongClickSelection == position) {

            viewModel.setItemAddEdit(null)
            setEditIconVisibility(false)
            setSelected(itemLayout,Color.TRANSPARENT)
            lastLongClickSelection = -1

        } else if ((lastLongClickSelection != position) && (lastLongClickSelection > -1)) {

            viewModel.setItemAddEdit(item)
            setEditIconVisibility(true)
            setSelected(itemLayout,Color.parseColor("#2596BE"))

            if (oldHolder != null) {
                setSelected(oldItemLayout,Color.TRANSPARENT,false)

            }
            lastLongClickSelection = position

        } else {
            viewModel.setItemAddEdit(item)
            setEditIconVisibility(true)
            setSelected(itemLayout,Color.parseColor("#2596BE"))
            lastLongClickSelection = position

        }

    }
    private fun setEditIconVisibility(visibility: Boolean) {

        viewModel.setMenuEditItemIconVisibility(visibility)
        viewModel.setMenuDeleteIconVisibility(visibility)
        if (!visibility) {
            lastLongClickSelection = -1
        }
    }
    private fun setMenu(view: View) {
        menuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                thisMenu = menu
                menuProvider = this

                viewModel.setMenuUpdateIconVisibility(false)
                viewModel.setMenuAddIconVisibility(false)
                viewModel.setMenuSettingsIconVisibility(true)
                viewModel.setMenuDeleteIconVisibility(false)
                viewModel.setMenuExportIconVisibility(true)
                viewModel.setMenuEditItemIconVisibility(false)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.editItem -> {
                        Log.d("click","clikikii")
                        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addErrorFragment)
                        lastLongClickSelection = -1
                        true
                    }
                    R.id.deleteItem -> {
                        viewModel.deleteError(viewModel.itemAddEdit.value!!)
                        setEditIconVisibility(false)
                        true
                    }
                    R.id.settings -> {
                        //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_settingsFragment)
                        lastLongClickSelection = -1
                        true
                    }
                    R.id.export -> {
                        Poi.export(requireActivity().application)
                        //exportTable()
                        true
                    }
                    else -> return false
                }

            }

        },viewLifecycleOwner )
    }

    private fun exportTable(): Boolean {

        return true
    }

    private fun setSelected(layout: LinearLayout, color: Int,vibrate: Boolean = true) {
        layout.setBackgroundColor(color)
        if (vibrate) {
            context?.getSystemService(Vibrator::class.java)
                ?.vibrate(VibrationEffect.createOneShot(50, 1))
        }

    }


















/*    private fun populateDB() {
        val list = ArrayList<Error1>()
        var datum: String
        var posel: String
        var serijska: String
        var napaka: String
        var opomba: String


        for (i in 1..200) {
            val dodajnapako = Error1(
                null,
                datum = "$i. 2. 2023",
                posel = "A11*8645",
                serijska = "845",
                napaka = "Vezava razvodnice",
                opomba = "Nevtralni vodnik od senzorja vezan na VS sponko"
            )
            list.add(dodajnapako)

        }
        viewModel.addError(list)

    }*/
}