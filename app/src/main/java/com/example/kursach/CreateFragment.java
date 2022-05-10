package com.example.kursach;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.Fragment;


public class CreateFragment extends Fragment {

    public EditText NameTechnics;
    public EditText NameCompany;
    public EditText DateWarranty;
    public Button btn_cr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        NameTechnics = view.findViewById(R.id.NameTechnics);
        NameCompany = view.findViewById(R.id.NameCompany);
        DateWarranty = view.findViewById(R.id.DateWarranty);
        btn_cr = view.findViewById(R.id.btn_cr);

        return view;
    }
}