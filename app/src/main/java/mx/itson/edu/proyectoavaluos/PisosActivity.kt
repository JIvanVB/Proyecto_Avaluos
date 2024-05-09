package mx.itson.edu.proyectoavaluos

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.setPadding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PisosActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pisos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        val captura=intent.getStringExtra("captura")!!
        val acabado=intent.getStringExtra("acabado")!!
        val pisos=intent.getStringExtra("pisos")!!

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
            append(pisos)
        }

        dbRef= FirebaseDatabase.getInstance().getReference("Avaluos")
            .child(usuario)
            .child(id)
            .child(captura)
            .child(acabado)
            .child(pisos)

        val ops = arrayListOf("Loseta cerámica", "Loseta cerámica con zoclos", "Loseta cerámica rectificada con zoclos", "Loseta vinílica", "Mosaico cerámico", "Laminado imitación duela", "Duela de cedro", "Alfombra", "Loseta antiderrapante", "Loseta cerámica y antiderrapante en zona húmeda", "Concreto pulido y antiderrapante en zona húmeda", "Loseta vinílica y antiderrapante en zona húmeda", "Mosaico cerámico y antiderrapante en zona húmeda", "Firme de concreto y antiderrapante en zona húmeda", "Loseta cerámica y firme de concreto en zona húmeda", "Mosaico cerámico y firme de concreto en zona húmeda", "Firme de concreto pulido", "Firme de concreto común", "Firme de concreto escobillado", "Firme de concreto lavado", "Huellas de concreto", "Adoquín", "Tierra y andadores de concreto", "Tierra", "Cantera", "Pasto sintético", "Pasto", "Firme de concreto con tratamiento epóxico", "Firme de concreto sin reglear", "Empedrado", "Se supone firme de concreto pulido")

        val dd =  CheckBox(this@PisosActivity).apply {
            //val tex = findViewById<EditText>(R.id.editTextOpcion).text.toString()
            //tex.let { text=it }
            text="sss"
            textSize = 20F
            setPadding(10)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            setOnClickListener {if (this.isChecked) opciones.add(text.toString()) else opciones.remove(text.toString())}
        }

        val d = Dialog(this).apply {
            setContentView(R.layout.otra_opcion_diag)
            window!!.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            window!!.setBackgroundDrawable(R.drawable.diag_fondo.toDrawable())
            setCancelable(false)
            findViewById<Button>(R.id.botonCancelarDiag).setOnClickListener { this.dismiss() }
            findViewById<Button>(R.id.botonAgregarDiag).setOnClickListener {
                if (findViewById<EditText>(R.id.editTextOpcion).text.isEmpty())
                    Toast.makeText(baseContext, "No ingreso una nueva opcion", Toast.LENGTH_LONG).show()
                else {
                    ops.add(findViewById<EditText>(R.id.editTextOpcion).text.toString())
                    /*findViewById<LinearLayout>(R.id.elementos).addView(
                        dd
                    )*/
                    dismiss()
                }
            }
        }

        ops.forEach { tex ->
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

        findViewById<LinearLayout>(R.id.elementos).addView(
            Button(this).apply {
                text = "Alta de otro"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textSize = 20F
                setPadding(10)
                setOnClickListener {
                    d.show()
                }
            }
        )

        findViewById<LinearLayout>(R.id.elementos).addView(Button(this@PisosActivity).apply {
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