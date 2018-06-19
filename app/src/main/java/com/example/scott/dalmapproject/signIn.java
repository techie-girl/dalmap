package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signIn extends AppCompatActivity {

    private String signIds, signPWs, secondPW, fname, lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);

        Button bt1 = findViewById(R.id.signsubBT);
        Button bt2 = findViewById(R.id.signcancelBT);

        EditText fnameET = findViewById(R.id.fnameInput);
        EditText lnameET = findViewById(R.id.lnameInput);
        final EditText sidET = findViewById(R.id.signIDinput);
        final EditText pwET = findViewById(R.id.signPWinput);
        final EditText pw2ET = findViewById(R.id.signSPWinput);




        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If there is no duplication of ID in database
                if (true) {

                    // If PW matches to Re-PW,
                    if(true) {

                        //To DO: Should call writing data into Firebase

                        setSignIds(sidET.getText().toString());

                        Intent intent1 = new Intent(getApplicationContext(), main_menu.class);
                        intent1.putExtra("id", signIds);
                        startActivity(intent1);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Re-Password didn't match to Password",Toast.LENGTH_SHORT).show();
                    }
                }
                // second input of password didn't match to first input

                else {
                    pw2ET.setText("");
                    pwET.setText("");
                    Toast.makeText(getApplicationContext(), "There is same ID in database",Toast.LENGTH_SHORT).show();
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
