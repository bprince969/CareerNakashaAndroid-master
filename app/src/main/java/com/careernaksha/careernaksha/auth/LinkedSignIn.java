package com.careernaksha.careernaksha.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.AbdAllahAbdElFattah13.linkedinsdk.ui.LinkedInUser;
import com.AbdAllahAbdElFattah13.linkedinsdk.ui.linkedin_builder.LinkedInFromActivityBuilder;
import com.careernaksha.careernaksha.ProfileActivity;
import com.careernaksha.careernaksha.R;
import com.careernaksha.careernaksha.RequestHandler;
import com.careernaksha.careernaksha.model.SaveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class LinkedSignIn extends AppCompatActivity {

  public static final int LINKEDIN_REQUEST = 99;
  public static String clientID="81eq65nw7x45lw";
  public static String clientSecret="RjVRmRkbgAyAGubo";
  public static String redirectUrl="https://careernaksha.com/linkedin-oauth2/callback";
  public static final String URL_REGISTER = "https://www.apnishayari.in/test.php";


  LoginViewModel loginViewModel=new LoginViewModel();
  private FirebaseAuth mAuth;
  DatabaseReference databaseReference;
  ProgressDialog progressDialog;
  SignupActivity signupActivity=new SignupActivity();

  String user_name,user_password,user_email;

  private ImageView ivUserPic;
  private  Button btnLogin;
  private Button confirm;
  private TextView tvFName, tvLName, tvEmail;
  private EditText password;

  private String accessToken;
  private long accessTokenExpiry;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.linkedin_signin);

    getCredentials();


    mAuth = FirebaseAuth.getInstance();
    ivUserPic = findViewById(R.id.iv_user_pic);
    btnLogin = findViewById(R.id.btn_login);
    tvFName = findViewById(R.id.tv_first_name);
    tvLName = findViewById(R.id.tv_last_name);
    tvEmail = findViewById(R.id.tv_email);
    password=findViewById(R.id.password_user);
    databaseReference = FirebaseDatabase.getInstance().getReference().child("user");

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


        LinkedInFromActivityBuilder.getInstance(LinkedSignIn.this)
            .setClientID(clientID)
            .setClientSecret(clientSecret)
            .setRedirectURI(redirectUrl)
            .authenticate(LINKEDIN_REQUEST);

      }
    });

    confirm = findViewById(R.id.btn_get_update);
    confirm.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        progressDialog = new ProgressDialog(LinkedSignIn.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Signing up..");
        progressDialog.show();

        //Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();

        user_password=password.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(user_email, user_password)
            .addOnCompleteListener(LinkedSignIn.this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  // Sign in success, update UI with the signed-in user's information
                  Add();
                  Update();


                } else {
                  // If sign in fails, display a message to the user.
                  progressDialog.dismiss();
                  Toast.makeText(LinkedSignIn.this, task.getException().getLocalizedMessage(),
                      Toast.LENGTH_SHORT).show();
                }


              }
            });




      }
    });

  }



  private void setUserData(LinkedInUser user) {

    Log.wtf("LINKEDIN ID", user.getId());

    tvFName.setText(user.getFirstName());
    tvLName.setText(user.getLastName());
    tvEmail.setText(user.getEmail());


    user_name=user.getFirstName();
    user_email=user.getEmail();

    btnLogin.setVisibility(View.INVISIBLE);
    confirm.setVisibility(View.VISIBLE);
    password.setVisibility(View.VISIBLE);



    if (user.getProfilePictureUrl() != null && !user.getProfilePictureUrl().isEmpty()) {
      new ImageLoadTask(user.getProfilePictureUrl(), ivUserPic).execute();
    }
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == LINKEDIN_REQUEST && data != null) {
      if (resultCode == RESULT_OK) {

        //Successfully signed in and retrieved data
        LinkedInUser user = data.getParcelableExtra("social_login");
        setUserData(user);

      } else {


        //print the error
        Log.wtf("LINKEDIN ERR", data.getStringExtra("err_message"));

        if (data.getIntExtra("err_code", 0) == LinkedInFromActivityBuilder.ERROR_USER_DENIED) {
          //user denied access to account
          Toast.makeText(this, "User Denied Access", Toast.LENGTH_SHORT).show();
        } else if (data.getIntExtra("err_code", 0) == LinkedInFromActivityBuilder.ERROR_USER_DENIED) {
          //some error occured
          Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }


      }
    }

  }


  /**
   * Loads Image from url in image view, you might want to use a library like picasso or glide
   */
  public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;

    public ImageLoadTask(String url, ImageView imageView) {
      this.url = url;
      this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
      try {
        URL urlConnection = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlConnection
            .openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        return BitmapFactory.decodeStream(input);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
      super.onPostExecute(result);
      imageView.setImageBitmap(result);
    }

  }


  /**
   *
   * This Method just for demonstration purpose only, you are free to use any technique to keep your credentials secure
   * If you want to use this method, just rename the linkedin-credentials-example.json file to linkedin-credentials.json
   * Make sure to update your linkedin credentials in the said file
   */
  private void getCredentials() {
    try {

      InputStream is = getAssets().open("linkedin-credentials.json");
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String json = new String(buffer, "UTF-8");
      JSONObject linkedinCred = new JSONObject(json);
      clientID = linkedinCred.getString("client_id");
      clientSecret = linkedinCred.getString("client_secret");
      redirectUrl = linkedinCred.getString("redirect_url");

    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }



  public void Add() {

    SaveData saveData= new SaveData(user_name, "Not found",user_email, user_password);
    databaseReference.push().setValue(saveData);
    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
  }

  private void Update() {
    final FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
      UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
          .setDisplayName(user_name.trim()).build();

      user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful()) {
            Intent intent = new Intent(LinkedSignIn.this, ProfileActivity.class);
            startActivity(intent);

            Toast.makeText(LinkedSignIn.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();

            class Login extends AsyncTask<Void, Void, String> {
              ProgressDialog pdLoading = new ProgressDialog(LinkedSignIn.this);

              @Override
              protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
              }

              @Override
              protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", "okgoogle.gmail.com");


                //returing the response
                return requestHandler.sendPostRequest(URL_REGISTER, params);
              }

              @Override
              protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {
                  //converting response to json object
                  JSONObject obj = new JSONObject(s);
                  //if no error in response
                  if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                  }
                } catch (JSONException e) {
                  e.printStackTrace();
                  //   Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_LONG).show();
                }
              }
            }

            Login login = new Login();
            login.execute();


          }
        }
      });
    }


  }




}
