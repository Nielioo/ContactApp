package com.snap.contact_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.snap.contact_app.model.User;

public class ContactActivity extends AppCompatActivity {

    private Toolbar contact_toolbar;
    private TextView contact_name_textView, contact_age_textView, contact_city_textView;
    private ImageView contact_edit_imageView, contact_delete_imageView;
    private User currentUser;
    private int position;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initialize();
        setClickListener();
        setCallBack();
    }

    private void setCallBack() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    // request code(1 = new ; 2 = edit; 3 = delete)
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 2) {
                            User updatedUser = result.getData().getParcelableExtra("updatedUser");
                            intent = new Intent();
                            intent.putExtra("updatedUser", updatedUser);
                            intent.putExtra("position", position);
                            setResult(2, intent);
                            finish();
                        }

                    }
                });
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
                currentUser = intent.getParcelableExtra("currentUser");
                intent = new Intent(getBaseContext(), AddContactActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("currentUser", currentUser);
                intent.putExtra("action", "edit");
                activityResultLauncher.launch(intent);
            }
        });

        contact_delete_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adialog = new AlertDialog.Builder(ContactActivity.this);
                adialog.setTitle("Are you sure?");
                adialog.setMessage("Deleting this contact will result in completely removing your contact information from the system and you won't be able to access it anymore.");
                adialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("position", position);
                        setResult(3,intent);
                        finish();
                    }
                });
                adialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface adialog, int which) {
                        adialog.dismiss();
                    }
                });

                AlertDialog alertDialog = adialog.create();
                alertDialog.show();
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
        position = intent.getIntExtra("position", -1);
        currentUser = intent.getParcelableExtra("currentUser");
        contact_name_textView.setText(currentUser.getName());
        contact_age_textView.setText(currentUser.getAge());
        contact_city_textView.setText(currentUser.getCity());
    }
}