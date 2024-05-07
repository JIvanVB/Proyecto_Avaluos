
package mx.itson.edu.proyectoavaluos

import android.app.Dialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar
import java.util.Locale

class FoliosActivity : AppCompatActivity() {
    private lateinit var dbRef:DatabaseReference
    private lateinit var listAval: ArrayList<Avaluo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_folios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuario=intent.getStringExtra("usuario")!!.substringBefore('@')
        listAval = ArrayList()
        val tis=this
        dbRef=FirebaseDatabase.getInstance().getReference("Avaluos")

        val diag = Dialog(this).apply {
            setContentView(R.layout.avaluo_direccion_diag)
            window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            window!!.setBackgroundDrawable(R.drawable.diag_fondo.toDrawable())
            setCancelable(false)
            "AV${SimpleDateFormat("HHmmssddMMyyyy",Locale.US).format(Calendar.getInstance().time)}".also { it.also { findViewById<TextView>(R.id.subTituloAvalDiag).text = it } }
            findViewById<Button>(R.id.botonCancelarDiag).setOnClickListener { this.dismiss() }
            findViewById<Button>(R.id.botonAgregarDiag).setOnClickListener {
                if (findViewById<EditText>(R.id.editTextNomCalle).text.isEmpty()||findViewById<EditText>(R.id.editTextNum).text.isEmpty()||
                    findViewById<EditText>(R.id.editTextCodPostal).text.isEmpty()||findViewById<EditText>(R.id.editTextMuni).text.isEmpty()||
                    findViewById<EditText>(R.id.editTextPais).text.isEmpty())

                    Toast.makeText(baseContext, "llene los campos vacios", Toast.LENGTH_LONG).show()
                else {
                    val a = Avaluo(
                        findViewById<TextView>(R.id.subTituloAvalDiag).text.toString(),
                        usuario,
                        findViewById<EditText>(R.id.editTextNomCalle).text.toString(),
                        findViewById<EditText>(R.id.editTextNum).text.toString(),
                        findViewById<EditText>(R.id.editTextCodPostal).text.toString(),
                        findViewById<EditText>(R.id.editTextMuni).text.toString(),
                        findViewById<EditText>(R.id.editTextPais).text.toString()
                    )
                    dbRef.child(usuario).child(a.id).setValue(a)
                }
                this.dismiss()
            }
        }

        findViewById<TextView>(R.id.textViewTitle).text= buildString {
            append(getString(R.string.folios_de))
            append(usuario)
        }

        findViewById<Button>(R.id.buttonAddFolios).setOnClickListener{
            diag.show()
        }

        //for (i in  1..10)linearLayout.addView(Button(this).apply { text="boton que no hace nada";height=200 })
        dbRef.child(usuario).addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    snapshot.let { sn ->
                        val cc = sn

                        val id = cc.child("id").value
                        val usu = cc.child("usuario").value
                        val calle = cc.child("calle").value
                        val numCasa = cc.child("numCasa").value
                        val codPostal = cc.child("codPostal").value
                        val municipio = cc.child("municipio").value
                        val pais = cc.child("pais").value

                        listAval.add(Avaluo(id.toString(), usu.toString(), calle.toString(), numCasa.toString(), codPostal.toString(), municipio.toString(), pais.toString()))
                    }

                    listAval.forEach { findViewById<LinearLayout>(R.id.buttonList).
                    addView(Button(tis).apply { text=it.id;setOnClickListener { startActivity(Intent(tis,AvaluosAltaActivity::class.java).putExtra("folio",it.id)) } })}


                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {                }
                override fun onChildRemoved(snapshot: DataSnapshot) {                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {                }
                override fun onCancelled(error: DatabaseError) {                }

            })
    }
}