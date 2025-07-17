package com.example.extensaotelas

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.example.extensaotelas.BancoDeDados.AppDatabase
import com.example.extensaotelas.BancoDeDados.Horario
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.app.DatePickerDialog
import java.util.Calendar
import java.util.TimeZone

class AdicionarHorarioActivity : AppCompatActivity() {
    
    private lateinit var db: AppDatabase
    private var horaInicial: Int = 0
    private var minutosInicial: Int = 0
    private var horaFinal: Int = 0
    private var minutosFinal: Int = 0
    private var ano: Int = 0
    private var mes: Int = 0
    private var dia: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_horario)

        // Inicializar com a hora atual do sistema
        val agora = Calendar.getInstance(TimeZone.getDefault())
        horaInicial = agora.get(Calendar.HOUR_OF_DAY)
        minutosInicial = agora.get(Calendar.MINUTE)
        horaFinal = horaInicial
        minutosFinal = minutosInicial

        db = androidx.room.Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration().build()

        val btnHorarioInicial = findViewById<Button>(R.id.btnHorarioInicial)
        val btnHorarioFinal = findViewById<Button>(R.id.btnHorarioFinal)
        val btnSalvarHorario = findViewById<Button>(R.id.btnSalvarHorario)
        val btnSelecionarData = findViewById<Button>(R.id.btnSelecionarData)

        btnHorarioInicial.text = "Selecionar Horário Inicial"
        btnHorarioFinal.text = "Selecionar Horário Final"
        btnSelecionarData.text = "Selecionar Data"

        btnHorarioInicial.setOnClickListener {
            mostrarTimePickerHorarioInicial(btnHorarioInicial)
        }

        btnHorarioFinal.setOnClickListener {
            mostrarTimePickerHorarioFinal(btnHorarioFinal)
        }

        btnSelecionarData.setOnClickListener {
            val calendario = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    ano = year
                    mes = month + 1 // Calendar.MONTH é 0-based
                    dia = dayOfMonth
                    btnSelecionarData.text = String.format("%02d/%02d/%04d", dia, mes, ano)
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        btnSalvarHorario.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val novoHorario = Horario(
                        ano = ano,
                        mes = mes,
                        dia = dia,
                        horaInicial = horaInicial,
                        minutosInicial = minutosInicial,
                        horaFinal = horaFinal,
                        minutosFinal = minutosFinal,
                        ativo = true
                    )
                    db.horarioDao().insertHorario(novoHorario)
                }
                finish()
            }
        }
    }
    

    private fun mostrarTimePickerHorarioInicial(btn: Button) {
        val picker = com.google.android.material.timepicker.MaterialTimePicker.Builder()
            .setTimeFormat(com.google.android.material.timepicker.TimeFormat.CLOCK_24H)
            .setHour(horaInicial)
            .setMinute(minutosInicial)
            .setTitleText("Selecionar Horário Inicial")
            .build()
        picker.addOnPositiveButtonClickListener {
            horaInicial = picker.hour
            minutosInicial = picker.minute
            btn.text = String.format("%02d:%02d", horaInicial, minutosInicial)
        }
        picker.show(supportFragmentManager, "timePickerInicial")
    }

    private fun mostrarTimePickerHorarioFinal(btn: Button) {
        val picker = com.google.android.material.timepicker.MaterialTimePicker.Builder()
            .setTimeFormat(com.google.android.material.timepicker.TimeFormat.CLOCK_24H)
            .setHour(horaFinal)
            .setMinute(minutosFinal)
            .setTitleText("Selecionar Horário Final")
            .build()
        picker.addOnPositiveButtonClickListener {
            horaFinal = picker.hour
            minutosFinal = picker.minute
            btn.text = String.format("%02d:%02d", horaFinal, minutosFinal)
        }
        picker.show(supportFragmentManager, "timePickerFinal")
    }
} 