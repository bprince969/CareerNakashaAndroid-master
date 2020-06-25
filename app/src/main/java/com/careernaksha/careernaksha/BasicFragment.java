package com.careernaksha.careernaksha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.careernaksha.careernaksha.model.Basic;
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
public class BasicFragment extends Fragment {

EditText edit1,edit2,edit3,edit4;
Button btnbasic;
DatabaseReference databaseReference;
FirebaseAuth auth;
FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_basic, container, false);
        edit1=view.findViewById(R.id.edit1);
        edit2=view.findViewById(R.id.edit2);
        edit3=view.findViewById(R.id.edit3);
        edit4=view.findViewById(R.id.edit4);
        auth=FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Profile");
        btnbasic=view.findViewById(R.id.btnbasic);
        fetch();
        btnbasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") || edit3.getText().toString().equals("") || edit4.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please fill required fields or fill  NA...!!!", Toast.LENGTH_SHORT).show();
                } else {


                    if (user != null) {
                        send();
                        fetch();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PersonalFragment()).addToBackStack(null).commit();
                    }
                }
            }
        });
        edit1.setText("akshay");

        return view;
    }

    private void fetch() {

        databaseReference.child(user.getDisplayName()).child("basic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue(String.class);
                String email=dataSnapshot.child("email").getValue(String.class);
                String dob=dataSnapshot.child("dob").getValue(String.class);
                String phone=dataSnapshot.child("phone").getValue(String.class);
               edit1.setText(name);
               edit2.setText(email);
               edit3.setText(dob);
               edit4.setText(phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void send() {

        String name=edit1.getText().toString();
        String email=edit2.getText().toString();
        String dob=edit3.getText().toString();
        String phone=edit4.getText().toString();
        Basic basic=new Basic(name,email,dob,phone);
        FirebaseUser user=auth.getCurrentUser();
        databaseReference.child(user.getDisplayName()).child("basic").setValue(basic);


    }

}
