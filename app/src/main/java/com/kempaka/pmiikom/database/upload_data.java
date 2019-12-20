package com.kempaka.pmiikom.database;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kempaka.pmiikom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kempaka.pmiikom.landasan.sejarah_pmii;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class upload_data extends AppCompatActivity {


    private ImageButton SelectPostImage;
    private Button Upload;
    private ProgressDialog loading;
    private EditText PostDescription;
    private Uri ImageUri;



    private String Description;
    private StorageReference PostsImageReference;
    private DatabaseReference userRef;
    private List<Artikel>  mHasil;
    private FirebaseAuth mAuth ;
    private String saveCurrentData, saveCurrentTime, postRandom, downloadUrl, current_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mAuth = FirebaseAuth.getInstance();
        current_id = mAuth.getCurrentUser().getUid();

        PostsImageReference = FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child( "Users" );
        // postRef = FirebaseDatabase.getInstance().getReference().child( "Posts" );

        SelectPostImage = (ImageButton) findViewById( R.id.imageupload );
        Upload = (Button) findViewById( R.id.ambil_image );
        PostDescription = (EditText) findViewById( R.id.isipesan );
        loading = new  ProgressDialog(this);


        SelectPostImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        } );
        Upload.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePost();
            }
        } );
    }

    private void ValidatePost() {
        Description = PostDescription.getText().toString();

        if (ImageUri == null){
            Toast.makeText( this, "Please select post Image", Toast.LENGTH_SHORT ).show();
        }
        else if (TextUtils.isEmpty( Description )){
            Toast.makeText( this, "Please say something about your image.....", Toast.LENGTH_SHORT ).show();
        }
        else {
            loading.setTitle( "Add new Post" );
            loading.setMessage( "Please wait, while we updating" );
            loading.show();
            loading.setCanceledOnTouchOutside( true );
            Storag();
        }
    }

    private void Storag() {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat( "dd-MMMM-yyyy" );
        saveCurrentData = currentDate.format( calFordDate.getTime() );

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat( "HH:mm" );
        saveCurrentTime = currentDate.format( calFordDate.getTime() );

        postRandom = saveCurrentData +saveCurrentTime;

        StorageReference filepath = PostsImageReference.child( "Post Images" ).child( ImageUri.getLastPathSegment() + postRandom +".jpg");

        filepath.putFile( ImageUri ).addOnCompleteListener( new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    downloadUrl = task.getResult().getDownloadUrl().toString();
                    Toast.makeText( upload_data.this, "Image Upload", Toast.LENGTH_SHORT ).show();
                    savingPost();
                }
                else
                {
                     String message = task.getException().getMessage();
                    Toast.makeText( upload_data.this, "Error occured" + message, Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    private void savingPost() {
        userRef.child( current_id  ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userFullname = dataSnapshot.child( "fullname" ).getValue().toString();
                    String userProfile = dataSnapshot.child( "profileImage" ).getValue().toString();

                    HashMap postMap = new HashMap(  );
                    postMap.put( "uid", current_id);
                    postMap.put( "date", saveCurrentData);
                    postMap.put( "time", saveCurrentTime);
                    postMap.put( "description", Description);
                    postMap.put( "postimage", downloadUrl);
                    postMap.put( "profileimage", userProfile);
                    postMap.put( "userfullname", userFullname);
                    userRef.child(current_id + postRandom ).updateChildren( postMap ).addOnCompleteListener( new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful())
                            {
                                new upload();
                                Toast.makeText( upload_data.this, "Post is Updated", Toast.LENGTH_SHORT ).show();
                                Artikel upload = dataSnapshot.getValue( Artikel.class );
                                mHasil.add( upload );
                                loading.dismiss();
                            }
                            else
                            {
                                Toast.makeText( upload_data.this, "Error occured", Toast.LENGTH_SHORT ).show();
                                loading.dismiss();
                            }
                        }
                    } );

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }



    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
        galleryIntent.setType( "image/*" );
        startActivityForResult(galleryIntent, Gallery_Pick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode==Gallery_Pick && resultCode == RESULT_OK && data!=null){
            ImageUri =  data.getData();
            SelectPostImage.setImageURI( ImageUri );
        }
    }
}
