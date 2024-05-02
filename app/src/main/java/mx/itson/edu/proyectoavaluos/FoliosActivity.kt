package mx.itson.edu.proyectoavaluos

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.edu.proyectoavaluos.databinding.ActivityMainBinding

class FoliosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_folios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<TextView>(R.id.textViewTitle).text="Folios de "+ intent.getStringExtra("usuario")!!
            .substringBefore('@')
        findViewById<Button>(R.id.buttonAddFolios).setOnClickListener{
            findViewById<LinearLayout>(R.id.buttonList).addView(Button(this).apply { text="Folio" })
        }

        //for (i in  1..10)linearLayout.addView(Button(this).apply { text="boton que no hace nada";height=200 })
    }
}