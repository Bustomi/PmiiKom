package com.kempaka.pmiikom.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.kempaka.pmiikom.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName, CountryName;
    private Button SaveInfo;
    private CircleImageView Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setup );

        UserName = (EditText) findViewById( R.id.editText );
        FullName = (EditText) findViewById( R.id.editText2 );
        CountryName = (EditText) findViewById( R.id.editText3 );
        SaveInfo = (Button) findViewById(R.id.button );
        Profile = (CircleImageView)findViewById( R.id.Image_profile );
    }
}
