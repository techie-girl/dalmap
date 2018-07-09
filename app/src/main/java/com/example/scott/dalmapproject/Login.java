package com.example.scott.dalmapproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private String ids, passwords;

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //Start the notification service on initial app start.
        Intent NotificationServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
        startService(NotificationServiceIntent);

        //Initialize the firebase shared variables
        FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplication();
        firebaseInstanceData.firebaseDBInstance = FirebaseDatabase.getInstance();
        firebaseInstanceData.firebaseReferenceClasses = firebaseInstanceData.firebaseDBInstance.getReference("classes");
        firebaseInstanceData.firebaseReferenceUsers = firebaseInstanceData.firebaseDBInstance.getReference("users");


        Button bt1 = findViewById(R.id.submitBT);
        final TextView tvSign = findViewById(R.id.sign);
        final EditText idEditText = findViewById(R.id.idInput);
        final EditText pwEditText = findViewById(R.id.pwInput);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseCommunication bc = new dataBaseCommunication();

                // if login done successfully
                if (bc.loginValidCheck(idEditText.getText().toString(), pwEditText.getText().toString())) {
                    setIds(idEditText.getText().toString());
                    setPasswords(pwEditText.getText().toString());
                    idEditText.setText("");
                    pwEditText.setText("");

                    Intent intent1 = new Intent(getApplicationContext(), main_menu.class);
                    intent1.putExtra("id", ids);
                    startActivity(intent1);

                }

                else {
                    Toast.makeText(getApplicationContext(), "Failed to Login. Please check ID and Password again",Toast.LENGTH_SHORT).show();
                    idEditText.setText("");
                    pwEditText.setText("");

                }
            }
        });

        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), signIn.class);
                startActivity(intent2);

            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Login.this);

        if (available == ConnectionResult.SUCCESS){
            //User can make map requests
            Log.d(TAG, "isServicesOK: google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Error occured but resolvable (version issue most likely)
            Log.d(TAG, "isServicesOK: Error occured, but resolvable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Login.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIds() {
        return this.ids;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getPasswords() {
        return this.passwords;
    }
}


