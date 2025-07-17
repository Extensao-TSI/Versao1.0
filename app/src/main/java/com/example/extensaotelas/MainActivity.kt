package com.example.extensaotelas

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.extensaotelas.BancoDeDados.AppDatabase
import com.google.android.material.button.MaterialButton
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate // Lib do gráfico
import com.github.anastr.speedviewlib.SpeedView // Lib dos icones de temperatura e umidade

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //instanciar o bd
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "horta_monitoramento"
        ).build()

        val btnGerenciarHorario = findViewById<MaterialButton>(R.id.btnGerenciarHorario)
        btnGerenciarHorario.setOnClickListener {
            val intent = Intent(this, ListaHorariosActivity::class.java)
            startActivity(intent)
        }

        val speedViewTemperatura = findViewById<SpeedView>(R.id.speedViewTemperatura)
        val speedViewUmidadeAr = findViewById<SpeedView>(R.id.speedViewUmidadeAr)
        val speedViewUmidadeSolo = findViewById<SpeedView>(R.id.speedViewUmidadeSolo)


        speedViewTemperatura.sections[0].color = Color.parseColor("#2ccc74")
        speedViewTemperatura.sections[1].color = Color.parseColor("#f4c40c")
        speedViewTemperatura.sections[2].color = Color.parseColor("#e44c3c")

        speedViewUmidadeAr.sections[1].color = Color.parseColor("#f4c40c")
        speedViewUmidadeAr.sections[2].color = Color.parseColor("#e44c3c")
        speedViewUmidadeAr.sections[0].color = Color.parseColor("#2ccc74")

        speedViewUmidadeSolo.sections[0].color = Color.parseColor("#2ccc74")
        speedViewUmidadeSolo.sections[1].color = Color.parseColor("#f4c40c")
        speedViewUmidadeSolo.sections[2].color = Color.parseColor("#e44c3c")


        speedViewTemperatura.speedTo(56f, 4000)
        // speedViewTemperatura.withTremble = false
        speedViewUmidadeAr.speedTo(100f, 4000)
        speedViewUmidadeSolo.speedTo(100f, 4000)

        val barChart = findViewById<BarChart>(R.id.barChartTemperatura)
        val entries = listOf(
            BarEntry(0f, 22f),
            BarEntry(1f, 24f),
            BarEntry(2f, 23f),
            BarEntry(3f, 21f),
            BarEntry(4f, 19f),
            BarEntry(5f, 17f),
            BarEntry(6f, 18f),
            BarEntry(7f, 20f),
            BarEntry(8f, 22f),
            BarEntry(9f, 24f),
            BarEntry(10f, 25f),
            BarEntry(11f, 23f)
        )
        val dataSet = BarDataSet(entries, "Média Temperatura (°C)")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.description.text = "Média mensal de temperatura"
        val meses = listOf(
            "Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
            "Jul", "Ago", "Set", "Out", "Nov", "Dez"
        )
        barChart.xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(meses)
        barChart.xAxis.granularity = 1f
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisRight.isEnabled = false
        barChart.animateY(1000)
    }
}