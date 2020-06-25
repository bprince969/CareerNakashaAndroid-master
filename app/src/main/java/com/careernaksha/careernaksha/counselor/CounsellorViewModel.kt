package com.careernaksha.careernaksha.counselor

import androidx.lifecycle.ViewModel
import com.careernaksha.careernaksha.model.Counsellor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class CounsellorViewModel : ViewModel() {

    private val database = FirebaseFirestore.getInstance()
    var listener: CounsellorListener? = null
    private val counsellors= ArrayList<Counsellor>()

    init {
        loadCounsellors()
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
        database.firestoreSettings = settings
    }

    private fun loadCounsellors() {
        database.collection("counselor").get().addOnSuccessListener {
            if (it.isEmpty) {
                listener?.onReceiveError("Counsellors not found.")
                return@addOnSuccessListener
            }
            it.documents.forEach { document ->
                counsellors.add(document.toObject(Counsellor::class.java)!!)
            }
            listener?.onReceiveCounsellor(counsellors)
        }.addOnFailureListener {
            listener?.onReceiveError(it.localizedMessage)
        }
    }

}