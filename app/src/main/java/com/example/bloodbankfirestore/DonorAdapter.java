package com.example.bloodbankfirestore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DatalistViewHolder> {


    private Context mCtx;
    private List<AdminDetail> AdminList;

    public DonorAdapter(Context mCtx, List<AdminDetail> AdminList) {
        this.mCtx = mCtx;
        this.AdminList = AdminList;
    }




    @Override
    public DatalistViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        return new DatalistViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.cardview, parent, false)
        );

    }

    @Override
    public void onBindViewHolder( DonorAdapter.DatalistViewHolder holder, int position) {

        AdminDetail adminDetail = AdminList.get(position);

        holder.name.setText("Name - " + adminDetail.getNamee());
        holder.address.setText("Address - " +adminDetail.getAddress());
        holder.mobilee.setText("Mobile - " +adminDetail.getMob());
        holder.bldgrp.setText("BloodGroup - " +adminDetail.getBldgrp());
        Log.d("check","imageurl  "+ adminDetail.getImageeurll() );
        Glide.with(mCtx).load(adminDetail.getImageeurll()).override(60, 60).centerCrop().into(holder.imageView);//to load image from url into imageview
        //Log.d("abcd123", "onBindViewHolder: "+itemsss.getVolumeInfo().getImagelinkss().getThumbnail());
//        holder.linearLayout.setVisibility(View.GONE);




    }

    @Override
    public int getItemCount() {
        return AdminList.size();
    }

    public class DatalistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView address;
        TextView mobilee;
        TextView bldgrp;
        ImageView imageView;

        public DatalistViewHolder( View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tx1);
            address = itemView.findViewById(R.id.tx2);
            mobilee = itemView.findViewById(R.id.tx3);
            bldgrp = itemView.findViewById(R.id.tx4);
            imageView = itemView.findViewById(R.id.imageView4);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            AdminDetail product = AdminList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, Showdata.class);
            intent.putExtra("listdata",  product);
            mCtx.startActivity(intent);

        }
    }
}

