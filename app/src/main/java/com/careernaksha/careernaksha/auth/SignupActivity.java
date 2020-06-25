package com.careernaksha.careernaksha.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.careernaksha.careernaksha.ProfileActivity;
import com.careernaksha.careernaksha.R;
import com.careernaksha.careernaksha.model.SaveData;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;


public class SignupActivity extends AppCompatActivity {

  EditText et1, et2, et3, et4;
  Button bt1;
  ImageView fb, google, twitter;
  private FirebaseAuth mAuth;
  DatabaseReference databaseReference;
  ProgressDialog progressDialog;
  CallbackManager mCallbackManager;
  GoogleSignInClient mGoogleSignInClient;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_signup);
    mAuth = FirebaseAuth.getInstance();
    et1 = findViewById(R.id.et1);
    et2 = findViewById(R.id.et2);
    et3 = findViewById(R.id.et3);
    et4 = findViewById(R.id.et4);
    bt1 = findViewById(R.id.bt1);
    fb = (ImageView) findViewById(R.id.fb);
    google = findViewById(R.id.googleSignInButton);
    twitter = findViewById(R.id.twitter);
    databaseReference = FirebaseDatabase.getInstance().getReference().child("user");




    TwitterAuthConfig authConfig = new TwitterAuthConfig(
        "JhWGuxZ1w05hllqq00QZHMXJO", "fDMhNaGxLgPj63E4WgmWgTLQ4sQ9Mk0UB7LParFgex64EcTzR0");
    TwitterConfig twitterAuthConfig = new TwitterConfig.Builder(this)
        .twitterAuthConfig(authConfig)
        .build();
    Twitter.initialize(twitterAuthConfig);


    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    google.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loging in..");
        progressDialog.show();
      }
    });

    bt1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Signing up..");
        progressDialog.show();
        if (et1.getText().toString().equals("") || et2.getText().toString().equals("") || et3
            .getText().toString().equals("") || et4.getText().toString().equals("")) {
          Toast.makeText(SignupActivity.this, "Enter Required Fields", Toast.LENGTH_SHORT).show();
        } else {

          mAuth.createUserWithEmailAndPassword(et3.getText().toString(), et4.getText().toString())
              .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Add();
                    Update();


                  } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
                  }


                }
              });
        }
      }
    });

  }

  private void handleFacebookAccessToken(AccessToken accessToken) {

    AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

//
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
          //
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              FirebaseUser user = mAuth.getCurrentUser();
              progressDialog.dismiss();
//
              Intent i = new Intent(SignupActivity.this, ProfileActivity.class);
              startActivity(i);
              finish();
//
            } else {
//                            // If sign in fails, display a message to the user.
              Toast.makeText(SignupActivity.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
            }
//
//                        // ...
          }
        }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode, resultCode, data);
    mCallbackManager.onActivityResult(requestCode, resultCode, data);
    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == 101) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        // Google Sign In was successful, authenticate with Firebase
        GoogleSignInAccount account = task.getResult(ApiException.class);
        firebaseAuthWithGoogle(account);
      } catch (ApiException e) {
        // Google Sign In failed, update UI appropriately

        // ...
      }
    }

  }


  private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information

              FirebaseUser user = mAuth.getCurrentUser();
              progressDialog.dismiss();
              Intent i = new Intent(SignupActivity.this, LoginActivity.class);
              startActivity(i);
              finish();

            } else {
              // If sign in fails, display a message to the user.
              progressDialog.dismiss();
              Toast.makeText(SignupActivity.this, "SignIn Failed..!!", Toast.LENGTH_SHORT).show();
            }

            // ...
          }
        });
  }

  public void Add() {
    String name = et1.getText().toString();
    String number = et2.getText().toString();
    String email = et3.getText().toString();
    String pass = et4.getText().toString();
    SaveData saveData= new SaveData(name, number, email, pass);
    databaseReference.push().setValue(saveData);
  }

  private void Update() {
    final FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
      UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
          .setDisplayName(et1.getText().toString().trim()).build();

      user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful()) {
            Intent intent = new Intent(SignupActivity.this, ProfileActivity.class);
            startActivity(intent);

            Toast.makeText(SignupActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }


  }

  @Override
  public void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) {
      Intent i = new Intent(SignupActivity.this, ProfileActivity.class);
      startActivity(i);
      finish();

    }

  }

}
