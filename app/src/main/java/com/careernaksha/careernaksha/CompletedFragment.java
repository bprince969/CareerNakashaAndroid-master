package com.careernaksha.careernaksha;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFragment extends Fragment {

CardView cvbuild1;
TextView tvbuild;
FirebaseUser user;
    public CompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_completed, container, false);
        cvbuild1=view.findViewById(R.id.cvbuild1);
        tvbuild=view.findViewById(R.id.tvbuild);
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String name=user.getDisplayName();
            tvbuild.setText("Hi "+name);
        }
        cvbuild1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new BasicFragment()).commit();
            }
        });
        return view;
    }



}
