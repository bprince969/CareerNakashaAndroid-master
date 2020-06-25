package com.careernaksha.careernaksha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class SearchFragment extends Fragment {

      private AutoCompleteTextView autoCompleteTextView;
      RecyclerView rv;
      DatabaseReference databaseReference;
      DatabaseReference databaseReference1;
      FirebaseAuth auth;
      FirebaseUser user;
      ArrayList<String>CollegeName;
      ArrayList<String>Exam;
      ArrayList<String>Loc;
      SearchAdapter searchAdapter;

      ImageButton imgButton;

      ListView listView;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView1);
        listView= view.findViewById(R.id.list);
        //rv=view.findViewById(R.id.rv)
        // rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        auth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("College");

        CollegeName=new ArrayList<>();
        Exam=new ArrayList<>();
        Loc=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String ,String> map= (Map<String, String>) dataSnapshot.getValue();
                for(Map.Entry m:map.entrySet()){
                    //System.out.println(m.getKey()+" "+m.getValue());
                    CollegeName.add((String) m.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final ArrayAdapter adapter = new
                ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,CollegeName);
        listView.setAdapter(adapter);

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        imgButton =view.findViewById(R.id.search);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val=autoCompleteTextView.getText().toString().trim();
                if(!val.equals(""))
                {
                    databaseReference1= databaseReference.child(val);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String ,String> map1= (Map<String, String>) dataSnapshot.getValue();
                            for(Map.Entry m:map1.entrySet()){
                                //System.out.println(m.getKey()+" "+m.getValue());
                                Exam.add((String) m.getKey());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    ArrayAdapter adapter1 = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,Exam);
                    listView.setAdapter(adapter1);
                }
                else
                {
                    Toast.makeText(getContext(),"Please search something...",Toast.LENGTH_SHORT).show();
                }

            }
        });

        




//        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(!s.toString().isEmpty())
//                {setAdapter(s.toString());
//
//                }
//                else
//                {
//                    CollegeName.clear();
//                    Exam.clear();
//                    Loc.clear();
//                    rv.removeAllViews();
//                }
//
//            }
//        });
        return view;
    }



//    private void setAdapter(final String string) {
//        user=auth.getCurrentUser();
//        databaseReference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                CollegeName.clear();
//                Exam.clear();
//                Loc.clear();
//                rv.removeAllViews();
//                int counter=0;
//                for(DataSnapshot snapshot: dataSnapshot.getChildren())
//                {
//                    String uid=snapshot.getKey();
//                    String name=snapshot.child("collegename").getValue(String.class);
//                    String email=snapshot.child("exams_fee_mode_duration").getValue(String.class);
//                    String loc=snapshot.child("location").getValue(String.class);
//
//                    if(name.toLowerCase().contains(string.toLowerCase()))
//                    {
//
//                        CollegeName.add(name);
//                        Exam.add(email);
//                        Loc.add(loc);
//                        counter++;
//                    }
//                   else if(name.toLowerCase().equals(string.toLowerCase()))
//                    {
//                        CollegeName.add(name);
//                        Exam.add(email);
//                        Loc.add(loc);
//                        counter++;
//                    }
//
//                       else if (!name.toLowerCase().contains(string.toLowerCase())) {
//                            //  Toast.makeText(getContext(), "Showing results for..", Toast.LENGTH_SHORT).show();
//                            String st = string.substring(0, 1);
//                            if (name.toLowerCase().equals(st.toLowerCase())) {
//
//                                CollegeName.add(name);
//                                Exam.add(email);
//                                Loc.add(loc);
//                                counter++;
//                            }
//                            else if(name.substring(0,1).toLowerCase().contains(st.toLowerCase()))
//                            {
//
//                                CollegeName.add(name);
//                                Exam.add(email);
//                                Loc.add(loc);
//                                counter++;
//                            }
//
//                        }
//
//
//
//                    if(counter==15)
//                        break;
//                }
//                searchAdapter=new SearchAdapter(getContext(),CollegeName,Exam,Loc);
//                rv.setAdapter(searchAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//   }

}
