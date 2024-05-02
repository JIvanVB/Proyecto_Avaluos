package mx.itson.edu.proyectoavaluos

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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


        val linearLayout = findViewById<LinearLayout>(R.id.buttonList)
        val button = Button(this)
        button.text = "Botón dinámico"
        button.height=200

        linearLayout.addView(button)
        for (i in  1..10)linearLayout.addView(Button(this).apply { text="boton que no hace nada";height=200 })
    }
}