package com.careernaksha.careernaksha;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.careernaksha.careernaksha.counselor.CounsellorFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareerOptionsFragment extends Fragment {
    Button option;

    public CareerOptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_career_options, container, false);
        option=view.findViewById(R.id.option);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new CounsellorFragment()).addToBackStack(null).commit();

            }
        });
        return view;
    }

}
