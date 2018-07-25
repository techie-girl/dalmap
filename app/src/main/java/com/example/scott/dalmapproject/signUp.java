/**
 * This activity handles sign up for the app.
 * Sets the info for a user.
 * That info is added to Firebase, which will allow the user to log on with the credentials they
 * signed up with.
 *
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.annotation.NonNull;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class signUp extends AppCompatActivity {

    private String signIds, signPWs, secondPW, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        Button bt1 = findViewById(R.id.signsubBT);
        Button bt2 = findViewById(R.id.signcancelBT);

        final EditText nameET = findViewById(R.id.nameInput);
        final EditText sidET = findViewById(R.id.signIDinput);
        final EditText pwET = findViewById(R.id.signPWinput);
        final EditText pw2ET = findViewById(R.id.signSPWinput);

        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pwET.getText().toString().equals(pw2ET.getText().toString())) {


                    final ArrayList<String> classes = new ArrayList();

                    ValueEventListener getClassesFromDatabaseListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            UserObject users = dataSnapshot.child(sidET.getText().toString()).getValue(UserObject.class);

                            //if there is the data of SID
                            if (users != null) {
                                Toast.makeText(getApplicationContext(), "There is the same SID in the database. Please check ID again", Toast.LENGTH_SHORT).show();
                                sidET.setText("");
                                nameET.setText("");
                                pwET.setText("");
                                pw2ET.setText("");
                            } else {
                                signIds = sidET.getText().toString().toUpperCase();
                                name = nameET.getText().toString();
                                signPWs = pwET.getText().toString();
                                secondPW = pw2ET.getText().toString();

                                UserObject userObject = new UserObject(signIds, name, classes, signPWs);

                                firebaseInstanceData.firebaseReferenceUsers.child(userObject.bannerID).setValue(userObject);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };

                    firebaseInstanceData.firebaseReferenceUsers.addListenerForSingleValueEvent(getClassesFromDatabaseListener);
                    firebaseInstanceData.firebaseReferenceUsers.push();
                }
                else {
                    Toast.makeText(getApplicationContext(), "The second input of password doesn't match with the first input", Toast.LENGTH_SHORT).show();
                    pwET.setText("");
                    pw2ET.setText("");
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will change to Logout
                Intent intent2 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent2);
            }
        });

    }

    /**
     * gets the sign up ids.
     * @return signIds.
     */
    public String getSignIds() {
        return signIds;
    }

    /**
     * Sets the signIds.
     * @param signIds sign up ids.
     */
    public void setSignIds(String signIds) {
        this.signIds = signIds;
    }

    /**
     * gets the sign up passwords.
     * @return signPWs.
     */
    public String getSignPWs() {
        return signPWs;
    }

    /**
     * sets thje sign up passwords.
     * @param signPWs the password users is signing up with.
     */
    public void setSignPWs(String signPWs) {
        this.signPWs = signPWs;
    }

    /**
     * Gets the second password.
     * @return secondPW.
     */
    public String getSecondPW(){
        return secondPW;
    }

    /**
     * Sets the second password.
     * @param secondPW the second password.
     */
    public void setSecondPW(String secondPW) {
        this.secondPW = secondPW;
    }

    /**
     * gets the name of signed up user.
     * @return name of user.
     */
    public String getname() {
        return name;
    }

    /**
     * Sets the name of the signing up user.
     * @param fname name of the user
     */
    public void setname(String fname) {
        this.name = fname;
    }
}