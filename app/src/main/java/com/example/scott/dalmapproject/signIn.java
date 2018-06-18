package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class signIn extends AppCompatActivity {

    private String ids, passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);

        Button bt1 = findViewById(R.id.submitBT);
        final EditText idEditText = findViewById(R.id.idInput);
        final EditText pwEditText = findViewById(R.id.pwInput);



        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseCommunication bc = new dataBaseCommunication();

                // if login done successfully
                if (bc.loginValidCheck(idEditText.getText().toString(), pwEditText.getText().toString())) {

                /*
                    setIds(idEditText.getText().toString());
                    setPasswords(pwEditText.getText().toString());
                    idEditText.setText("");
                    pwEditText.setText("");

                    Intent intent1 = new Intent(getApplicationContext(), classLists.class);
                    startActivity(intent1);

*/
                }
            }
        });
    }
}
