package mx.itson.edu.proyectoavaluos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itson.edu.proyectoavaluos.databinding.ActivityRegistrarseBinding

class RegistrarseActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistrarseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //findViewById<Button>(R.id.buttonRegister).setOnClickListener{
        binding.buttonRegister.setOnClickListener{
            val email = binding.editTextEmail./*findViewById<EditText>(R.id.editTextEmail).*/text.toString().trim()
            val pass = binding.editTexPassword/*findViewById<EditText>(R.id.editTexPassword)*/.text.toString().trim()
            val passConf = binding.editTextPasswordConfirm/*findViewById<EditText>(R.id.editTextPasswordConfirm)*/.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty() || passConf.isEmpty())
                Toast.makeText(baseContext, "Mail o contraseña vacios. Debe llenar todo.", Toast.LENGTH_SHORT).show()
            else
                Registrarse(email,pass,passConf)
        }
    }

    private fun Registrarse(email: String, pass: String, passConf: String) {

        if(pass == passConf)
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isComplete) {
                        Toast.makeText(this, "$email registrado correctamente", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else
                        Toast.makeText( this,"Error al registrar usuario: ${it.exception?.message}",
                            Toast.LENGTH_LONG).show()
            }
    }

}