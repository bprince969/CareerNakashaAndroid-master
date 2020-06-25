package com.careernaksha.careernaksha;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThankyouFragment extends Fragment {
TextView textday,texttime;FirebaseAuth auth;FirebaseUser user;DatabaseReference databaseReference1,databaseReference;
    public ThankyouFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_thankyou, container, false);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        databaseReference1= FirebaseDatabase.getInstance().getReference().child("Nimish");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Nimish");

       // fetch();
       // fetch1();
        return view;
    }

    private void fetch1() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String day=dataSnapshot.child("Monday").getKey().toString();

                textday.setText(day+":");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetch() {

        databaseReference1.child("Monday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String time=dataSnapshot.child("8am").getKey().toString();
                texttime.setText(time);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
