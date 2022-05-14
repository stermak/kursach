package com.example.kursach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursach.Add.CreateFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SpisokActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    spisokAdapter adapter;
    DatabaseReference mbase;
    public Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spisok);

        mbase = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<spisok> options = new FirebaseRecyclerOptions.Builder<spisok>().setQuery(mbase, spisok.class).build();
        adapter = new spisokAdapter(options);
        recyclerView.setAdapter(adapter);
        btn4 = findViewById(R.id.btn4);


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpisokActivity.this, CreateFragment.class);
                startActivity(intent);
            }
        });
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}