package com.example.myapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Art;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ArtViewHolder>{
    private List<Art> listUser;
    public ArtAdapter(List<Art> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public ArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.art, parent, false);
        return new ArtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtViewHolder holder, int position) {
        Art art = listUser.get(position);
        if(art == null){
            return;
        }
        Picasso.get().load(art.getUrl()).into(holder.url_Art);
    }

    @Override
    public int getItemCount() {
        if (listUser != null){
            return listUser.size();
        }
        return 0;
    }

    public void updateData(List<Art> newList) {
        listUser.clear();
        listUser.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ArtViewHolder extends RecyclerView.ViewHolder {

        private final ImageView url_Art;

        public ArtViewHolder(@NonNull View itemView) {
            super(itemView);
            url_Art = itemView.findViewById(R.id.url_art);
        }
    }
}
