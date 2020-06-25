package com.careernaksha.careernaksha.counselor

import com.careernaksha.careernaksha.model.Counsellor

interface  CounsellorListener{
    fun onReceiveError(error:String)
    fun onReceiveCounsellor(counsellors: ArrayList<Counsellor>)
}