package com.app.simplecalendar.calendar

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.simplecalendar.R
import com.app.simplecalendar.model.Event
import com.app.simplecalendar.viewmodel.EventViewModel

class Radapter(private val viewModel: EventViewModel): RecyclerView.Adapter<Radapter.MyViewHolder>(){

    private var eventList=emptyList<Event>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):MyViewHolder{
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_layout,parent,false))
    }

    override fun getItemCount():Int{
        return eventList.size
    }

    override fun onBindViewHolder(holder:MyViewHolder,position:Int){
        val currentItem=eventList[position]

        holder.itemView.findViewById<TextView>(R.id.exame_name_txt).text ="Event Name : ${currentItem.name}"
        holder.itemView.findViewById<TextView>(R.id.event_date_txt).text="Event Date : ${currentItem.stamp}"
        holder.itemView.findViewById<TextView>(R.id.event_time_txt).text="Event Time : ${currentItem.time}"

// when touching a row it will send me to the update fragment

        holder.itemView.findViewById<ImageView>(R.id.imageView2).setOnClickListener {



            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setPositiveButton("Confirm") { _, _ ->
                viewModel.deleteInfo(currentItem)
                notifyItemRemoved(position)
                Toast.makeText(
                    holder.itemView.context,
                    "successfully deleted ",
                    Toast.LENGTH_SHORT
                ).show()

            }
            builder.setNegativeButton("cancel") { _, _ -> }


            builder.setMessage("Do you want to delete the event ")

            builder.create().show()

        }

    }

    fun setData(event:List<Event>){
        this.eventList=event
        notifyDataSetChanged()
    }
}