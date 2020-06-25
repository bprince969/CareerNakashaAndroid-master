package com.careernaksha.careernaksha;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.careernaksha.careernaksha.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  private FirebaseAuth auth = FirebaseAuth.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (auth.getCurrentUser() != null) {
          startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else {
          startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        MainActivity.this.finish();
      }
    },3000);
  }
}