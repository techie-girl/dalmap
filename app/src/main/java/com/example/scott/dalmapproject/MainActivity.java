package com.example.scott.dalmapproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String ids, passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt1 = findViewById(R.id.submitBT);
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

                    Intent intent1 = new Intent(getApplicationContext(), classLists.class);
                    startActivity(intent1);

                }

                /* If there is a problem to connect to Firebase
                else if() {

                    //Show the Error msg about Network Connectivity Issue

                }
                 */

                /* If there is ID on Firebase, but Password was wrong
                else if () {

                    // Prompt Error msg with Toast

                }
                */

                /*

                else {

                    //Addition window poped for Asking Sign In
                }
                 */

                idEditText.setText("");
                pwEditText.setText("");

            }
        });
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIds() {
        return ids;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getPasswords() {
        return passwords;
    }
}
