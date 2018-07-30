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
        firebaseInstanceData.firebaseReferenceServices = firebaseInstanceData.firebaseDBInstance.getReference("services");

        loginButtonFunctionality();
        exitButtonFunctionality();
        signupOnClickFunctionality();
    }

    /**
     * Login button functionality
     */
    private void loginButtonFunctionality(){

        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplication();
        final EditText idEditText = findViewById(R.id.idInput);
        final EditText pwEditText = findViewById(R.id.pwInput);

        Button bt1 = findViewById(R.id.submitBT);
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
    }

    /**
     * Exit button functionality
     */
    private void exitButtonFunctionality(){
        Button bt2 = findViewById(R.id.exitBT);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit ??");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int button) {
                        android.os.Process.killProcess(android.os.Process.myPid());
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
     * Sign Up button functionality
     */
    private void signupOnClickFunctionality(){
        final TextView tvSign = findViewById(R.id.sign);
        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent2);
                finish();
            }
        });
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