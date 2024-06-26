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

class MurosActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_muros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        val captura=intent.getStringExtra("captura")!!
        val acabado=intent.getStringExtra("acabado")!!
        val muros=intent.getStringExtra("muros")!!

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
            append(muros)
        }

        dbRef= FirebaseDatabase.getInstance().getReference("Avaluos")
            .child(usuario)
            .child(id)
            .child(captura)
            .child(acabado)
            .child(muros)

        arrayOf("Aplanado mortero cemento-arena y pintura", "Aplanado fino cemento-arena y pintura", "Aplanado mortero cemento arena sin pintura", "Pasta texturizada", "Aplanado de yeso y pintura", "Block aparente y pintura", "Azulejo", "Aplanado de mezcla, pintura y azulejo en área húmeda", "Aplanado fino de mezcla, pintura y azulejo en área húmeda", "Aplanado de mezcla sin pintura y azulejo en área húmeda", "Pasta texturizada y azulejo en área húmeda", "Aplanado de yeso con pintura y azulejo en área húmeda", "Block aparente con pintura y azulejo en área húmeda", "Azulejo a 2.10m y aplanado de mezcla con pintura", "Azulejo a 0.90m y aplanado de mezcla con pintura", "Tirol lanzado", "Tirol planchado", "Ladrillo aparente", "Block aparente", "Barda con enjarre sin reglear", "Mortero lanzado", "Acabado cerroteado", "Aplanado de mezcla y recubrimientos de granito", "Aplanado de mezcla y recubrimientos cerámicos", "Aplanado de mezcla y recubrimientos de piedra cultivada", "Aplanado de mezcla y recubrimientos de cantera", "Aplanado de mezcla y azulejo en regular estado", "Aplanado de mezcla en regular estado", "Aplanado de yeso en regular estado", "Aplanado de mezcla vandalizado en mal estado", "Yeso vandalizado en mal estado", "Block aparente vandalizado en mal estado", "Azulejo vandalizado en mal estado", "Lámian acanalada", "Se supone aplanado de mezcla con pintura")
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