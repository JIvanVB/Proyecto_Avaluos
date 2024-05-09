package mx.itson.edu.proyectoavaluos

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.setPadding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ElementosAccesoriosAvtivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var v: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_elementos_accesorios_avtivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val usuario = intent.getStringExtra("usuario")!!
        val id = intent.getStringExtra("id")!!
        val captura = intent.getStringExtra("captura")!!
        val acabado = "Elementos Accesorios"

        findViewById<TextView>(R.id.textViewTitulo).text = buildString {
            append("Folio ")
            append(id)
            append(" ")
            append(usuario)
            append(" ")
            append(captura)
            append(" ")
            append(acabado)
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Avaluos")
            .child(usuario)
            .child(id)
            .child(captura)
            .child(acabado.replace(" ", "_"))


        val opciones = ArrayList<String>()

        v =  ArrayList(hashMapOf("Portón de Herrería" to false, "Protecciones de herrería" to false, "Cocina Hecha en Obra" to false, "Cocina Integral" to false, "Equipos Aire Acondicionado" to false, "Muebles Empotrados" to false, "Closet de Madera" to false, "Aire Acondicionado mini-split" to false, "Aire Acondicionado de Ventana" to false, "Sistema de Aire acondicionado central" to false, "Tinaco" to false, "Sistema Hidroneumático" to false, "Tanque Estacionario" to false, "Recubrimientos de Cantera" to false, "Recubrimientos Cerámicos" to false, "Asador" to false, "Escalera de Herrería Exterior" to false, "Cerco de Herrería" to false, "Sistema de Seguridad" to false, "Cerco Electrificado" to false, "Closet" to false).keys)

        v.forEach { e ->
            findViewById<LinearLayout>(R.id.elementos).addView(
                CheckBox(this).apply {
                    text = e
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                    textSize=20F
                    setPadding(10)
                    setOnClickListener{ if (this.isChecked) opciones.add(text.toString()) else opciones.remove(text.toString())}
                })
        }
        findViewById<LinearLayout>(R.id.elementos).addView(Button(this).apply {
            text = "Guardar"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            textSize=20F
            setPadding(10)
            setOnClickListener {
                if (v.isNotEmpty()) dbRef.setValue(opciones)
            }
        })

        dbRef.addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    snapshot.let{ sn ->
                        findViewById<LinearLayout>(R.id.elementos).forEach {
                            if (it is CheckBox)
                                if(it.text==sn.value)     it.isChecked=true
                        }
                    }
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {                }
                override fun onChildRemoved(snapshot: DataSnapshot) {                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {                }
                override fun onCancelled(error: DatabaseError) {              }

            })
    }
}