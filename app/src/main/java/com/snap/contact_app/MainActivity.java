package com.snap.contact_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.snap.contact_app.model.OnCardClickListener;
import com.snap.contact_app.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnCardClickListener {

    private RecyclerView main_recyclerView;
    private ContactRVAdapter adapter;
    private FloatingActionButton main_fab;
    private TextView main_noContact_textView;
    private ArrayList<User> userList;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setHint();
        setRecyclerView();
        setClickListener();
        setCallback();
    }

    private void setCallback() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    // request code(1 = new ; 2 = edit; 3 = delete)
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 1) {
                            User user = result.getData().getParcelableExtra("newUser");
                            userList.add(user);
                            adapter.notifyDataSetChanged();

                            setHint();
                        } else if (result.getResultCode() == 2) {
                            User updatedUser = result.getData().getParcelableExtra("updatedUser");
                            int position = result.getData().getIntExtra("position",-1);
                            userList.set(position, updatedUser);
                            adapter.notifyDataSetChanged();

                            setHint();
                        } else if (result.getResultCode() == 3) {
                            int position = result.getData().getIntExtra("position", -1);
                            userList.remove(position);
                            adapter.notifyDataSetChanged();

                            setHint();
                        }

                    }
                });
    }

    private void setClickListener() {
        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), AddContactActivity.class);
                intent.putExtra("action", "add");
                activityResultLauncher.launch(intent);
            }
        });


    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext());
        main_recyclerView.setLayoutManager(manager);
        main_recyclerView.setAdapter(adapter);
    }

    private void setHint() {
        if (userList.isEmpty()) {
            main_noContact_textView.setVisibility(View.VISIBLE);
        } else {
            main_noContact_textView.setVisibility(View.INVISIBLE);
        }
    }

    private void initialize() {
        main_recyclerView = findViewById(R.id.main_recyclerView);
        main_fab = findViewById(R.id.main_fab);
        main_noContact_textView = findViewById(R.id.main_noContact_textView);
        intent = getIntent();
        userList = new ArrayList<>();
        adapter = new ContactRVAdapter(userList, this);
    }

    @Override
    public void onClick(int position) {
        User currentUser = userList.get(position);
        intent = new Intent(getBaseContext(), ContactActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("position", position);
        activityResultLauncher.launch(intent);
    }

}