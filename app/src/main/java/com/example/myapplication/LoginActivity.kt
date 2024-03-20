package com.example.myapplication

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.ApiService
import com.example.myapplication.Models.User
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.BeginSignInRequest.PasswordRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.http.Tag


class LoginActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var oneTapClient: SignInClient? = null
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val sharedPref = getSharedPreferences("MySession", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = Firebase.auth
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()).build()
    }

    fun buttonGoogleSignIn(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            signinGoogle()
        }
    }


    private suspend fun signinGoogle() {
        val result = oneTapClient?.beginSignIn(signInRequest)?.await()
        val intentSenderRequest = IntentSenderRequest.Builder(result!!.pendingIntent).build()
        activityResultLauncher.launch(intentSenderRequest)
    }

    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential = oneTapClient!!.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Đăng nhập thành công, chuyển đến MainActivity

                                val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)

                                val firstName = googleSignInAccount?.givenName ?: ""
                                val lastName = googleSignInAccount?.familyName ?: ""
                                val currentUser = auth.currentUser
                                val email = currentUser?.email ?: ""
                                val displayName = currentUser?.displayName ?: ""
                                val photoUrl = currentUser?.photoUrl?.toString() ?: ""


                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val sharedPref = getSharedPreferences("MySession", MODE_PRIVATE)
                                        val editor = sharedPref.edit()
                                        editor.putBoolean("isLoggedIn", true)
                                        editor.apply()

                                        val user = User(email, firstName, lastName, displayName, photoUrl)
                                        val response = ApiService.apiService.createUser(user).execute()
                                        if (response.isSuccessful) {
                                            startActivity(intent)
                                            finish()
                                        } else {

                                        }
                                    } catch (e: Exception) {
                                        Log.e("WaringApi", "Error occurred during API call: ${e.message}", e)
                                    }
                                }

                                val intent = Intent(this, MainActivity::class.java)


                            } else {
                                // Đăng nhập không thành công
                                Log.e("LoginActivity", "signInWithCredential:failure", task.exception)
                            }
                        }
                    }
                } catch (e: ApiException) {
                    Log.e("LoginActivity", "signInResult:failed code=" + e.statusCode)
                }
            }
        }
}