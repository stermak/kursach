package com.example.kursach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class LoginFragment extends Fragment {

    public EditText userName2;
    public EditText editTextPassword2;
    public Button btnlog;
    public TextView info2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userName2 = view.findViewById(R.id.userName2);
        editTextPassword2 = view.findViewById(R.id.editTextPassword2);
        btnlog = view.findViewById(R.id.btnlog);
        info2 = view.findViewById(R.id.info2);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d;
                String p;

                for(int i = 0; i < Data.usernameList.size(); i++){
                    d = Data.usernameList.get(i);
                    if(d.equals(userName2.getText().toString())){
                        for (int j = 0; j < Data.passwordList.size(); j++){
                            p = Data.passwordList.get(j);
                            if(p.equals(editTextPassword2.getText().toString())){
                                Data.log = Data.usernameList.get(i);
                                Data.Phone = Data.phoneList.get(i);
                                Data.accept = true;

                                Fragment fragment = new ProfileFragment();
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.HomeFragment, fragment);
                                ft.commit();
                            }
                            else {
                                info2.setText("Не верный пароль");
                            }
                        }
                        break;
                    }
                    else {
                        info2.setText("Нет такого имени");
                    }
                }
            }
        });
        return view;
    }
}