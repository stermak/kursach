package com.example.kursach.Add;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kursach.LoadingDialog;
import com.example.kursach.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class CreateFragment extends Fragment {

    ImageView img;
    Boolean imgSet = false;
    Uri imgUri;
    ChipGroup chips;
    Button submitBtn;
    EditText name1, name2, desc, year;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    LoadingDialog loadingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create, container, false);
        img = root.findViewById(R.id.imageView2);
        chips = root.findViewById(R.id.chips);
        submitBtn = root.findViewById(R.id.button2);
        name1 = root.findViewById(R.id.NameTechnics);
        name2 = root.findViewById(R.id.NameCompany);
        year = root.findViewById(R.id.year);
        desc = root.findViewById(R.id.desc);
        storage = FirebaseStorage.getInstance();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        loadingDialog = new LoadingDialog(getActivity());

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("warranty/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "warranty"), 1);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameTechnics = name1.getText().toString();
                if(NameTechnics.isEmpty()){
                    name1.setError("Name needed");
                    name1.requestFocus();
                    return;
                }
                String NameCompany = name2.getText().toString();
                if(NameCompany.isEmpty()){
                    name2.setError("Language needed");
                    name2.requestFocus();
                    return;
                }
                String DateWarranty = year.getText().toString();
                if(DateWarranty.isEmpty()){
                    year.setError("Year needed");
                    year.requestFocus();
                    return;
                }
                String description = desc.getText().toString();
                if(description.isEmpty()){
                    desc.setError("Please provide description");
                    desc.requestFocus();
                    return;
                }
                List<String> categories = getCatogories();
                if(categories.size() == 0){
                    Toast.makeText(getContext(), "Select atleast 1 category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!imgSet){
                    Toast.makeText(getContext(), "Please add image", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> docData = new HashMap<>();
                docData.put("name1", NameTechnics);
                docData.put("name2", NameCompany);
                docData.put("year", DateWarranty);
                docData.put("description", description);
                docData.put("categories", categories);

                uploadImage(docData);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) return;
        try{
            if(requestCode == 1){
                assert data != null;
                imgUri = data.getData();
                Transformation transformation = new RoundedCornersTransformation(30, 0);
                Picasso.get().load(imgUri).centerCrop().resize(720, 960).transform(transformation).into(img);
                imgSet = true;
            }
        } catch(Error e){
            //no handler necessary
        }

    }
    private List<String> getCatogories(){
        List<String> checked = new ArrayList<>();
        for(int i=0; i<chips.getChildCount(); i++){
            Chip chip = (Chip) chips.getChildAt(i);
            if(chip.isChecked()){
                checked.add(chip.getText().toString());
            }
        }
        return  checked;
    }

    private void uploadImage(Map<String, Object> docData){
        loadingDialog.startLoading();

        DocumentReference doc = db.collection("warranty").document();
        String docId = doc.getId();
        StorageReference imageRef = storageRef.child("warranty/"+docId);
        imageRef.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                docData.put("url", uri.toString());
                                uploadDocument(docData, docId);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Upload failed! try again", Toast.LENGTH_SHORT).show();
                        loadingDialog.stopLoading(false);
                    }
                });

    }
    private void uploadDocument(Map<String, Object> docData, String docId){
        loadingDialog.startLoading();
        db.collection("list")
                .document(docId)
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Period added successfully", Toast.LENGTH_SHORT).show();
                        clearForm();
                        loadingDialog.stopLoading(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed! try again", Toast.LENGTH_SHORT).show();
                        loadingDialog.stopLoading(false);
                    }
                });
    }
    private void clearForm(){
        img.setImageResource(R.drawable.ic_lmovie_placeholder);
        imgSet = false;
        name1.getText().clear();
        name2.getText().clear();
        year.getText().clear();
        desc.getText().clear();
        for(int i=0; i<chips.getChildCount(); i++){
            Chip chip = (Chip) chips.getChildAt(i);
            chip.setChecked(false);
        }
    }
}