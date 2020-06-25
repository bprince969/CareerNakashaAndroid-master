package com.careernaksha.careernaksha;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.careernaksha.careernaksha.auth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

  EditText email;
  Button Reset;
  FirebaseAuth firebaseAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgotpassword);

    email = findViewById(R.id.emailpw);
    Reset = findViewById(R.id.Resetpw);
    firebaseAuth = FirebaseAuth.getInstance();

    Reset.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String useremail = email.getText().toString().trim();

        if (useremail.equals("")){
          Toast.makeText(forgotpassword.this,"Please Enter Your Registered Email",Toast.LENGTH_SHORT).show();
        }
        else {
          firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(
              new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()){
                    Toast.makeText(forgotpassword.this,"Password Reset Mail is Sent",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(forgotpassword.this, LoginActivity.class));

                  }
                  else {
                    Toast.makeText(forgotpassword.this,"Error in Sending Password Reset Mail",Toast.LENGTH_SHORT).show();

                  }

                }
              });
        }

      }
    });
  }
}
