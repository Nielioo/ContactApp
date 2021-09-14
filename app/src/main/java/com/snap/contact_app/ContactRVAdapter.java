package com.snap.contact_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snap.contact_app.model.OnCardClickListener;
import com.snap.contact_app.model.User;

import java.util.ArrayList;

public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.ContactViewHolder> {

    private ArrayList<User> userList = new ArrayList<>();
    private OnCardClickListener cardListener;

    public ContactRVAdapter(ArrayList<User> userList, OnCardClickListener cardListener) {
        this.userList = userList;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_user, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.user_name_textView.setText(userList.get(position).getName());
        holder.user_age_textView.setText(String.valueOf(userList.get(position).getAge()));
        holder.user_city_textView.setText(userList.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView user_name_textView, user_age_textView, user_city_textView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name_textView = itemView.findViewById(R.id.user_name_textView);
            user_age_textView = itemView.findViewById(R.id.user_age_textView);
            user_city_textView = itemView.findViewById(R.id.user_city_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardListener.onClick(getAdapterPosition());
                }
            });

        }
    }
}
