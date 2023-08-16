package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView tologin;
    private EditText email, password, confirmPassword, fullName;
    private Button registerButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.regemail);
        password = findViewById(R.id.regpassword);
        fullName = findViewById(R.id.regfullname);
        confirmPassword = findViewById(R.id.regcheckpassword);
        registerButton = findViewById(R.id.btnregister);


        tologin = findViewById(R.id.txttologin);
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);

            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userFullName = fullName.getText().toString().trim();


                if (userEmail.isEmpty()) {
                    email.setError("Email cannot be empty");
                    return;
                }

                if (userPassword.isEmpty()) {
                    password.setError("Password cannot be empty");
                    return;
                }

                if (!userPassword.equals(confirmPassword.getText().toString().trim())) {
                    confirmPassword.setError("Passwords do not match");
                    return;
                }

                auth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    if (firebaseUser != null) {
                                        String userId = firebaseUser.getUid();
                                        uploadData(userId, userFullName, userEmail);
                                        updata();

                                    }
                                    Toast.makeText(Register.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, Login.class));
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "SignUp Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    private void updata(){
        String name=fullName.getText().toString();
        String userEmail = email.getText().toString().trim();
        String currentuser=FirebaseAuth.getInstance().getCurrentUser().getUid();

        User dataClass=new User(currentuser,name,userEmail);

        FirebaseDatabase.getInstance().getReference("Users").child(currentuser).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void uploadData(String userId, String fullName, String email) {
        User user = new User(userId, fullName, email);

        FirebaseDatabase.getInstance().getReference("Users").child(userId)
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Data upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
