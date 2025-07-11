package com.example.extensaotelas


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.materialswitch.MaterialSwitch

class HorarioAdapter(val horarios: MutableList<String>) : RecyclerView.Adapter<HorarioAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHorario: TextView = itemView.findViewById(R.id.textViewHorario)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnExcluir: Button = itemView.findViewById(R.id.btnExcluir)
        val btnToggle: MaterialSwitch = itemView.findViewById(R.id.btnToggle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horario, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val horario = horarios[position]
        holder.textViewHorario.text = horario

        // TODO
        holder.btnEditar.setOnClickListener {
        }

        // TODO
        holder.btnExcluir.setOnClickListener {
        }

        // TODO
        //holder.btnToggle


    }

    override fun getItemCount(): Int {
        return horarios.size
    }
} 