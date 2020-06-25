package com.careernaksha.careernaksha;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.careernaksha.careernaksha.model.Personal;
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
public class PersonalFragment extends Fragment {

EditText ep1,ep2,ep3,ep4,ep5,ep6,ep7,ep8,ep9,ep10,ep11,ep12;
Button bp;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_personal, container, false);
       ep1=view.findViewById(R.id.ep1);
        ep2=view.findViewById(R.id.ep2);
        ep3=view.findViewById(R.id.ep3);
        ep4=view.findViewById(R.id.ep4);
        ep5=view.findViewById(R.id.ep5);
        ep6=view.findViewById(R.id.ep6);
        ep7=view.findViewById(R.id.ep7);
        ep8=view.findViewById(R.id.ep8);
        ep9=view.findViewById(R.id.ep9);
        ep10=view.findViewById(R.id.ep10);
        ep11=view.findViewById(R.id.ep11);
        ep12=view.findViewById(R.id.ep12);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Profile");
        bp=view.findViewById(R.id.bp);
        fetch();
        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ep1.getText().toString().equals("") || ep2.getText().toString().equals("") || ep3.getText().toString().equals("") || ep4.getText().toString().equals("") ||
                        ep5.getText().toString().equals("") || ep6.getText().toString().equals("") || ep7.getText().toString().equals("") || ep8.getText().toString().equals("") ||
                        ep9.getText().toString().equals("") || ep10.getText().toString().equals("") || ep11.getText().toString().equals("") || ep12.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Please fill required fields or fill  NA...!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(user!=null)
                    {
                        save();
                        fetch();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new EducationFragment()).addToBackStack(null).commit();
                    }
                }
            }
        });

       return view;
    }

    private void fetch() {


        databaseReference.child(user.getDisplayName()).child("personal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String g=dataSnapshot.child("gender").getValue(String.class);
                String mar=dataSnapshot.child("marital").getValue(String.class);
                String family=dataSnapshot.child("familymembers").getValue(String.class);
                String occ=dataSnapshot.child("occupation").getValue(String.class);
                String familyincome=dataSnapshot.child("familyincome").getValue(String.class);
                String indi=dataSnapshot.child("indivisual").getValue(String.class);
                String highest=dataSnapshot.child("highesteducation").getValue(String.class);
                String facebook=dataSnapshot.child("facebook").getValue(String.class);
                String linked=dataSnapshot.child("linkedin").getValue(String.class);
                String twit=dataSnapshot.child("twitter").getValue(String.class);
                String add=dataSnapshot.child("address").getValue(String.class);
                String skills=dataSnapshot.child("skills").getValue(String.class);


                ep1.setText(g);
                ep2.setText(mar);
                ep3.setText(family);
                ep4.setText(occ);
                ep5.setText(familyincome);
                ep6.setText(indi);
                ep7.setText(highest);
                ep8.setText(facebook);
                ep9.setText(linked);
                ep10.setText(twit);
                ep11.setText(add);
                ep12.setText(skills);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void save() {

        String gender=ep1.getText().toString();
        String marital=ep2.getText().toString();
        String familymembers=ep3.getText().toString();
        String occupation=ep4.getText().toString();
        String familyincome=ep5.getText().toString();
        String indivisual=ep6.getText().toString();
        String highesteducation=ep7.getText().toString();
        String facebook=ep8.getText().toString();
        String linkedin =ep9.getText().toString();
        String twitter=ep10.getText().toString();
        String address=ep11.getText().toString();
        String skills =ep12.getText().toString();
        Personal personal=new Personal(gender,marital,familymembers,occupation,familyincome,indivisual,highesteducation,facebook,linkedin,twitter,address,skills);

        databaseReference.child(user.getDisplayName()).child("personal").setValue(personal);
    }

}
