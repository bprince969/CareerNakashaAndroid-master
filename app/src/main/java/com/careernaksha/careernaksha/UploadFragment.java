package com.careernaksha.careernaksha;


import static android.app.Activity.RESULT_OK;
import static com.careernaksha.careernaksha.ProfileActivity.saveData;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {

  TextView tvuser, tvfile;
  CardView cvupload;
  FirebaseAuth auth;
  FirebaseUser user;
  ProgressDialog progressDialog;
  DatabaseReference databaseReference;
  FirebaseStorage storage;
  FirebaseDatabase database;
  Uri pdf;

  public UploadFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_upload, container, false);
    tvuser = view.findViewById(R.id.tvuser);
    tvfile = view.findViewById(R.id.tvfile);
    cvupload = view.findViewById(R.id.cvupload);

    auth = FirebaseAuth.getInstance();
    user = auth.getCurrentUser();
    databaseReference = FirebaseDatabase.getInstance().getReference();
    if (user != null) {
      tvuser.setText("Hi " + saveData.getName());
    }

    cvupload.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (pdf != null) {
          Upload(pdf);
        } else if (pdf == null) {

          Toast.makeText(getContext(), "Press Long to Select a file", Toast.LENGTH_SHORT).show();

        }


      }
    });

    cvupload.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {

        if (ContextCompat
            .checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
          select();

        } else {
          ActivityCompat.requestPermissions(getActivity(),
              new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
        }

        return true;
      }
    });
    return view;


  }

  private void Upload(Uri pdf) {
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    progressDialog.setTitle("Uploading File...");
    progressDialog.setProgress(0);
    progressDialog.show();
    final String filename = System.currentTimeMillis() + "";
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    storageReference.child(Objects.requireNonNull(user.getDisplayName())).child(filename)
        .putFile(pdf).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        String url = taskSnapshot.getUploadSessionUri().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(user.getDisplayName()).setValue(url)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                  Toast.makeText(getContext(), "File Uploaded Successfully", Toast.LENGTH_SHORT)
                      .show();
                  progressDialog.dismiss();

                  tvfile.setText("Press Long to choose another file to Upload..!!");

                } else {
                  Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                }
              }
            });

      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Toast.makeText(getContext(), "Not Uploaded", Toast.LENGTH_SHORT).show();
      }
    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        int cur = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot
            .getTotalByteCount());
        progressDialog.setProgress(cur);
      }
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      select();
    } else {
      Toast.makeText(getContext(), "Please provide permission", Toast.LENGTH_SHORT).show();
    }
  }

  private void select() {
    Intent i = new Intent();
    i.setType("application/pdf");
    i.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(i, 86);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // super.onActivityResult(requestCode,resultCode,data);
    if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
      pdf = data.getData();
      tvfile.setText("A file is selected :" + data.getData().getLastPathSegment());
    } else {

      Toast.makeText(getContext(), "Please select file", Toast.LENGTH_SHORT).show();
    }
  }


}




