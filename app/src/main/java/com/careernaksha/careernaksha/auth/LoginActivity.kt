@file:Suppress("DEPRECATION")

package com.careernaksha.careernaksha.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careernaksha.careernaksha.ProfileActivity
import com.careernaksha.careernaksha.R
import com.careernaksha.careernaksha.RequestHandler
import com.careernaksha.careernaksha.forgotpassword
import com.careernaksha.careernaksha.model.User
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.twitter.sdk.android.core.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private val signInCode = 0

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val callbackManager = CallbackManager.Factory.create()

    private lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog
    val URL_REGISTER = "https://www.apnishayari.in/test.php"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mail()


        val button = findViewById<TextView>(R.id.forgotPasswordBtn)
        button.setOnClickListener(){
            val intent = Intent(this, forgotpassword::class.java)
            startActivity(intent)
        }



        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                        getString(R.string.twitter_consumer_secret)))
                .debug(true)
                .build()
        Twitter.initialize(config)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        googleSignInButton.setOnClickListener {
            startActivityForResult(mGoogleSignInClient.signInIntent, signInCode)
        }
        facebookLoginBtn.setOnClickListener { loginFacebookBtn.performClick() }
        twitterLoginBtn.setOnClickListener { loginTwitterBtn.performClick() }

        linkedSignInButton.setOnClickListener{

            val intent = Intent(this, LinkedSignIn::class.java)
            startActivity(intent)
        }

        viewModel.listener = object : LoginCallback {
            override fun onReceiveError(error: String) {
                AlertDialog.Builder(this@LoginActivity).setTitle("Error").setMessage(error).setPositiveButton("Ok") { _, _ -> }.show()
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onReceiveUser(user: User) {
                viewModel.listener?.onStateChange(null)
                startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                this@LoginActivity.finish()
            }

            override fun onStateChange(state: String?) {
                if (state != null) {
                    progressDialog.setMessage(state)
                    progressDialog.show()
                } else {
                    progressDialog.hide()
                }
            }
        }

        loginTwitterBtn.callback = object : Callback<TwitterSession>() {

            override fun success(result: com.twitter.sdk.android.core.Result<TwitterSession>?) {
                viewModel.handleTwitterSession(result!!.data)
            }

            override fun failure(exception: TwitterException) {
                viewModel.listener?.onReceiveError(exception.localizedMessage) ?: return
            }
        }

        loginFacebookBtn.setReadPermissions("email", "public_profile")
        loginFacebookBtn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                viewModel.handleFacebookAccessToken(this@LoginActivity, loginResult.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Canceled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                viewModel.listener?.onReceiveError(error.localizedMessage) ?: return
            }
        })

        loginRegisterBtn.setOnClickListener {
            onClickRegister()
        }

        loginBtn.setOnClickListener {
            onClickLogin()
        }

    }

    private fun mail() {
        class Login : AsyncTask<Void?, Void?, String?>() {
            var pdLoading = ProgressDialog(this@LoginActivity)
            override fun onPreExecute() {
                super.onPreExecute()

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...")
                pdLoading.setCancelable(false)
                pdLoading.show()
            }

            protected override fun doInBackground(vararg params: Void?): String? {
                //creating request handler object
                val requestHandler = RequestHandler()

                //creating request parameters
                val params = HashMap<String, String>()
                params["email"] = "okgoogle.gmcom"


                //returing the response
                return requestHandler.sendPostRequest(URL_REGISTER, params)
            }

            protected override fun onPostExecute(s: String?) {
                super.onPostExecute(s)
                pdLoading.dismiss()
                try {
                    //converting response to json object
                    val obj = JSONObject(s)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    //   Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_LONG).show();
                }
            }
        }

        val l1 = Login()
        l1.execute()



    }


    private fun onClickRegister() {
        if (loginUsernameET.visibility == View.GONE && loginPhoneET.visibility == View.GONE) {
            loginUsernameET.visibility = View.VISIBLE
            loginPhoneET.visibility = View.VISIBLE
            return
        }

        val username = loginUsernameET.text.toString()
        val email = loginEmailET.text.toString().trim()
        val password = loginPasswordET.text.toString()
        val phoneNumber = loginPhoneET.text.toString()

        Log.d("OnClickRegister",email)

        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Enter Required Fields", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.createUserWithEmailAndPassword(username, phoneNumber, email, password)
        }
    }

    private fun onClickLogin() {
        if (loginUsernameET.visibility == View.VISIBLE && loginPhoneET.visibility == View.VISIBLE) {
            loginUsernameET.visibility = View.GONE
            loginPhoneET.visibility = View.GONE
            return
        }
        val email = loginEmailET.text.toString()
        val password = loginPasswordET.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Enter Required Fields", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.signInWithEmailAndPassword(this, email, password)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        loginTwitterBtn.onActivityResult(requestCode, resultCode, data)
        if (requestCode == signInCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.firebaseAuthWithGoogle(this, account!!)
            } catch (e: ApiException) {
                viewModel.listener?.onReceiveError(e.localizedMessage) ?: return
            }

        }
    }
}
