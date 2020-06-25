package com.careernaksha.careernaksha.book_appointment

import com.google.firebase.firestore.DocumentReference

interface BookAppointmentCallback{
    fun onReceiveError(error: String)
    fun onAddSlot(result:DocumentReference)
}