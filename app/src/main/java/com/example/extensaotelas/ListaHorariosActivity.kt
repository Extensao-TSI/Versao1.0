package com.example.extensaotelas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.extensaotelas.BancoDeDados.AppDatabase
import com.example.extensaotelas.BancoDeDados.Horario
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaHorariosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorarioAdapter
    private lateinit var db: AppDatabase
    private var horarios: MutableList<Horario> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_horarios)

        db = androidx.room.Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration().build()

        recyclerView = findViewById(R.id.recyclerViewHorarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnAdicionar = findViewById<Button>(R.id.btnAdicionarHorario)
        btnAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarHorarioActivity::class.java)
            startActivity(intent)
        }

        carregarHorarios()
    }

    private fun carregarHorarios() {
        val textViewSemHorariosCadastrados = findViewById<TextView>(R.id.textViewSemHorariosCadastrados)
        lifecycleScope.launch {
            val novosHorarios = withContext(Dispatchers.IO) {
                db.horarioDao().getAll().toMutableList()
            }
            horarios.clear()
            horarios.addAll(novosHorarios)
            if (!::adapter.isInitialized) {
                adapter = HorarioAdapter(horarios,
                    onEditar = { horario -> editarHorario(horario) },
                    onExcluir = { horario -> excluirHorario(horario) },
                    onToggle = { horario, ativo -> ativarDesativarHorario(horario, ativo) }
                )
                recyclerView.adapter = adapter
            } else {
                adapter.notifyDataSetChanged()
            }
            atualizarVisibilidadeLista(textViewSemHorariosCadastrados)
        }
    }

    override fun onResume() {
        super.onResume()
        carregarHorarios()
    }

    private fun atualizarVisibilidadeLista(textViewSemHorariosCadastrados: TextView) {
        if (horarios.isEmpty()) {
            textViewSemHorariosCadastrados.visibility = android.view.View.VISIBLE
            recyclerView.visibility = android.view.View.GONE
        } else {
            textViewSemHorariosCadastrados.visibility = android.view.View.GONE
            recyclerView.visibility = android.view.View.VISIBLE
        }
    }

    private fun editarHorario(horario: Horario) {
        // Primeiro TimePicker para o horário inicial
        val pickerInicial = com.google.android.material.timepicker.MaterialTimePicker.Builder()
            .setTimeFormat(com.google.android.material.timepicker.TimeFormat.CLOCK_24H)
            .setHour(horario.horaInicial)
            .setMinute(horario.minutosInicial)
            .setTitleText("Editar Horário Inicial")
            .build()
        pickerInicial.addOnPositiveButtonClickListener {
            val novaHoraInicial = pickerInicial.hour
            val novoMinutoInicial = pickerInicial.minute

            // Segundo TimePicker para o horário final
            val pickerFinal = com.google.android.material.timepicker.MaterialTimePicker.Builder()
                .setTimeFormat(com.google.android.material.timepicker.TimeFormat.CLOCK_24H)
                .setHour(horario.horaFinal)
                .setMinute(horario.minutosFinal)
                .setTitleText("Editar Horário Final")
                .build()
            pickerFinal.addOnPositiveButtonClickListener {
                val novaHoraFinal = pickerFinal.hour
                val novoMinutoFinal = pickerFinal.minute

                // Atualizar no banco e na lista
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        horario.horaInicial = novaHoraInicial
                        horario.minutosInicial = novoMinutoInicial
                        horario.horaFinal = novaHoraFinal
                        horario.minutosFinal = novoMinutoFinal
                        db.horarioDao().updateHorario(horario)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            pickerFinal.show(supportFragmentManager, "timePickerFinalEditDireto")
        }
        pickerInicial.show(supportFragmentManager, "timePickerInicialEditDireto")
    }

    private fun excluirHorario(horario: Horario) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.horarioDao().deleteHorario(horario)
            }
            horarios.remove(horario)
            adapter.notifyDataSetChanged()
            atualizarVisibilidadeLista(findViewById(R.id.textViewSemHorariosCadastrados))
        }
    }

    private fun ativarDesativarHorario(horario: Horario, ativo: Boolean) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.horarioDao().setAtivo(horario.id, ativo)
            }
            horario.ativo = ativo
            adapter.notifyDataSetChanged()
        }
    }
} 