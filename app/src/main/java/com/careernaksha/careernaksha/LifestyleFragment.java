package com.careernaksha.careernaksha;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.careernaksha.careernaksha.model.Lifestyle;
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
public class LifestyleFragment extends Fragment {

EditText el1,el2,el3,el4,el5;
Button bl;
FirebaseAuth auth;
DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lifestyle, container, false);
        el1=view.findViewById(R.id.el1);
        el2=view.findViewById(R.id.el2);
        el3=view.findViewById(R.id.el3);
        el4=view.findViewById(R.id.el4);
        el5=view.findViewById(R.id.el5);
        bl=view.findViewById(R.id.bl);
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Profile");
        fetch();
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(el1.getText().toString().equals("") || el2.getText().toString().equals("") || el3.getText().toString().equals("") || el4.getText().toString().equals("")
                        || el5.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Please fill required fields or fill NA...!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseUser user=auth.getCurrentUser();
                    if(user!=null)
                    {
                        save();
                        fetch();
                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new CompletedFragment()).addToBackStack(null).commit();
                        Toast.makeText(getContext(), "Profile Successfully Updated..!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    private void fetch() {
        FirebaseUser user=auth.getCurrentUser();
        databaseReference.child(user.getDisplayName()).child("lifestyle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String entertainment=dataSnapshot.child("entertainment").getValue(String.class);
                String living=dataSnapshot.child("living").getValue(String.class);
                String food=dataSnapshot.child("food").getValue(String.class);
                String travel=dataSnapshot.child("travel").getValue(String.class);
                String miscellaneous=dataSnapshot.child("miscellaneous").getValue(String.class);
                el1.setText(entertainment);
                el2.setText(living);
                el3.setText(food);
                el4.setText(travel);
                el5.setText(miscellaneous);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void save() {

        String entertainment=el1.getText().toString();
        String living=el2.getText().toString();
        String food=el3.getText().toString();
        String travel=el4.getText().toString();
        String miscellaneous=el5.getText().toString();
        Lifestyle lifestyle=new Lifestyle(entertainment,living,food,travel,miscellaneous);
        FirebaseUser user=auth.getCurrentUser();
        databaseReference.child(user.getDisplayName()).child("lifestyle").setValue(lifestyle);

    }

}
