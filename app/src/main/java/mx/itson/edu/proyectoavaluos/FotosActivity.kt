package mx.itson.edu.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FotosActivity : AppCompatActivity() {
    //private lateinit var dbRef: DatabaseReference
    private val file = 1
    //val usuario=intent.getStringExtra("usuario")!!
    //val id=intent.getStringExtra("id")!!
    //val fotos=intent.getStringExtra("captura")!!
    private lateinit var refdb : DatabaseReference
    private lateinit var Folder :StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fotos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usuario=intent.getStringExtra("usuario")!!
        val id=intent.getStringExtra("id")!!
        val fotos=intent.getStringExtra("captura")!!
        findViewById<TextView>(R.id.textViewTitleFolio).text = buildString {
            append("Folio ")
            append(id)
            append(" ")
            append(usuario)
            append(" ")
            append(fotos)
        }
        Folder = FirebaseStorage.getInstance().getReference("Avaluos").child(usuario).child(id).child(fotos)
        refdb = Firebase.database.getReference("Avaluos").child(usuario).child(id).child(fotos)

        /*dbRef= FirebaseDatabase.getInstance().getReference("Avaluos")
            .child(usuario)
            .child(id)
            .child(fotos)*/

        arrayOf("Sala", "Comedor", "Cocina", "Baño", "Patio", "Recámara principal", "Recámara 1", "Recámara 2", "Estacionamiento", "Fachada Principal", "Entorno").forEach {
            tex ->
            findViewById<LinearLayout>(R.id.caract).addView(Button(this).apply {
                text = tex
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                textSize=20F
                setPadding(10)
                setOnClickListener {
                    subirArchivo()
                }
            })
        }/*.also {
            findViewById<LinearLayout>(R.id.caract).addView(Button(this).apply {
                text = "Fotos adicionales"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                textSize=20F
                setPadding(10)
            })
        }*/

    }

    private fun subirArchivo() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type="*/*"
        startActivityForResult(intent,file)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==file)
            if (resultCode== RESULT_OK) {
                val FileUri = data!!.data
                //Folder  = FirebaseStorage.getInstance().getReference().child(usuario).child(id).child(fotos)
                val file_name : StorageReference = Folder.child("file"+FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.downloadUrl.addOnSuccessListener { uri ->
                        val hashMap = HashMap<String,String>()
                        hashMap["link"] = uri.toString()
                        refdb.setValue(hashMap)
                        Log.d("Mensaje","Se subio correctamente")
                    }
                }

            }
    }
}