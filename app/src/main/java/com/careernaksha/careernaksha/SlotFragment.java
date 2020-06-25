package com.careernaksha.careernaksha;



import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlotFragment extends Fragment {


    CardView card1,card2,card3,card4,card5,card6,card7,car1,car2,car3,car4,car5,car6;
    long count=0;
    FirebaseAuth auth;
    DatabaseReference databaseReference1,databaseReference2,databaseReference3,databaseReference4,databaseReference5;;
 long val,val1;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_slot, container, false);
        card1=view.findViewById(R.id.card1);
        card2=view.findViewById(R.id.card2);
        card3=view.findViewById(R.id.card3);
        card4=view.findViewById(R.id.card4);
        card5=view.findViewById(R.id.card5);
        card6=view.findViewById(R.id.card6);
        card7=view.findViewById(R.id.card7);
        auth=FirebaseAuth.getInstance();
        databaseReference1= FirebaseDatabase.getInstance().getReference().child("Nimish");
        databaseReference2=FirebaseDatabase.getInstance().getReference().child("Shikha");
        databaseReference3=FirebaseDatabase.getInstance().getReference().child("Sumit");
        databaseReference4=FirebaseDatabase.getInstance().getReference().child("Shiva");
        databaseReference5=FirebaseDatabase.getInstance().getReference().child("Deepa");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            /*    val=dataSnapshot.child("Mon6").getValue(long.class);
                val1=dataSnapshot.child("Mon6:30").getValue(long.class);*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                View vi = LayoutInflater.from(getContext()).inflate(R.layout.timeslot, null);

                TimePicker timePicker = vi.findViewById(R.id.tpp);
                car1 = vi.findViewById(R.id.car1);
                car2 = vi.findViewById(R.id.car2);
                car3 = vi.findViewById(R.id.car3);
                car4 = vi.findViewById(R.id.car4);
                car5 = vi.findViewById(R.id.car5);
                car6 = vi.findViewById(R.id.car6);
                FirebaseUser user = auth.getCurrentUser();
                // databaseReference.child(user.getDisplayName()).setValue(count);

                car2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = auth.getCurrentUser();
                        count = 1;


                        if (val == 1) {
                            Toast.makeText(getContext(), "Already Booked", Toast.LENGTH_SHORT).show();
                        } else {

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ThankyouFragment()).addToBackStack(null).commit();
                           databaseReference1.child("Mon6").setValue(count);
                            databaseReference1.child(user.getDisplayName()).setValue(user.getEmail());

                        }
                    }

                });

                car3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseUser user=auth.getCurrentUser();
                        count=1;
                        if(val1==1)
                        {
                            Toast.makeText(getContext(), "Already Booked", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ThankyouFragment()).addToBackStack(null).commit();
                           databaseReference1.child("Mon6:30").setValue(count);
                            databaseReference1.child(user.getDisplayName()).setValue(user.getEmail());

                        }
                    }
                });
                car4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                ab.setView(vi);
                ab.create();
                ab.show();



            }
        });
        //final FirebaseUser user=auth.getCurrentUser();
        //databaseReference.child(user.getDisplayName()).setValue(count);








        return view;

    }



    @Override
    public void onStart() {
        super.onStart();

          final FirebaseUser user=auth.getCurrentUser();
        //databaseReference.child(user.getDisplayName()).setValue(count);



        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               val=dataSnapshot.child("Mon6").getValue(long.class);
              val1=dataSnapshot.child("Mon6:30").getValue(long.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }

    @Override
    public void onResume() {
        super.onResume();

        final FirebaseUser user=auth.getCurrentUser();
        //databaseReference.child(user.getDisplayName()).setValue(count);



        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val=dataSnapshot.child("Mon6").getValue(long.class);
                val1=dataSnapshot.child("Mon6:30").getValue(long.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });



    }



    @Override
    public void onPause() {
        super.onPause();
          final FirebaseUser user=auth.getCurrentUser();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val=dataSnapshot.child("Mon6").getValue(long.class);
                val1=dataSnapshot.child("Mon6:30").getValue(long.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    @Override
    public void onStop() {
        super.onStop();

        final FirebaseUser user=auth.getCurrentUser();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val=dataSnapshot.child("Mon6").getValue(long.class);
                val1=dataSnapshot.child("Mon6:30").getValue(long.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final FirebaseUser user=auth.getCurrentUser();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val=dataSnapshot.child("Mon6").getValue(long.class);
                val1=dataSnapshot.child("Mon6:30").getValue(long.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }
}
