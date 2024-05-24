package com.example.restaurantapp

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var registerButton: Button
    private lateinit var logInButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var nameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        initComponents()

        auth = FirebaseAuth.getInstance()

        initListeners()
    }

    private fun initListeners()
    {
        logInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(baseContext, "Registro fallido.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor llenar todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }

        setEnterKeyListener(nameEditText)
        setEnterKeyListener(emailEditText)
        setEnterKeyListener(passwordEditText)
        setEnterKeyListener(confirmPasswordEditText)
    }

    private fun initComponents()
    {
        registerButton = findViewById(R.id.loginButton)
        logInButton = findViewById(R.id.logInTextButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        nameEditText = findViewById(R.id.nameEditText)
    }

    private fun setEnterKeyListener(editText: EditText) {
        editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                // Evita que se añada una nueva línea
                true
            } else {
                false
            }
        }
    }
}
