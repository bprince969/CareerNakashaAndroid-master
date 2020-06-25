package com.careernaksha.careernaksha.book_appointment

import androidx.lifecycle.ViewModel
import com.careernaksha.careernaksha.model.Counsellor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookAppointmentViewModel : ViewModel() {

    var listener: BookAppointmentCallback? = null
    lateinit var counsellor:Counsellor

    private val database = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addNewSlot(date: String, time: String) {
        val data = HashMap<String, Any>()
        data["date"] = date
        data["time"] = time
        data["status"] = 0
        data["uid"] = auth.uid ?: "Uid not found."
        data["name"] = auth.currentUser?.displayName ?: "Name not found"
        data["counsellorID"] = counsellor.id
        data["counsellorName"] = counsellor.name
        data["counsellorEmail"] = counsellor.email

        database.collection("booked_slot").add(data).addOnSuccessListener {
            listener?.onAddSlot(it)
        }.addOnFailureListener {
            listener?.onReceiveError(it.localizedMessage)
        }
    }
}