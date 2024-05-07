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

class EntornoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_entorno)
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

        arrayOf("Clasificación de la Zona", "Tipos de Construcción en la Zona", "Tipo de construcciones predominantes", "Densidad de Población", "Contaminación Ambiental", "Grado e Impacto", "Uso del Suelo", "Agua potable", "Drenaje y alcantarillado", "Red drenaje pluvial en la calle", "Sistema mixto (residual y pluvial)", "Electrificación", "Alumbrado público", "Vialidades terminadas", "Banquetas", "Guarniciones", "Nivel de Infraestructura en la zona", "Gas natural", "Red telefónica", "Señalización y nomenclatura", "Transporte urbano", "Recolección de basura", "Topografía").forEach {
            tex ->
            findViewById<LinearLayout>(R.id.caract).addView(Button(this).apply {
                text = tex
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                textSize=20F
                setPadding(10)
                //setOnClickListener { startActivity(Intent(this@InmuebleActivity,AcabadosActivity::class.java).putExtra("usuario",usuario).putExtra("id",id).putExtra("acabado",tex)) }
            })
        }

    }
}