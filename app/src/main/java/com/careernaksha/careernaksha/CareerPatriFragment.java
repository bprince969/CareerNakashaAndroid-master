package com.careernaksha.careernaksha;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.careernaksha.careernaksha.counselor.CounsellorFragment;

public class CareerPatriFragment extends Fragment {

Button patri;
    public CareerPatriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_career_patri, container, false);
patri=view.findViewById(R.id.patri);
patri.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new CounsellorFragment()).addToBackStack(null).commit();
    }
});
        return view;
    }

}
