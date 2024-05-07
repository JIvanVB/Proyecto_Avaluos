package mx.itson.edu.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding

class FotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fotos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        findViewById<TextView>(R.id.textViewTitleFolio).text = buildString {
            append("Folio ")
            append(id)
            append(" ")
            append(usuario)
        }

        arrayOf("Sala", "Comedor", "Cocina", "Baño", "Patio", "Recámara principal", "Recámara 1", "Recámara 2", "Estacionamiento", "Fachada Principal", "Entorno").forEach {
            tex ->
            findViewById<LinearLayout>(R.id.caract).addView(Button(this).apply {
                text = tex
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                textSize=20F
                setPadding(10)
            })
        }.also {
            findViewById<LinearLayout>(R.id.caract).addView(Button(this).apply {
                text = "Fotos adicionales"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                textSize=20F
                setPadding(10)
            })
        }

    }
}