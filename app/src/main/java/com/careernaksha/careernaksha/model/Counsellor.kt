package com.careernaksha.careernaksha.model

import java.io.Serializable

data class Counsellor(
        val id: String = "",
        val email: String = "Not found",
        val expertise: ArrayList<String> = arrayListOf(),
        val fieldFocus: ArrayList<String> = arrayListOf(),
        val name: String = "Not found",
        val phoneNumber: String = "Not found",
        val profilePicture: String = "Not found",
        val summary: String = "Not found"
) : Serializable