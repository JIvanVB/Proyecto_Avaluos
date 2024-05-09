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

class GeneralActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var v : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_general)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        val captura=intent.getStringExtra("captura")!!
        val acabado=intent.getStringExtra("acabado")!!

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

        dbRef= FirebaseDatabase.getInstance().getReference("Avaluos")
            .child(usuario)
            .child(id)
            .child(captura)
            .child(acabado.replace(" ","_"))

        val opciones = ArrayList<String>()

        when (acabado){
            "Instalaciones Hidraulicas y Sanitarias" -> v = ArrayList(hashMapOf("CPVC de 1/2\" y sanitaria de PVC de 2\" y 4\"" to false, "Cobre de 1/2\" y sanitaria de PVC de 2\" y 4\"" to false, "Tubo Plus de 1/2\" y sanitaria de PVC de 2\" y 4\"" to false, "No tiene, se encuentra vandalizada la vivienda" to false).keys)
            "Instalaciones Electricas" -> v = ArrayList(hashMapOf("Oculta instalación eléctrica a base de poliductos de 1/2\", cables calibre 12 y 14 centro de carga con dos circuitos" to false, "Visibles instalación eléctrica a base de poliductos de 1/2\", cables calibre 12 y 14 centro de carga con dos circuitos" to false, "No tiene, se encuentra vandalizada la vivienda" to false).keys)
            "Canceleria y comunicaciones" -> v = ArrayList(hashMapOf("Ventanas a base de aluminio, cristal transparente. Protecciones de herrería en ventanas." to false, "Ventanas a base de aluminio, cristal transparente" to false, "Cerco frontal de herrería de buena calidad" to false, "Ventanas a base de herrería, cristal transparente. Protecciones de herrería en ventanas." to false, "Puertas de herrería común en accesos a la vivienda" to false).keys)
            "Obras complementarias" -> v =  ArrayList(hashMapOf("Firmes de concreto" to false, "Firmes con loseta en patio" to false, "Firme de adoquín" to false, "Bardas de Block Aparente" to false, "Bardas de Ladrillo Aparente" to false, "Voladizos" to false, "Bardas con Aplanado" to false, "Firmes con loseta" to false, "Firme concreto estampado" to false, "Techumbre de Mallasombra" to false, "Techumbre de Lamina" to false, "Cochera cubierta concreto" to false, "Área techada lamina" to false, "Áreas techadas concreto" to false, "Techumbre de Galvateja" to false, "Pasillos de circulación" to false, "Pórtico" to false, "Cuarto de servicio" to false, "Bodega" to false, "Baño en exterior de tablaroca" to false, "Alberca" to false, "Ampliación (Obra Negra)" to false, "Ampliación (Obra Negra, Techumbre de lámina)" to false, "Terraza" to false, "Kiosco" to false).keys)
            "Elementos accesorios" -> v =  ArrayList(hashMapOf("Portón de Herrería" to false, "Protecciones de herrería" to false, "Cocina Hecha en Obra" to false, "Cocina Integral" to false, "Equipos Aire Acondicionado" to false, "Muebles Empotrados" to false, "Closet de Madera" to false, "Aire Acondicionado mini-split" to false, "Aire Acondicionado de Ventana" to false, "Sistema de Aire acondicionado central" to false, "Tinaco" to false, "Sistema Hidroneumático" to false, "Tanque Estacionario" to false, "Recubrimientos de Cantera" to false, "Recubrimientos Cerámicos" to false, "Asador" to false, "Escalera de Herrería Exterior" to false, "Cerco de Herrería" to false, "Sistema de Seguridad" to false, "Cerco Electrificado" to false, "Closet" to false).keys)
        }

        v.forEach { e ->
            findViewById<LinearLayout>(R.id.elementos).addView(
                CheckBox(this).apply {
                    text = e.toString()
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