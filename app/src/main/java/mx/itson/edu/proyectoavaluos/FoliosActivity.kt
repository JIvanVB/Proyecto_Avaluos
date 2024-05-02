package mx.itson.edu.proyectoavaluos

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar

class FoliosActivity : AppCompatActivity() {
    private val avaluosRef = FirebaseDatabase.getInstance().getReference("Avaluos")
    val listaAvaluos = mutableListOf<Avaluo>()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_folios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuario = intent.getStringExtra("usuario")!!

        findViewById<TextView>(R.id.textViewTitle).text="Folios de "+ usuario.substringBefore('@')

        findViewById<Button>(R.id.buttonAddFolios).setOnClickListener{
            avaluosRef.push().setValue(Avaluo(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time),usuario))
        }
        val lista = findViewById<LinearLayout>(R.id.buttonList)
        /*avaluosRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                listaAvaluos.add(Avaluo())
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {                }
            override fun onChildRemoved(snapshot: DataSnapshot) {                }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {                }
            override fun onCancelled(error: DatabaseError) {                }

        })*/
    }
}