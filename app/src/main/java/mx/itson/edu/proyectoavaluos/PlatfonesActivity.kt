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

class PlatfonesActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_platfones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        val captura=intent.getStringExtra("captura")!!
        val acabado=intent.getStringExtra("acabado")!!
        val platfones=intent.getStringExtra("platfones")!!

        val opciones = ArrayList<String>()

        findViewById<TextView>(R.id.textViewTitulo).text = buildString {
            append("Folio ")
            append(id)
            append(" ")
            append(usuario)
            append(" ")
            append(captura)
            append(" ")
            append(acabado)
            append(" ")
            append(platfones)
        }

        dbRef= FirebaseDatabase.getInstance().getReference("Avaluos")
            .child(usuario)
            .child(id)
            .child(captura)
            .child(acabado)
            .child(platfones)

        arrayOf("Acabado tipo tirol", "Tirol planchado", "Tirol y yeso con pintura", "Yeso acabado pintura", "Aplanado fino de yeso y pintura", "Yeso acabado esmalte", "Aplanado cemento-arena y pintura", "Pasta texturizada", "Aplanado cemento-arena sin pintura", "Aplanado fino con molduras de yeso", "Adoquín y vigas de madera", "Lámina y perfiles de madera", "Lámina y perfiles metálicos", "Plafones de poliestireno expandido con cancelería", "Fibra de vidrio y perfiles metálicos", "Teja y perfiles metálicos", "Lona y perfiles metálicos", "Mallasombra y perfiles metálicos", "Insulpanes y perfiles metálicos", "Pergolado", "Losa aparente sin enjarre", "Aplanado de mezcla en regular estado", "Yeso terminado pintura en regular estado", "Losa en mal estado con acero expuesto", "Losa quemada en mal estado", "Se supone tirol")
            .forEach { tex ->
                findViewById<LinearLayout>(R.id.elementos).addView(
                    CheckBox(this).apply {
                        text = tex
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                        textSize=20F
                        setPadding(10)
                        setOnClickListener{ if(this.isChecked) opciones.add(text.toString()) else opciones.remove(text.toString())}
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

                if (opciones.isNotEmpty())
                    dbRef.setValue(opciones)
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