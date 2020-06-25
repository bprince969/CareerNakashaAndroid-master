package com.careernaksha.careernaksha.counselor

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.careernaksha.careernaksha.R
import com.careernaksha.careernaksha.model.Counsellor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_counsellor.view.*

class CounsellorAdapter(private val counsellors: ArrayList<Counsellor>, private val onClickBook: (counsellor: Counsellor) -> Unit) : RecyclerView.Adapter<CounsellorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounsellorViewHolder {
        return CounsellorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_counsellor, parent, false))
    }

    override fun getItemCount(): Int = counsellors.size

    override fun onBindViewHolder(holder: CounsellorViewHolder, position: Int) {
        val counsellor = counsellors[position]
        Picasso.get().load(counsellor.profilePicture).resize(100,100).into(holder.profileImage)
        holder.name.text = counsellor.name
        holder.expertise.text = counsellor.expertise.toString().replace("[","").replace("]","")
        holder.summary.text = counsellor.summary
        holder.fields.text = counsellor.fieldFocus.toString().replace("[","").replace("]","")
        holder.bookBtn.setOnClickListener {
            onClickBook(counsellor)
        }
    }



}

class CounsellorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.cardCounsellorNameTV!!
    val profileImage = view.cardCounsellorProfileImage!!
    val expertise = view.cardCounsellorExpertise!!
    val summary = view.cardCounsellorSummery!!
    val fields = view.cardCounsellorFieldFocus!!
    val bookBtn = view.cardCounsellorBookBtn!!
}