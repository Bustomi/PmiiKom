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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kempaka.pmiikom.R;

import java.util.Set;

public class MainRegistrasi extends AppCompatActivity {

    private EditText UserEmail, UserPassword, UserConfirm;
    private Button CreateAccount;
    private ProgressDialog LoadingBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_registrasi );

        mAuth = FirebaseAuth.getInstance();

        UserEmail = (EditText) findViewById( R.id.login_email );
        UserPassword = (EditText)findViewById( R.id.password_email );
        UserConfirm = (EditText) findViewById( R.id.confirm_password );
        CreateAccount = (Button) findViewById( R.id.registrasi );
        LoadingBar = new ProgressDialog( this );

        CreateAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        } );
    }

    private void CreateNewAccount() {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        String confirmPassword = UserConfirm.getText().toString();

        if (TextUtils.isEmpty( email )){
            Toast.makeText( this,"Please Write your email", Toast.LENGTH_SHORT ).show();
        }else if(TextUtils.isEmpty( password )){
            Toast.makeText( this,"Please write your password...",Toast.LENGTH_SHORT ).show();
        }else if(TextUtils.isEmpty( confirmPassword )){
            Toast.makeText( this,"Please Confimr your Password", Toast.LENGTH_SHORT ).show();
        }else if (!password.equals(confirmPassword)){
            Toast.makeText( this,"Your passrod do not watch with your confirm password.....",Toast.LENGTH_SHORT ).show();
        }else {

            LoadingBar.setTitle( "Creating New Account" );
            LoadingBar.setMessage( "Please wait, while we are creating your new Account..." );
            LoadingBar.show();
            LoadingBar.setCanceledOnTouchOutside( true );

            mAuth.createUserWithEmailAndPassword( email,password )
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                SendUserToSetup(); 

                                Toast.makeText( MainRegistrasi.this,"You are authenticated successfully....",Toast.LENGTH_SHORT ).show();
                                LoadingBar.dismiss();
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText( MainRegistrasi.this,"Error Occured"+message,Toast.LENGTH_SHORT ).show();
                                LoadingBar.dismiss();
                            }
                        }
                    } );
        }
    }

    private void SendUserToSetup() {
        Intent setupIntent = new Intent( MainRegistrasi.this, SetupActivity.class );
        setupIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( setupIntent );
        finish();
    }
}
