package com.snap.contact_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.snap.contact_app.model.User;

public class ContactActivity extends AppCompatActivity {

    private Toolbar contact_toolbar;
    private TextView contact_name_textView, contact_age_textView, contact_city_textView;
    private ImageView contact_edit_imageView, contact_delete_imageView;
    private User currentUser;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initialize();
        setClickListener();
    }

    private void setClickListener() {
        contact_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), MainActivity.class);
                finish();
            }
        });

        contact_edit_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = intent.getIntExtra("position", -1);
                currentUser = intent.getParcelableExtra("currentUser");
                intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("currentUser", currentUser);
                finish();
            }
        });

        contact_delete_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = intent.getIntExtra("position", -1);
                intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("position", position);
                finish();
            }
        });

    }

    private void initialize() {
        contact_toolbar = findViewById(R.id.contact_toolbar);
        contact_name_textView = findViewById(R.id.contact_name_textView);
        contact_age_textView = findViewById(R.id.contact_age_textView);
        contact_city_textView = findViewById(R.id.contact_city_textView);
        contact_edit_imageView = findViewById(R.id.contact_edit_imageView);
        contact_delete_imageView = findViewById(R.id.contact_delete_imageView);

        intent = getIntent();
        currentUser = intent.getParcelableExtra("currentUser");
        contact_name_textView.setText(currentUser.getName());
        contact_age_textView.setText(currentUser.getAge());
        contact_city_textView.setText(currentUser.getCity());
    }
}