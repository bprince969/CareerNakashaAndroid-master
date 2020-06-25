package com.careernaksha.careernaksha;


import static com.careernaksha.careernaksha.ProfileActivity.saveData;

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
public class ProfileFragment extends Fragment {


 TextView tvbuild;
 CardView cvbuild;
 FirebaseUser user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_pfofile, container, false);
       tvbuild=view.findViewById(R.id.tvbuild);
       cvbuild=view.findViewById(R.id.cvbuild);
       user= FirebaseAuth.getInstance().getCurrentUser();
       if(user!=null)
       {
           tvbuild.setText("Hi "+saveData.getName());
       }
       cvbuild.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new BasicFragment()).addToBackStack(null).commit();
           }
       });


       return view;
    }

}
