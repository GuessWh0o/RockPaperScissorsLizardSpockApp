package com.guesswho.rockpaper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by GuessWh0o on 21.02.2018.
 * Email: developerint97@gmail.com
 */

public class LoginWithPasswordAndEmail extends AppCompatActivity {
    private EditText ET_NewUserName, ET_NewPassword, ET_newEmail; // For signUp
    private EditText ET_userName, ET_password; // For signIn

    private Button BTN_singUp, BTN_singIn;

    private FirebaseDatabase database;
    private DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_up);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        initViews();
    }

    private void initViews() {
        ET_userName = findViewById(R.id.et_userName);
        ET_password = findViewById(R.id.et_password);

        BTN_singIn = findViewById(R.id.btn_sign_in);
        BTN_singUp = findViewById(R.id.btn_sign_up);

        BTN_singUp.setOnClickListener(view -> {
            showSignUpDialog();
        });

        BTN_singIn.setOnClickListener(view -> {
            signIn(ET_userName.getText().toString(), ET_password.getText().toString());
        });
    }

    private void signIn(final String username, final String pass) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()) {
                    if(!username.isEmpty()) {
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if(login.getPassword() != null && login.getPassword().equals(pass)) {
                            Toast.makeText(LoginWithPasswordAndEmail.this, "You are logged in", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginWithPasswordAndEmail.this, "Wrong password or username", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginWithPasswordAndEmail.this, "Please enter you username", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginWithPasswordAndEmail.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.sign_up);
        alertDialog.setMessage(R.string.please_fill_forms);

        LayoutInflater inflater = this.getLayoutInflater();
        View signUpView = inflater.inflate(R.layout.sign_up_layout, null);

        ET_NewUserName =  signUpView.findViewById(R.id.et_new_userName);
        ET_newEmail =  signUpView.findViewById(R.id.et_new_email);
        ET_NewPassword =  signUpView.findViewById(R.id.et_new_password);

        alertDialog.setView(signUpView);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton(R.string.no, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        alertDialog.setPositiveButton(R.string.yes, ((dialogInterface, i) -> {
            User user = new User(ET_NewUserName.getText().toString(),
                    ET_NewPassword.getText().toString(),
                    ET_newEmail.getText().toString());

            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user.getUserName()).exists()) {
                        Toast.makeText(LoginWithPasswordAndEmail.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    } else {
                        users.child(user.getUserName())
                                .setValue(user);
                        Toast.makeText(LoginWithPasswordAndEmail.this, "User registration succeeded!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            dialogInterface.dismiss();
        }));
        alertDialog.show();
    }
}
