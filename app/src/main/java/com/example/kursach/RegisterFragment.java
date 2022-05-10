package com.example.kursach;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {

    public EditText userName;
    public EditText editTextPhone;
    public EditText editTextPassword;
    public TextView info;
    public Button btn_reg;
    public Button btn_log;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        userName = view.findViewById(R.id.userName);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        btn_reg = view.findViewById(R.id.btn_reg);
        btn_log = view.findViewById(R.id.btn_log);
        info = view.findViewById(R.id.info);

        btn_reg.setOnClickListener(view1 -> {

            Pattern p = Pattern.compile("^((\\+7)([0-9]){10})$");
            Matcher m = p.matcher(editTextPhone.getText());

            if (userName.getText().toString().isEmpty() || editTextPhone.getText().toString().isEmpty()
                    || editTextPassword.getText().toString().isEmpty()){
                info.setText("Заполните все поля");
            }
            else {
                if(!Data.usernameList.contains(userName.getText().toString())){
                    info.setText("Регистрация пройдена");
                    if(userName.getText().length() < 15 && m.matches() && editTextPassword.getText().length() > 5){
                        Data.usernameList.add(userName.getText().toString());
                        Data.phoneList.add(editTextPhone.getText().toString());
                        Data.passwordList.add(editTextPassword.getText().toString());
                    }
                    else if (userName.getText().length() > 15){
                        info.setText("Слишком большое имя пользователя");
                    }
                    else if (!m.matches()){
                        info.setText("Неккоректный номер телефона");
                    }
                    else if(editTextPassword.getText().length() < 5){
                        info.setText("Пароль слишком короткий");
                    }

                }
                else {
                    info.setText("Логин занят");
                }
            }

            System.out.println(Data.usernameList);
            System.out.println(Data.passwordList);

        });

        btn_log.setOnClickListener(view12 -> {
            Fragment fragment = new LoginFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.HomeFragment, fragment);
            ft.commit();
        });
        return view;
    }
}
