/**
 * This is the activity for Login.
 * This is the first activity the user will see.
 * It will prompt the user to enter their info to log into the app.
 * It will check the database to see if the user exists.
 * If so, then the user will be logged into the app.
 *
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private String ids, passwords, userID;

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //Start the notification service on initial app start.
        Intent NotificationServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
        startService(NotificationServiceIntent);

        Button bt1 = findViewById(R.id.submitBT);
        Button bt2 = findViewById(R.id.exitBT);
        final TextView tvSign = findViewById(R.id.sign);
        final EditText idEditText = findViewById(R.id.idInput);
        final EditText pwEditText = findViewById(R.id.pwInput);


        //Initialize the firebase shared variables
        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplication();
        firebaseInstanceData.firebaseDBInstance = FirebaseDatabase.getInstance();
        firebaseInstanceData.firebaseReferenceClasses = firebaseInstanceData.firebaseDBInstance.getReference("classes");
        firebaseInstanceData.firebaseReferenceUsers = firebaseInstanceData.firebaseDBInstance.getReference("users");
        firebaseInstanceData.firebaseReferenceServices = firebaseInstanceData.firebaseDBInstance.getReference("services");

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = idEditText.getText().toString().toUpperCase();
                ValueEventListener pwListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserObject users = dataSnapshot.child(userID).getValue(UserObject.class);

                        //if there is the data of SID
                        if (users != null) {
                            setPasswords(users.password);

                            if (passwords.equals(pwEditText.getText().toString())) {
                                setIds(userID);
                                idEditText.setText("");
                                pwEditText.setText("");

                                Intent intent1 = new Intent(getApplicationContext(), main_menu.class);
                                intent1.putExtra("id", ids);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(getApplicationContext(), "Password doesn't match. Please check ID and Password again", Toast.LENGTH_SHORT).show();
                                idEditText.setText("");
                                pwEditText.setText("");
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid SID. Please check ID again", Toast.LENGTH_SHORT).show();
                            idEditText.setText("");
                            pwEditText.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "The read failed: " + databaseError.toException(), Toast.LENGTH_SHORT).show();
                    }
                };

                //If there is no data with user input, return null
                Query query = firebaseInstanceData.firebaseReferenceUsers.orderByChild("bannerID").equalTo(userID);
                query.addListenerForSingleValueEvent(pwListener);

            }
        });

        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), signUp.class);;
                startActivity(intent2);
                finish();

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit ??");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int button) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        //System.exit(0);
                        //finishAffinity();
                        //Login.this.finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int button) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    /**
     * Checks to see if versions are compatible and if google play services are available.
     * @return true if they are, false if they are not available.
     */
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

    /**
     * Simple method that sets the IDs.
     * @param ids
     */
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * GET method for retrieving the IDs.
     * @return this id.
     */
    public String getIds() {
        return this.ids;
    }

    /**
     * method to set the password.
     * @param passwords gives passwords a value.
     */
    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    /**
     * Method to get the passwords.
     * @return this passwords.
     */
    public String getPasswords() {
        return this.passwords;
    }
}