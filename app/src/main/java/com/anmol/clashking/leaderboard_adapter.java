package com.anmol.clashking;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class leaderboard_adapter extends FirebaseRecyclerAdapter<leaderboard_model,leaderboard_adapter.Viewholder> {
    public leaderboard_adapter(@NonNull FirebaseRecyclerOptions<leaderboard_model> options) {
        super(options);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void onBindViewHolder(@NonNull leaderboard_adapter.Viewholder holder, @SuppressLint("RecyclerView") int position, @NonNull leaderboard_model model) {
        Picasso.get().load(model.getUrl()).placeholder(R.drawable.freelogo2__1_).into(holder.imageView);
        holder.position.setText(String.valueOf((getItemCount()-(position+1))+1));
        holder.name.setText(model.getName());
        if (TextUtils.isEmpty(model.getKills())){
            holder.kill.setText("0");
        }else{
            holder.kill.setText(model.getKills());
        }
    }
    @NonNull
    @Override
    public leaderboard_adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_raw,parent,false);
        return new Viewholder(view);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView kill,name,position;
        ImageView imageView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            kill=itemView.findViewById(R.id.kill);
            name=itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.imageView);
            position=itemView.findViewById(R.id.position);
        }
    }
}
