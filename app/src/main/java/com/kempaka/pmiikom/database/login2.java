package com.kempaka.pmiikom.database;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kempaka.pmiikom.FavoritesFragment;
import com.kempaka.pmiikom.MainActivity;
import com.kempaka.pmiikom.R;

public class login2 extends AppCompatActivity {

    private Button LoginButton;
    private EditText UserEmail,UserPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView AkunBaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login2 );

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
        }

        AkunBaru = (TextView) findViewById( R.id.new_akun );
        UserEmail = (EditText) findViewById( R.id.log_email );
        UserPassword = (EditText) findViewById( R.id.log_pass );
        LoginButton = (Button) findViewById( R.id.login );

        LoginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu();
            }
        } );
        AkunBaru.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserReg();
            }
        } );
    }

    private void Menu() {
        String email = UserEmail.getText().toString().trim();
        String password = UserPassword.getText().toString().trim();

        if (TextUtils.isEmpty( email )) {
            Toast.makeText( this, "Please enter email", Toast.LENGTH_LONG ).show();
            return;
        }

        if (TextUtils.isEmpty( password )) {
            Toast.makeText( this, "Please enter password", Toast.LENGTH_LONG ).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage( "Registering Please Wait..." );
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword( email, password )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                        }
                    }
                } );

    }

    private void SendUserReg() {
        Intent registerIntent = new Intent( login2.this,MainRegistrasi.class );
        startActivity( registerIntent );
        finish();
    }
    
}
