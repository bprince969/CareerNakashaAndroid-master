package com.careernaksha.careernaksha.book_appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.careernaksha.careernaksha.R
import com.careernaksha.careernaksha.ThankyouFragment
import com.careernaksha.careernaksha.model.Counsellor
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.fragment_pick_time.*


class BookAppointmentActivity : AppCompatActivity() {

    private lateinit var date: String
    private lateinit var viewModel: BookAppointmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pick_time)
        date = intent.getStringExtra("date")

        viewModel = ViewModelProviders.of(this).get(BookAppointmentViewModel::class.java)
        viewModel.counsellor = intent.getSerializableExtra("counsellor") as Counsellor

        viewModel.listener = object : BookAppointmentCallback {

            override fun onReceiveError(error: String) {
                AlertDialog.Builder(this@BookAppointmentActivity).setTitle("Error").setMessage(error).show()
            }

            override fun onAddSlot(result: DocumentReference) {
                pickTimeFrameLayout.visibility = View.VISIBLE
                Toast.makeText(this@BookAppointmentActivity, "Slot booked", Toast.LENGTH_SHORT).show()
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(R.id.pickTimeFrameLayout, ThankyouFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                this@BookAppointmentActivity.setContentView(R.layout.fragment_pick_time)
            }

        }

        btn8.setOnClickListener {
            viewModel.addNewSlot(date, "8:00 AM")
        }
        btnone.setOnClickListener {
            viewModel.addNewSlot(date, "1:30 PM")
        }
        btn4.setOnClickListener {
            viewModel.addNewSlot(date, "4:30 PM")
        }
        btn8pm.setOnClickListener {
            viewModel.addNewSlot(date, "8:00 PM")
        }

    }

    override fun onBackPressed() {
        this.finish()
    }


}
