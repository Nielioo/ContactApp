package com.snap.contact_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.snap.contact_app.model.User;

public class AddContactActivity extends AppCompatActivity {

    private Toolbar add_contact_toolbar;
    private TextInputLayout add_name_textInputLayout, add_age_textInputLayout, add_city_textInputLayout;
    private Button add_contact_button;
    private TextWatcher tmpWatcher;
    private String action;
    private User currentUser;
    private int position;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initialize();
        setUi();
        setListener();
    }

    private void setUi() {
        if(action.equalsIgnoreCase("add")){
            add_contact_toolbar.setTitle("Add Contact");
            add_contact_button.setText("Add Contact");

            setButton(false);

        } else if (action.equalsIgnoreCase("edit")) {
            add_contact_toolbar.setTitle("Edit Contact");
            add_contact_button.setText("Edit Contact");

            add_name_textInputLayout.getEditText().setText(currentUser.getName());
            add_age_textInputLayout.getEditText().setText(currentUser.getAge());
            add_city_textInputLayout.getEditText().setText(currentUser.getCity());

            setButton(true);

        }
    }

    private void setButton(boolean tmp) {
        add_contact_button.setEnabled(tmp);
        add_contact_button.setClickable(tmp);
    }

    private void setListener() {

        if(action.equalsIgnoreCase("add")){
            add_contact_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getBaseContext(), MainActivity.class);
                    finish();
                }
            });
        } else if(action.equalsIgnoreCase("edit")){
            add_contact_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getBaseContext(), ContactActivity.class);
                    finish();
                }
            });
        }


        tmpWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = add_name_textInputLayout.getEditText().getText().toString().trim();
                String age = add_age_textInputLayout.getEditText().getText().toString().trim();
                String city = add_city_textInputLayout.getEditText().getText().toString().trim();

                if (!(name.isEmpty() && age.isEmpty() && city.isEmpty())) {
                    setButton(true);
                } else {
                    setButton(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        add_name_textInputLayout.getEditText().addTextChangedListener(tmpWatcher);
        add_age_textInputLayout.getEditText().addTextChangedListener(tmpWatcher);
        add_city_textInputLayout.getEditText().addTextChangedListener(tmpWatcher);

        add_contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_name_textInputLayout.getEditText().getText().toString().trim();
                String age = add_age_textInputLayout.getEditText().getText().toString().trim();
                String city = add_city_textInputLayout.getEditText().getText().toString().trim();

                if(action.equalsIgnoreCase("add")){
                    User newUser = new User(name, age, city);
                    intent = new Intent();
                    intent.putExtra("newUser", newUser);
                    setResult(1, intent);
                    finish();
                } else if(action.equalsIgnoreCase("edit")){
                    User updatedUser = new User(name, age, city);
//                    currentUser.setName(name);
//                    currentUser.setAge(age);
//                    currentUser.setCity(city);
                    intent = new Intent();
                    intent.putExtra("updatedUser", updatedUser);
                    intent.putExtra("position", position);
                    setResult(2, intent);
                    finish();
                }


            }
        });

    }

    private void initialize() {
        add_contact_toolbar = findViewById(R.id.add_contact_toolbar);
        add_name_textInputLayout = findViewById(R.id.add_name_textInputLayout);
        add_age_textInputLayout = findViewById(R.id.add_age_textInputLayout);
        add_city_textInputLayout = findViewById(R.id.add_city_textInputLayout);
        add_contact_button = findViewById(R.id.add_contact_button);

        intent = getIntent();
        action = intent.getStringExtra("action");
        position = intent.getIntExtra("position",-1);
        currentUser= intent.getParcelableExtra("currentUser");
    }
}