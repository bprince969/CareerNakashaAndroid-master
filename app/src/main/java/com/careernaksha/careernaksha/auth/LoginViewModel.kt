package com.careernaksha.careernaksha.auth

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.careernaksha.careernaksha.model.User
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.twitter.sdk.android.core.TwitterSession

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()

    var listener: LoginCallback? = null

    fun firebaseAuthWithGoogle(activity: Activity, acct: GoogleSignInAccount) {
        listener?.onStateChange("Authenticating with google.")
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                getUserData()
            } else {
                if (listener != null) {
                    listener!!.onReceiveError(task.exception?.message
                            ?: "Error while display error.")
                }
            }
        }
    }

    fun handleFacebookAccessToken(activity: Activity, token: AccessToken) {
        listener?.onStateChange("Authenticating with facebook.")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                getUserData()
            } else {
                if (listener != null) {
                    listener!!.onReceiveError(task.exception?.localizedMessage
                            ?: "Error while display error.")
                }
            }
        }
    }

    fun handleTwitterSession(session: TwitterSession) {
        listener?.onStateChange("Authenticating with twitter.")
        val credential = TwitterAuthProvider.getCredential(
                session.authToken.token,
                session.authToken.secret)

        auth.signInWithCredential(credential).addOnSuccessListener {
            getUserData()
        }.addOnFailureListener {
            if (listener != null) {
                listener!!.onReceiveError(it.localizedMessage ?: "Error while display error.")
            }
        }
    }

    fun signInWithEmailAndPassword(activity: Activity, email: String, password: String) {
        listener?.onStateChange("Authenticating with email.")
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                getUserData()
            } else {
                listener?.onReceiveError(task.exception?.localizedMessage
                        ?: "Error while display error.")
            }
        }
    }

    fun createUserWithEmailAndPassword(userName: String, phoneNumber: String, email: String, password: String) {
        listener?.onStateChange("Creating user with email.")
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            addUserData(userName, phoneNumber)
        }.addOnFailureListener {
            listener?.onReceiveError(it.localizedMessage)
        }
    }

    private fun getUserData() {
        database.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val name = it.get("name")
            if (name != null) {
                val user = User(
                        uid = it.getString("uid")!!,
                        name = it.getString("name")!!,
                        email = it.getString("email")!!,
                        phoneNumber = it.getString("phone") ?: " Phone not found."
                )
                if (listener != null) {
                    listener!!.onReceiveUser(user)
                }
            } else {
                addUserData()
            }
        }.addOnFailureListener {
            if (listener != null) {
                listener!!.onReceiveError(it.localizedMessage ?: "Error while display error.")
            }
        }
    }

     fun addUserData(userName: String? = null, phoneNumber: String? = null) {
        listener?.onStateChange("Adding user data")
        val currentUser = auth.currentUser ?: return
        val user = User(
                uid = currentUser.uid,
                name = userName ?: currentUser.displayName ?: "Name not found",
                email = currentUser.email ?: "Email not found",
                phoneNumber = phoneNumber ?: "Phone not found")

        database.collection("users").document(user.uid).set(user).addOnSuccessListener {
            if (listener != null) {
                listener!!.onReceiveUser(user)
            }
        }.addOnFailureListener {
            if (listener != null) {
                listener!!.onReceiveError(it.localizedMessage ?: "Error while display error.")
            }
        }
    }
}