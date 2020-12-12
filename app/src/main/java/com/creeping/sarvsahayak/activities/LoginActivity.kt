package com.creeping.sarvsahayak.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.creeping.sarvsahayak.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {



    private lateinit var mGoogleSignInClient:GoogleSignInClient
    private val RC_SIGN_IN: Int=123
    private lateinit var auth:FirebaseAuth

    //sign in with google button
    lateinit var btGoogleSignIn:SignInButton

    lateinit var NoInternetConnection:TextView

    lateinit var ProgressBarLogin:ProgressBar

    override fun onStart() {
        //overriding the onStart() func
        val currentUser = auth.currentUser
        if (currentUser!=null)
        {
            println("user comes 2nd time")
            //If the user already exists in firebase
            val intent=Intent(this, MobileVerification::class.java)
            startActivity(intent)
            finish()
        }
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //firebase auth object initialization
        auth= FirebaseAuth.getInstance()

        createRequest()
        //HOOK
        btGoogleSignIn=findViewById(R.id.btGoogleSignIn)
        btGoogleSignIn.setOnClickListener{
            signIn()

        }
        NoInternetConnection=findViewById(R.id.NoInternetConnection)

        NoInternetConnection.setOnClickListener {
            val intent=Intent(this, OfflineMessage::class.java)
            startActivity(intent)
            finish()
        }

        ProgressBarLogin=findViewById(R.id.ProgressBarLogin)


    }

    private fun createRequest() {
        //step 1 : Creating request
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private fun signIn() {
        //step 2
        val signInIntent = mGoogleSignInClient.signInIntent
        //assign any integer valuse to RC_SIGN_IN
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //step 3:
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                ProgressBarLogin.visibility= View.VISIBLE

//                Toast.makeText(this,"sign in approved" , Toast.LENGTH_SHORT).show()
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e)
                // ...

                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
                    println("user comes 1st time")
                    val user = auth.currentUser
                    val intent=Intent(this, MobileVerification::class.java)
                    startActivity(intent)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
//                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
//                    updateUI(null)
                    ProgressBarLogin.visibility=View.INVISIBLE
                    Toast.makeText(this, "Sorry authentication failed", Toast.LENGTH_SHORT).show()

                }

                // ...
            }
    }


}