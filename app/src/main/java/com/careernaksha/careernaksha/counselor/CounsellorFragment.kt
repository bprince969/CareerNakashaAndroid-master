package com.careernaksha.careernaksha.counselor


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES.N
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.careernaksha.careernaksha.R
import com.careernaksha.careernaksha.book_appointment.BookAppointmentActivity
import com.careernaksha.careernaksha.model.Counsellor
import kotlinx.android.synthetic.main.date_picker.view.*
import kotlinx.android.synthetic.main.fragment_counsellor.view.*
import java.util.*

class CounsellorFragment : Fragment() {

    private lateinit var viewModel: CounsellorViewModel
    private val counsellors = ArrayList<Counsellor>()
    private lateinit var adapter: CounsellorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CounsellorViewModel::class.java)
        viewModel.listener = object : CounsellorListener {
            override fun onReceiveError(error: String) {
                AlertDialog.Builder(context).setTitle("Error").setMessage(error).setPositiveButton("Ok") { _, _ -> }.show()
            }

            override fun onReceiveCounsellor(counsellors: ArrayList<Counsellor>) {
                counsellors.forEach {
                    this@CounsellorFragment.counsellors.add(it)
                }
                adapter.notifyDataSetChanged()
            }
        }

        adapter = CounsellorAdapter(counsellors) {
            if (Build.VERSION.SDK_INT >= N) {
                showDatePicker(it)
            } else {
                showDatePickerForBelowN(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_counsellor, container, false)

        val recyclerView = rootView.counsellorRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return rootView
    }

    @RequiresApi(N)
    private fun showDatePicker(counsellor: Counsellor) {
        val datePickerDialog = DatePickerDialog(context!!)
        datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            val intent = Intent(context, BookAppointmentActivity::class.java)
            intent.putExtra("date", "$dayOfMonth/$month/$year")
            intent.putExtra("counsellor", counsellor)
            startActivity(intent)
            Log.d("OnSetDate", "\nYEAR = $year, MONTH = $month, DayOfTheMonth = $dayOfMonth")
        }
        datePickerDialog.show()
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
    }

    @SuppressLint("InflateParams")
    private fun showDatePickerForBelowN(counsellor: Counsellor) {
        val layout = LayoutInflater.from(context).inflate(R.layout.date_picker, null, false)
        val datePicker = layout.datePicker
        layout.datePicker.minDate = System.currentTimeMillis() - 1000
        AlertDialog.Builder(context).setView(layout).setPositiveButton("Ok") { _, _ ->
            val intent = Intent(context, BookAppointmentActivity::class.java)
            intent.putExtra("date", "${datePicker.dayOfMonth}/${datePicker.month}/${datePicker.year}")
            intent.putExtra("counsellor", counsellor)
            startActivity(intent)
        }.show()
    }

}


