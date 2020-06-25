package com.careernaksha.careernaksha.auth

import com.careernaksha.careernaksha.model.User

interface LoginCallback {
    fun onReceiveError(error: String)
    fun onReceiveUser(user: User)
    fun onStateChange(state: String?)
}