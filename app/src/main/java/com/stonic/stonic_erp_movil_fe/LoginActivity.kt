package com.stonic.stonic_erp_movil_fe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        val googleSignInButton: ImageButton = findViewById(R.id.login_imgbtn_google)

        googleSignInButton.setOnClickListener {
            startSignInWithGoogle()
        }

        val loginButton: Button = findViewById(R.id.login_btn_login)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.login_edttxt_email).text.toString()
            val password = findViewById<EditText>(R.id.login_edttxt_password).text.toString()
            signInWithEmail(email, password)
        }
    }

    private fun startSignInWithGoogle() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        startSignIn(providers)
    }

    private fun startSignInWithFacebook() {
        val providers = arrayListOf(AuthUI.IdpConfig.FacebookBuilder().build())
        startSignIn(providers)
    }

    private fun startSignInWithMicrosoft() {
        val providers = arrayListOf(AuthUI.IdpConfig.MicrosoftBuilder().build())
        startSignIn(providers)
    }

    private fun startSignIn(providers: List<AuthUI.IdpConfig>) {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    navigateToMainActivity()
                } else {
                    // Si el inicio de sesión falla, mostrar un mensaje al usuario
                    // Actualizar UI con el error
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                // El inicio de sesión fue exitoso
                navigateToMainActivity()
            } else {
                // Si el inicio de sesión falla, mostrar un mensaje al usuario
                // Actualizar UI con el error
                if (response == null) {
                    finish()
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}