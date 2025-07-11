package com.example.extensaotelas

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class AdicionarHorarioActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_horario)

        val btnHorarioInicial = findViewById<Button>(R.id.btnHorarioInicial)
        val btnHorarioFinal = findViewById<Button>(R.id.btnHorarioFinal)
        val btnSalvarHorario = findViewById<Button>(R.id.btnSalvarHorario)

        btnHorarioInicial.setOnClickListener {
            mostrarTimePickerHorarioInicial()
        }

        btnHorarioFinal.setOnClickListener {
            mostrarTimePickerHorarioFinal()
        }

        // TODO
        btnSalvarHorario.setOnClickListener(){

        }


    }
    

    private fun mostrarTimePickerHorarioInicial() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Selecionar Horário Inicial")
            .build()
        

        picker.show(supportFragmentManager, "timePickerInicial")
    }
    

    private fun mostrarTimePickerHorarioFinal() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Selecionar Horário Final")
            .build()
        

        picker.show(supportFragmentManager, "timePickerFinal")
    }
} 