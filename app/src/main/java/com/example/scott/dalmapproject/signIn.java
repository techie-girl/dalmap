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

public class signIn extends AppCompatActivity {

    private String signIds, signPWs, secondPW, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);

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
                                signIds = sidET.getText().toString();
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

    public String getSignIds() {
        return signIds;
    }

    public void setSignIds(String signIds) {
        this.signIds = signIds;
    }

    public String getSignPWs() {
        return signPWs;
    }

    public void setSignPWs(String signPWs) {
        this.signPWs = signPWs;
    }

    public String getSecondPW(){
        return secondPW;
    }

    public void setSecondPW(String secondPW) {
        this.secondPW = secondPW;
    }

    public String getname() {
        return name;
    }

    public void setname(String fname) {
        this.name = fname;
    }
}