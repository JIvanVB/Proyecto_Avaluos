package mx.itson.edu.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ModuloCapturaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modulo_captura)
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
        findViewById<TextView>(R.id.textViewInmueble).setOnClickListener { startActivity(Intent(this,InmuebleActivity::class.java)
            .putExtra("usuario",usuario)
            .putExtra("id",id)
            .putExtra("captura","inmueble"))}
        findViewById<TextView>(R.id.textViewEntorno).setOnClickListener { startActivity(Intent(this,EntornoActivity::class.java)
            .putExtra("usuario",usuario)
            .putExtra("id",id)
            .putExtra("captura","entorno")) }
        findViewById<TextView>(R.id.textViewFotografias).setOnClickListener { startActivity(Intent(this,FotosActivity::class.java)
            .putExtra("usuario",usuario)
            .putExtra("id",id)
            .putExtra("captura","fotos")) }


    }
}