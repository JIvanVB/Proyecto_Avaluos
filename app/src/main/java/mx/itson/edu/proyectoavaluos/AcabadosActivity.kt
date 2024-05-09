package mx.itson.edu.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AcabadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acabados)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        val captura=intent.getStringExtra("captura")!!
        val acabado=intent.getStringExtra("acabado")!!

        findViewById<TextView>(R.id.textViewTitleFolio).text = buildString {
            append("Folio ")
            append(id)
            append(" ")
            append(usuario)
            append(" ")
            append(captura)
            append(" ")
            append(acabado)
        }
        findViewById<TextView>(R.id.textViewInmueble).setOnClickListener { startActivity(Intent(this,PisosActivity::class.java)
            .putExtra("usuario",usuario)
            .putExtra("id",id)
            .putExtra("captura",captura)
            .putExtra("acabado",acabado)
            .putExtra("pisos","pisos")) }
        findViewById<TextView>(R.id.textViewEntorno).setOnClickListener { startActivity(Intent(this,MurosActivity::class.java)
            .putExtra("usuario",usuario)
            .putExtra("id",id)
            .putExtra("captura",captura)
            .putExtra("acabado",acabado)
            .putExtra("muros","muros")) }
        findViewById<TextView>(R.id.textViewFotografias).setOnClickListener { startActivity(Intent(this,PlatfonesActivity::class.java)
            .putExtra("usuario",usuario)
            .putExtra("id",id)
            .putExtra("captura",captura)
            .putExtra("acabado",acabado)
            .putExtra("platfones","platfones")) }
    }
}