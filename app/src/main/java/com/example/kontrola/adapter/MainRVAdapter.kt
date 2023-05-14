package com.example.kontrola.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.kontrola.databinding.ErrorListBinding
import com.example.kontrola.model.Error1

class MainRVAdapter(private val listener: OnItemClickListener) :  RecyclerView.Adapter<MainRVAdapter.ViewHolder>() {
    private val allError = ArrayList<Error1>()
    private var map: HashMap<Int,Boolean> = HashMap<Int,Boolean>()
    private var lastLongClickPosition = -1

    interface OnItemClickListener {
        fun onItemLongClick(item: Error1,position: Int)
    }
    inner class ViewHolder(binding: ErrorListBinding): RecyclerView.ViewHolder(binding.root) {
        private val mainLayout = binding.mainLayout
        val expandedLayout = binding.expandedListLayout
        val itemLayout = binding.linearLayout
        var date = binding.date
        var type = binding.type
        var exported = binding.exported
        var posel = binding.posel
        var serial = binding.serial
        var note = binding.note

        init {
            itemLayout.setOnClickListener {
                if (map[adapterPosition] == true) {
                    map[adapterPosition] = false
                    expandedLayout.visibility = View.GONE
                } else {
                    map[adapterPosition] = true
                    expandedLayout.visibility = View.VISIBLE
                }
            }

/*            itemLayout.setOnTouchListener(object: OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        if (v != null) {
                            listener.onItemLongClick(allError[adapterPosition],adapterPosition)
                        }
                        return true
                    }
                    return false
                }
            })*/
            itemLayout.setOnLongClickListener {
                if (lastLongClickPosition == adapterPosition) {
                    lastLongClickPosition = -1
                } else {
                    lastLongClickPosition = adapterPosition
                }
                listener.onItemLongClick(allError[adapterPosition],adapterPosition)

                return@setOnLongClickListener true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ErrorListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return allError.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (lastLongClickPosition == position) {
            holder.itemLayout.setBackgroundColor(Color.parseColor("#2596BE"))

        } else {
            holder.itemLayout.setBackgroundColor(Color.TRANSPARENT)
        }

        if (map[position] == true) {
            holder.expandedLayout.visibility = View.VISIBLE
        } else {
            holder.expandedLayout.visibility = View.GONE
        }
        holder.date.text = allError[position].datum
        holder.type.text = allError[position].napaka
        holder.exported.text = allError[position].exported.toString()
        holder.posel.text = allError[position].posel
        holder.serial.text = allError[position].serijska
        holder.note.text = allError[position].opomba

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Error1>) {
        allError.clear()
        allError.addAll(newList)
        notifyDataSetChanged()
    }



}