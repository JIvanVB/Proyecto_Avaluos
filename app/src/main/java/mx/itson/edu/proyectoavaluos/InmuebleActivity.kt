package mx.itson.edu.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import androidx.core.view.setPadding

class InmuebleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inmueble)
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
        arrayOf("Sala", "Comedor", "Cocina", "Baño", "Recamara", "Estancia", "Patio Posterior", "Estacionamiento", "Terraza").forEach { tex ->
            findViewById<LinearLayout>(R.id.caract).addView(Button(this).apply {
                text = tex
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                textSize=20F
                setPadding(10)
                setOnClickListener { startActivity(Intent(this@InmuebleActivity,AcabadosActivity::class.java).putExtra("usuario",usuario).putExtra("id",id).putExtra("acabado",tex)) }
            })
        }
        //arrayOf("Instalaciones Hidráulicas y Sanitarias", "Instalaciones Eléctricas", "Cancelería y comunicaciones", "Obras Complementarias", "Elementos Accesorios")
        findViewById<LinearLayout>(R.id.caract).addView(Button(this).apply {
            text = "Alta de otro"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            textSize=20F
            setPadding(10)
            setOnClickListener { startActivity(Intent(this@InmuebleActivity,AcabadosActivity::class.java).putExtra("usuario",usuario).putExtra("id",id).putExtra("acabado",text)) }
        })
    }
}