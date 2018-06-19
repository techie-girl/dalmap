package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private String ids, passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

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


