package com.anmol.clashking;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class matchadapter extends FirebaseRecyclerAdapter<matchmodel,matchadapter.Viewholder> {
    public matchadapter(@NonNull FirebaseRecyclerOptions<matchmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull matchadapter.Viewholder holder, int position, @NonNull matchmodel model) {
        DatabaseReference join=FirebaseDatabase.getInstance().getReference("Joined/"+model.getTitle());
        holder.status.setText(model.getStatus());
        holder.fee.setText(model.getFee());
        holder.title.setText(model.getTitle());
        holder.size.setText("0/"+model.getSize());
        holder.kill.setText(model.getKill());
        holder.win.setText(model.getPrize());
        holder.bar.setMax(Integer.parseInt(model.getSize()));
        EditText text=holder.dialog.findViewById(R.id.username);
        Button enter=holder.dialog.findViewById(R.id.enter);
        TextInputLayout layout=holder.dialog.findViewById(R.id.textInputLayout);
        join.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Long l=snapshot.getChildrenCount();
                    holder.size.setText(String.valueOf(l)+"/"+model.getSize());
                    int p=Integer.parseInt(String.valueOf(l));
                    holder.bar.setProgress(p);
                }else{
                    holder.size.setText("0/"+model.getSize());
                    holder.bar.setProgress(0);
                }
                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()){
                    holder.button.setEnabled(false);
                    holder.button.setText("JOINED");
                }else{
                    holder.button.setText("JOIN");
                    holder.button.setEnabled(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(holder.size.getText(),model.getSize()+"/"+model.getSize())){
                    Snackbar.make(view.getRootView(),"Sorry! you are late", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/amount");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            if (Integer.parseInt(model.getFee())<=Integer.parseInt(snapshot.getValue().toString())){
                                int a=Integer.parseInt(snapshot.getValue().toString())-Integer.parseInt(model.getFee());
                                reference.setValue(String.valueOf(a));
                                Toast.makeText(holder.itemView.getContext(), "Amount debited", Toast.LENGTH_SHORT).show();
                               holder.dialog.show();
                            }else{
                                Snackbar.make(view.getRootView(),"Insufficient amount", BaseTransientBottomBar.LENGTH_SHORT).show();
                            }
                        }else{
                            Snackbar.make(view.getRootView(),"Add Amount", BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                ProgressDialog progressDialog=new ProgressDialog(holder.itemView.getContext());
                progressDialog.setMessage("Joining...");
                progressDialog.setCancelable(false);
                progressDialog.create();
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(text.getText())){
                            progressDialog.show();
                            join.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"/username").setValue(text.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            holder.dialog.dismiss();
                                            Snackbar.make(view.getRootView(),"Successfully Joined",BaseTransientBottomBar.LENGTH_SHORT).show();
                                        }
                                    },3000);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(view.getRootView(),e.getMessage().toString(),BaseTransientBottomBar.LENGTH_SHORT)
                                            .setBackgroundTint(Color.RED).show();
                                    progressDialog.dismiss();
                                    holder.dialog.dismiss();
                                }
                            });
                        }else{
                            layout.setError("");
                        }
                    }
                });
            }
        });
        DatabaseReference room=FirebaseDatabase.getInstance().getReference("Room/"+model.getTitle());
        room.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.room.setText(snapshot.child("id").getValue().toString());
                    holder.pass.setText(snapshot.child("pass").getValue().toString());
                    holder.room.setEnabled(false);
                    holder.pass.setEnabled(false);
                    holder.matchname.setText(model.getTitle());
                    join.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()){
                                holder.RoomDialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager=(ClipboardManager)holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("id and password",holder.room.getText()+","+holder.pass.getText());
               manager.setPrimaryClip(clip);
                Toast.makeText(holder.itemView.getContext(), "Id Pass copied to clipboard", Toast.LENGTH_SHORT).show();
                holder.RoomDialog.dismiss();
            }
        });
    }

    @NonNull
    @Override
    public matchadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.matchcard,parent,false);
        return new Viewholder(view);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        Button button,copy;
        TextView title,status,kill,win,fee,size,matchname;
        ProgressBar bar;
        Dialog RoomDialog;
        Dialog dialog;
        EditText room,pass;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.button2);
            title=itemView.findViewById(R.id.title);
            status=itemView.findViewById(R.id.statust);
            kill=itemView.findViewById(R.id.kill);
            win=itemView.findViewById(R.id.win);
            fee=itemView.findViewById(R.id.fee);
            size=itemView.findViewById(R.id.progress);
            bar=itemView.findViewById(R.id.progressBar2);
            RoomDialog=new Dialog(itemView.getContext());
            RoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            RoomDialog.setContentView(R.layout.room_id);
            RoomDialog.setCancelable(false);
            matchname=RoomDialog.findViewById(R.id.matchname);
            copy=RoomDialog.findViewById(R.id.copy);
            room=RoomDialog.findViewById(R.id.roomid);
            pass=RoomDialog.findViewById(R.id.pass);
            RoomDialog.create();
            dialog=new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.matchjoindialog);
            dialog.setCancelable(false);
            dialog.create();
        }
    }
}
