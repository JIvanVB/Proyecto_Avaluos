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

class GeneralEntornoActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var v : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_general_entorno)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        val entorno=intent.getStringExtra("entorno")!!
        val caract=intent.getStringExtra("caract")!!

        findViewById<TextView>(R.id.textViewTitulo).text = buildString {
            append("Folio ")
            append(id)
            append(" ")
            append(usuario)
            append(" ")
            append(entorno)
            append(" ")
            append(caract)
        }

        dbRef= FirebaseDatabase.getInstance().getReference("Avaluos")
            .child(usuario)
            .child(id)
            .child(entorno)
            .child(caract.replace(" ","_"))


        val opciones = ArrayList<String>()

        when (caract){
            "Clasificacion de la Zona" -> v = arrayListOf("Habitacional Tipo de Interés Social", "Habitacional tipo nivel medio", "Habitacional Tipo de Interés Social y Medio", "Habitacional nivel medio y económico", "Habitacional Superior", "Habitacional Económico", "Mixto, Comercial y Habitacional", "Habitacional Departamentos Nivel Medio")
            "Tipos de Construccion en la Zona" -> v = arrayListOf("Casas Habitación", "Departamentos", "Locales comerciales principalmente", "Comercios y casas habitación")
            "Tipo de construcciones predominantes" -> v = arrayListOf("Casas unifamiliares de 1 y 2 niveles, tipo de Interés Social", "Casas unifamiliares de 1 y 2 niveles, tipo medio y económico", "Casas unifamiliares de 1 y 2 niveles, tipo Superior", "Casas unifamiliares de 1 y 2 niveles, tipo medio", "Casas unifamiliares de 1 y 2 niveles, Tipo Económicas", "Comercios y casas unifamiliares de 1 y 2 niveles clase media y económica")
            "Densidad de Población" -> v = arrayListOf("Normal","Escasa")
            "Contaminacion Ambiental" -> v = arrayListOf("No se aprecia algún tipo de contaminación","Polvos por terracerías","Transito Vehicular")
            "Grado e Impacto" -> v = arrayListOf("No existe medición del indicador", "Polvos por terracerías", "Transito vehicular", "Ruidos")
            "Uso del Suelo" -> v = arrayListOf("Habitacional", "Comercial", "Mixto, Habitacional y Comercial")
            "Agua potable"-> v =  arrayListOf("Existe con cometida al inmueble","No existe")
            "Drenaje y alcantarillado" -> v = arrayListOf("Con conexión al inmueble "," No existe")
            "Red drenaje pluvial en la calle" -> v = arrayListOf("Existe con tomas tipo boca de tormenta", "Natural por pendiente en vialidad", "No existe")
            "Sistema mixto (residual y pluvial)" -> v = arrayListOf("De las viviendas hacia la red de drenaje municipal ","No Existe")
            "Electrificacion"-> v = arrayListOf("Aérea","Subterránea"," No existe")
            "Alumbrado publico" -> v = arrayListOf("Existe"," No existe")
            "Vialidades terminadas" -> v = arrayListOf("Pavimento de concreto asfáltico", "Pavimento de concreto hidráulico", "Terracerías")
            "Banquetas"-> v = arrayListOf("De concreto"," No existe")
            "Guarniciones"-> v = arrayListOf("De concreto"," No existe")
            "Nivel de Infraestructura en la zona"-> v = arrayListOf("Cuenta con los servicios de alumbrado público, abastecimiento de agua, drenaje y vialidades terminadas", "Cuenta con los servicios de alumbrado público, abastecimiento de agua, drenaje y vialidades de terracería", "No cuenta con pavimentación, solamente suministro de agua y drenaje.")
            "Gas natural"-> v = arrayListOf("Existe"," No existe")
            "Red telefónica"-> v = arrayListOf("Existe"," No existe")
            "Señalización y nomenclatura"-> v = arrayListOf("Existe"," No existe")
            "Transporte urbano"-> v = arrayListOf("A unos 100 m. aproximadamente", "Inmediato", "A unos 200 m. aproximadamente", "A unos 300 m. aproximadamente", "A unos 400 m. aproximadamente", "A unos 500 m. aproximadamente")
            "Recolección de basura"-> v = arrayListOf("Cada 3 días en promedio", "Cada 5 días en promedio", "Cada semana")
            "Topografía"-> v = arrayListOf("Topografía Plana"," Topografía Accidentada")
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