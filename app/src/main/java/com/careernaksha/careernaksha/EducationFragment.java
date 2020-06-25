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
import com.careernaksha.careernaksha.model.Educational;
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
public class EducationFragment extends Fragment {

EditText ee1,ee2,ee3,ee4,ee5,ee6,ee7,ee8,ee9,ee10,ee11;
Button be;
TextView te,te1;
FirebaseAuth auth;
DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.fragment_education, container, false);
     ee1=view.findViewById(R.id.ee1);
        ee2=view.findViewById(R.id.ee2);
        ee3=view.findViewById(R.id.ee3);
        ee4=view.findViewById(R.id.ee4);
        ee5=view.findViewById(R.id.ee5);
        ee6=view.findViewById(R.id.ee6);
        ee7=view.findViewById(R.id.ee7);
        ee8=view.findViewById(R.id.ee8);
        ee9=view.findViewById(R.id.ee9);
        ee10=view.findViewById(R.id.ee10);
        ee11=view.findViewById(R.id.ee11);
        be=view.findViewById(R.id.be);

        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Profile");
        fetch();
        be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ee1.getText().toString().equals("") || ee2.getText().toString().equals("") || ee3.getText().toString().equals("") || ee4.getText().toString().equals("") ||
                        ee5.getText().toString().equals("") || ee6.getText().toString().equals("") || ee7.getText().toString().equals("") || ee8.getText().toString().equals("") ||
                        ee9.getText().toString().equals("") || ee10.getText().toString().equals("") || ee11.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Please fill required fields or fill  NA...!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseUser user=auth.getCurrentUser();
                    if(user!=null)
                    {
                        send();
                        fetch();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ProfessionalFragment()).addToBackStack(null).commit();
                    }
                }

            }
        });
        return view;

    }

    private void fetch() {
        FirebaseUser user=auth.getCurrentUser();
        databaseReference.child(user.getDisplayName()).child("education").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tenth=dataSnapshot.child("tenth").getValue(String.class);
                String twelth=dataSnapshot.child("twelth").getValue(String.class);
                String bfield=dataSnapshot.child("bfieldofstudy").getValue(String.class);
                String collegename=dataSnapshot.child("collegename").getValue(String.class);
                String mfield=dataSnapshot.child("mfieldofstudy").getValue(String.class);
                String nameofcolle=dataSnapshot.child("nameofcollege").getValue(String.class);
                String gre=dataSnapshot.child("gre").getValue(String.class);
                String gmat=dataSnapshot.child("gmat").getValue(String.class);
                String ielts=dataSnapshot.child("ielts").getValue(String.class);
                String toefl=dataSnapshot.child("toefl").getValue(String.class);
                String additional=dataSnapshot.child("additinalqualifiction").getValue(String.class);

                ee1.setText(tenth);
                ee2.setText(twelth);
                ee3.setText(bfield);
                ee4.setText(collegename);
                ee5.setText(mfield);
                ee6.setText(nameofcolle);
                ee7.setText(gre);
                ee8.setText(gmat);
                ee9.setText(ielts);
                ee10.setText(toefl);
                ee11.setText(additional);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void send() {
        String tenth=ee1.getText().toString();
       String twelth=ee2.getText().toString();
        String bfieldofstudy=ee3.getText().toString();
       String collegename=ee4.getText().toString();
       String mfieldofstudy=ee5.getText().toString();
        String nameofcollege=ee6.getText().toString();
        String gre=ee7.getText().toString();
        String gmat=ee8.getText().toString();
        String ielts=ee9.getText().toString();
        String toefl=ee10.getText().toString();
        String additinalqualifiction=ee11.getText().toString();
        FirebaseUser firebaseUser=auth.getCurrentUser();
        Educational educational=new Educational(tenth,twelth,bfieldofstudy,collegename,mfieldofstudy,nameofcollege,gre,gmat,ielts,toefl
        ,additinalqualifiction);
        databaseReference.child(firebaseUser.getDisplayName()).child("education").setValue(educational);
    }

}
