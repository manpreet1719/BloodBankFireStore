package com.example.bloodbankfirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class dataList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private List<AdminDetail> adminDetailList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        recyclerView = findViewById(R.id.rcly);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminDetailList = new ArrayList<>();
        donorAdapter = new DonorAdapter(this, adminDetailList);
        recyclerView.setAdapter(donorAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("admindata").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                        if(!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){

                                AdminDetail p = d.toObject(AdminDetail.class);
                                p.setId(d.getId());
                                adminDetailList.add(p);

                            }

                            donorAdapter.notifyDataSetChanged();

                        }


                    }
                });


    }
}