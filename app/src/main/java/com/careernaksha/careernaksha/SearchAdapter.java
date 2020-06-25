package com.careernaksha.careernaksha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Search> {
    Context context;
    ArrayList<String> CollegeName;
    ArrayList<String>Exam;
    ArrayList<String>Loc;
    class Search extends RecyclerView.ViewHolder{
        TextView tvname;
        TextView tvemail;
        TextView tvloc;

        public Search(View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.tvname);
            tvemail=itemView.findViewById(R.id.tvemail);
            tvloc=itemView.findViewById(R.id.tvloc);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> name, ArrayList<String> email, ArrayList<String> loc) {
        this.context = context;
        CollegeName = name;
        Exam = email;
        Loc=loc;
    }

    @NonNull
    @Override
    public SearchAdapter.Search onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.searchadapter,parent,false);
        return new SearchAdapter.Search(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Search holder, int position) {
        holder.tvname.setText(CollegeName.get(position));
        holder.tvemail.setText(Exam.get(position));
        holder.tvloc.setText(Loc.get(position));

    }



    @Override
    public int getItemCount() {
        return CollegeName.size();
    }
}
