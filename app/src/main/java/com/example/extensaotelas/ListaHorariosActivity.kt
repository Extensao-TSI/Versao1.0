package com.example.extensaotelas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaHorariosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_horarios)

        recyclerView = findViewById(R.id.recyclerViewHorarios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HorarioAdapter(HorarioTemp.horarios)
        recyclerView.adapter = adapter

        val btnAdicionar = findViewById<Button>(R.id.btnAdicionarHorario)
        btnAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarHorarioActivity::class.java)
            startActivity(intent)
        }
    }

} 