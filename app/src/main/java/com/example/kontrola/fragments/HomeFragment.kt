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

class HomeFragment : Fragment(),MainRVAdapter.OnItemClickListener {

    private val viewModel: com.example.kontrola.viewmodel.ViewModel by activityViewModels()
    private lateinit var  menuHost: MenuHost
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

        viewModel.allError.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
        val fab = FragmentHomeBinding.bind(view).fab
        fab.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_addErrorFragment)
        }



        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMenu()
    }

    override fun onItemLongClick(item: Error1, position: Int) {
        counter++
        Log.d("longclick","setselected $counter")

        val holder = recyclerView.findViewHolderForAdapterPosition(position)
        val oldHolder = recyclerView.findViewHolderForAdapterPosition(lastLongClickSelection)
        lateinit var oldItemLayout: LinearLayout
        if (oldHolder != null) {
                oldItemLayout = (oldHolder as MainRVAdapter.ViewHolder).itemLayout
        }
        val itemLayout = (holder as MainRVAdapter.ViewHolder).itemLayout

        if (lastLongClickSelection == position) {
            Log.d("setvisibility","prva")

            setEditIconVisibility(false)
            setSelected(itemLayout,Color.TRANSPARENT)
            lastLongClickSelection = -1

        } else if ((lastLongClickSelection != position) && (lastLongClickSelection > -1)) {
            Log.d("setvisibility","druga")

            setEditIconVisibility(true)
            setSelected(itemLayout,Color.parseColor("#2596BE"))
            if (oldHolder != null) {
                setSelected(oldItemLayout,Color.TRANSPARENT,false)

            }
            lastLongClickSelection = position

        } else {
            Log.d("setvisibility","tretja")
            setEditIconVisibility(true)
            setSelected(itemLayout,Color.parseColor("#2596BE"))
            lastLongClickSelection = position

        }

    }
    private fun setEditIconVisibility(visible: Boolean) {
        Log.d("setvisibility","setvisibility $counter")
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //menuHost.removeMenuProvider(this)
                menu.findItem(R.id.editItem).isVisible = visible
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }


        },viewLifecycleOwner )
    }
    private fun setMenu() {
        menuHost = requireActivity()
        menuHost.invalidateMenu()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.findItem(R.id.addError).isVisible = false
                menu.findItem(R.id.update).isVisible = false
                menu.findItem(R.id.settings).isVisible = true
                menu.findItem(R.id.export).isVisible = true
                menu.findItem(R.id.editItem).isVisible = false
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        },viewLifecycleOwner )
    }
    private fun setSelected(layout: LinearLayout, color: Int,vibrate: Boolean = true) {
        layout.setBackgroundColor(color)
        if (vibrate) {
            context?.getSystemService(Vibrator::class.java)
                ?.vibrate(VibrationEffect.createOneShot(50, 1))
        }

    }


















    private fun populateDB() {
        val list = ArrayList<Error1>()

        var datum: String
        var posel: String
        var serijska: String
        var napaka: String
        var opomba: String

        val dodajnapako = Error1(

            null,
            datum = "21. 2. 2023",
            posel = "A11*8645",
            serijska = "845",
            napaka = "Vezava razvodnice",
            opomba = "Nevtralni vodnik od senzorja vezan na VS sponko"
        )



        list.add(dodajnapako)

        datum = "22. 2. 2023"
        posel = "A11*8645"
        serijska = "321"
        napaka = "Vezava razvodnice"
        opomba = "Nevtralni vodnik od senzorja vezan na VS sponko"

        list.add(Error1(null,datum,posel,serijska,napaka,opomba,false))


        datum = "23. 2. 2023"
        posel = "A11*4545"
        serijska = "844"
        napaka = "Vezava razvodnice"
        opomba = "Pozabljena brika"

        list.add(Error1(null,datum,posel,serijska,napaka,opomba,false))


        datum = "24. 2. 2023"
        posel = "A11*9845"
        serijska = "748"
        napaka = "CEE Priklop"
        opomba = "Nezategnjeni vijaki na dovodu"

        list.add(Error1(null,datum,posel,serijska,napaka,opomba,false))


        datum = "25. 2. 2023"
        posel = "A11*7445"
        serijska = "999"
        napaka = "Kabli"
        opomba = "Prektratki kabli za pregradno steno"

        list.add(Error1(null,datum,posel,serijska,napaka,opomba,false))


        datum = "26. 2. 2023"
        posel = "A11*5545"
        serijska = "478"
        napaka = "Razdelilec"
        opomba = "Napačne varovalke"

        list.add(Error1(null,datum,posel,serijska,napaka,opomba,false))


        viewModel.addError(list)

    }
}