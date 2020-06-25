package com.careernaksha.careernaksha;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.careernaksha.careernaksha.model.Professional;
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
public class ProfessionalFragment extends Fragment {

EditText pr1,pr2,pr3,pr4,pr5,pr6;
TextView tp,tp1;
Button bpr;
FirebaseAuth auth;
DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_professional, container, false);
        pr1=view.findViewById(R.id.pr1);
        pr2=view.findViewById(R.id.pr2);
        pr3=view.findViewById(R.id.pr3);
        pr4=view.findViewById(R.id.pr4);
        pr5=view.findViewById(R.id.pr5);
        pr6=view.findViewById(R.id.pr6);

        bpr=view.findViewById(R.id.bpr);
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Profile");
        fetch();
        bpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pr1.getText().toString().equals("") || pr2.getText().toString().equals("") || pr3.getText().toString().equals("") || pr4.getText().toString().equals("")
                        || pr5.getText().toString().equals("") || pr6.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Please fill required fields of fill NA....!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseUser user=auth.getCurrentUser();
                    if(user!=null)
                    {
                        send();
                        fetch();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new InterestFragment()).commit();

                    }
                }
            }
        });
        return  view;
    }

    private void fetch() {
        FirebaseUser user=auth.getCurrentUser();
        databaseReference.child(user.getDisplayName()).child("professional").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String j1pos=dataSnapshot.child("j1pos").getValue(String.class);
                String j1loc=dataSnapshot.child("j1loc").getValue(String.class);
                String j1salary=dataSnapshot.child("j1salary").getValue(String.class);
                String j2pos=dataSnapshot.child("j2pos").getValue(String.class);
                String j2loc=dataSnapshot.child("j2loc").getValue(String.class);
                String j2salary=dataSnapshot.child("j2salary").getValue(String.class);
                pr1.setText(j1pos);
                pr2.setText(j1loc);
                pr3.setText(j1salary);
                pr4.setText(j2pos);
                pr5.setText(j2loc);
                pr6.setText(j2salary);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void send() {

        String j1pos=pr1.getText().toString();
        String j1loc=pr2.getText().toString();
        String j1salary=pr3.getText().toString();
        String j2pos=pr4.getText().toString();
        String j2loc=pr5.getText().toString();
        String j2salary=pr6.getText().toString();
        Professional professional=new Professional(j1pos,j1loc,j1salary,j2pos,j2loc,j2salary);
        FirebaseUser user=auth.getCurrentUser();
        databaseReference.child(user.getDisplayName()).child("professional").setValue(professional);
    }


}
