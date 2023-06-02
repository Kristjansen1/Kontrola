package com.example.kontrola.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.kontrola.MainActivity
import com.example.kontrola.R
import com.example.kontrola.Util
import com.example.kontrola.databinding.FragmentAddErrorBinding
import com.example.kontrola.model.Error1
import com.example.kontrola.viewmodel.ViewModel
import java.time.LocalDate
import java.util.Calendar
import kotlin.properties.Delegates

class AddErrorFragment : Fragment() {

    var counter : Int = 0
    private val viewModel: ViewModel by activityViewModels()
    private var edit = false
    private lateinit var binding : FragmentAddErrorBinding
    private var idOfItem by Delegates.notNull<Int>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


    private lateinit var datum: TextView
    private lateinit var calendarImage : ImageView
    private lateinit var posel: TextView
    private lateinit var serijska: TextView
    private lateinit var spinnner: Spinner
    private lateinit var opomba: TextView
    private lateinit var viewHolder: View

    private lateinit var menuHost: MenuHost
    private lateinit var menuProvider: MenuProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentAddErrorBinding.inflate(inflater, container, false)


        datum = binding.txtDate
        calendarImage = binding.imageView2
        posel = binding.txtPosel
        serijska = binding.txtSerijska
        spinnner = binding.spinner
        opomba = binding.txtOpomba

        if (viewModel.itemAddEdit.value != null) {
            edit = true
            val actionBar = requireActivity() as MainActivity
            actionBar.supportActionBar?.title = "Edit error"
            idOfItem = viewModel.itemAddEdit.value!!.id!!
            datum.text = viewModel.itemAddEdit.value!!.datum
            posel.text = viewModel.itemAddEdit.value!!.posel
            serijska.text = viewModel.itemAddEdit.value!!.serijska
            spinnner.setSelection(viewModel.itemAddEdit.value!!.napaka!!)
            opomba.text = viewModel.itemAddEdit.value!!.opomba


        } else {
            datum.text = Util.myDateFormat(LocalDate.now())
        }

        calendarImage.setOnClickListener {
            pickDate()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //super.onViewCreated(view, savedInstanceState)

        viewHolder = view


        menuHost =  requireActivity()
        menuHost.invalidateMenu()
        menuHost.addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //menuInflater.inflate(R.menu.options_menu, menu)
                menuProvider = this

                menu.findItem(R.id.update).isVisible = edit
                menu.findItem(R.id.addError).isVisible = !edit
                menu.findItem(R.id.settings).isVisible = false
                menu.findItem(R.id.export).isVisible = false
                menu.findItem(R.id.editItem).isVisible = false
                menu.findItem(R.id.deleteItem).isVisible = false

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.addError -> {
                        //Log.d("adderror","clicked")
                        addErrorToDb("add")

                    }
                    R.id.update -> {
                        addErrorToDb("update")
                    }
                }
                return false
            }

            } )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("adderror","destroy")
        menuHost.removeMenuProvider(menuProvider)
    }

    private fun addErrorToDb(action: String) {
        counter++
        Log.d("adderror","adderror $counter")

        val error: Error1 = Error1(
            id = editOrNot(),
            datum = datum.text.toString(),
            posel = posel.text.toString(),
            serijska = serijska.text.toString(),
            napaka = spinnner.selectedItemPosition,
            opomba = opomba.text.toString()
        )
        when (action) {
            "add" ->  viewModel.addError(error)
            "update" -> viewModel.updateError(error)
        }
        try {
            val act = requireActivity() as MainActivity
            navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
            navController = navHostFragment.findNavController()
            navHostFragment.navController.navigate(R.id.action_addErrorFragment_to_homeFragment)
        }catch (e: java.lang.IllegalArgumentException) {
            e.printStackTrace()
        }






        /*  try {
              viewModel.addError(error)
              Navigation.findNavController(viewHolder).navigate(R.id.action_addErrorFragment_to_homeFragment)
          } catch (e: Exception) {
              e.printStackTrace()
          }*/

    }

    private fun editOrNot(): Int? {
        return if (!edit) {
            null
        } else {
            idOfItem
        }
    }

    private fun pickDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                val fixMonth = mMonth+1
                val date = "$mDay. $fixMonth. $mYear"
                datum.text = date
            },
            year,
            month,
            day
        ).show()
    }


}