package com.careernaksha.careernaksha;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.careernaksha.careernaksha.auth.LoginActivity;
import com.careernaksha.careernaksha.counselor.CounsellorFragment;
import com.careernaksha.careernaksha.model.SaveData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;


public class ProfileActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  public static SaveData saveData= new SaveData();
  DatabaseReference databaseReference;
  TextView tv, t, tvi;
  Button b1, b2;
  CardView ivprofilebuilder, ivguidance, ivjob, ivcounselling, ivalumini, ivpsycho, ivinspire, ivoption;
  private FirebaseAuth auth;
  FirebaseDatabase database;
  // ConstraintLayout constraintLayout;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    Toolbar toolbar = findViewById(R.id.toolbar);
    // constraintLayout=findViewById(R.id.content);
    // tv=(TextView)findViewById(R.id.textView);
    tvi = findViewById(R.id.tvi);
    b1 = findViewById(R.id.b1);
    b2 = findViewById(R.id.b2);
    auth = FirebaseAuth.getInstance();

    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(" ");
    databaseReference = FirebaseDatabase.getInstance().getReference();
    DrawerLayout drawer = findViewById(R.id.drawer_layout);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    View v = navigationView.getHeaderView(0);
    tv = v.findViewById(R.id.textView);
    t = findViewById(R.id.t);
    ivprofilebuilder = findViewById(R.id.ivprofilebuilder);
    ivguidance = findViewById(R.id.ivguidance);
    ivjob = findViewById(R.id.ivjob);
    ivcounselling = findViewById(R.id.ivcounselling);
    ivalumini = findViewById(R.id.ivalumini);
    ivpsycho = findViewById(R.id.ivpsycho);
    ivinspire = findViewById(R.id.ivinspire);
    ivoption = findViewById(R.id.ivoption);



    FirebaseFirestore.getInstance().collection("users").document(auth.getUid()).get()
        .addOnSuccessListener(
            new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = documentSnapshot.getString("name");
                if (name != null){
                  saveData.setName(name);
                  tv.setText("Hello " + name);
                  t.setText("Hello " + name);
                }else {
                  saveData.setName(auth.getCurrentUser().getDisplayName());
                  tv.setText("Hello "+auth.getCurrentUser().getDisplayName());
                  t.setText("Hello " + auth.getCurrentUser().getDisplayName());
                }
              }
            })
        .addOnFailureListener(new OnFailureListener() {
          @Override

          public void onFailure(@NonNull Exception e) {
            Toast.makeText(ProfileActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
          }
        });


    ivprofilebuilder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, new CareerPatriFragment()).addToBackStack(null).commit();
      }
    });
    ivguidance.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, new CareerDishaFragment()).addToBackStack(null).commit();
      }
    });
    ivjob.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, new CareerRozgarFragment()).addToBackStack(null).commit();
      }
    });
    ivcounselling.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, new CareerParmarshFragment()).addToBackStack(null).commit();
      }
    });
    ivalumini.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, new CareerSamparkFragment()).addToBackStack(null).commit();
      }
    });
    ivpsycho.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CareerPathFragment())
            .addToBackStack(null).commit();

      }
    });
    ivinspire.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, new CareerPrernaFragment()).addToBackStack(null).commit();

      }
    });
    ivoption.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, new CareerOptionsFragment()).addToBackStack(null).commit();

      }
    });
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    }

    if (getFragmentManager().getBackStackEntryCount() == 0) {
      super.onBackPressed();
    } else {
      getFragmentManager().popBackStack();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.profile, menu);
    return true;
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(@NotNull MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_profile) {

      //ProfileFragment profileFragment=new ProfileFragment();
      getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ProfileFragment())
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_upload) {
      getSupportFragmentManager().beginTransaction().replace(R.id.frame, new UploadFragment())
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_councelor) {
      getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CounsellorFragment())
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_search) {
      getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SearchFragment())
          .addToBackStack(null).commit();


    }  else if (id == R.id.nav_home) {
      Intent j = new Intent(getApplicationContext(), ProfileActivity.class);
      startActivity(j);

    } else if (id == R.id.nav_logout) {
      FirebaseAuth.getInstance().signOut();
      startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
      finish();
    }
    else if (id == R.id.nav_facebook) {
      String urlString = "https://m.facebook.com/careernaksha/";

      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setPackage("com.android.chrome");
      try {
         startActivity(intent);
      } catch (ActivityNotFoundException ex) {
        // Chrome browser presumably not installed so allow user to choose instead
        intent.setPackage(null);
        startActivity(intent);
      }
    }
    else if (id == R.id.nav_linkin) {
      String urlString = "https://www.linkedin.com/company/careernaksha";

      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setPackage("com.android.chrome");
      try {
        startActivity(intent);
      } catch (ActivityNotFoundException ex) {
        // Chrome browser presumably not installed so allow user to choose instead
        intent.setPackage(null);
        startActivity(intent);
      }

    }


    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
