package com.example.kursach;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {


    public TextView login;
    public EditText yourPhone;

    MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        login = view.findViewById(R.id.nameLogin);
        yourPhone = view.findViewById(R.id.yourPhone);

        login.setText(Data.log);
        yourPhone.setText(Data.Phone);

        return view;
    }
}