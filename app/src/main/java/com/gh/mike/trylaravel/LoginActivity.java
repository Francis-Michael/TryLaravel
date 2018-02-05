package com.gh.mike.trylaravel;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    public static   String password1,username1, token1;
    EditText mfUsername, mfPassword;
    Button mfButton;
    AlertDialog.Builder mfbuilder;
    String username, password = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mfButton = (Button) findViewById(R.id.btnButton);
        mfPassword = (EditText) findViewById(R.id.btnPassword);
        mfUsername = (EditText) findViewById(R.id.btnUsername);

        mfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username1 = mfUsername.getText().toString();
                password1 = mfPassword.getText().toString();

                if (mfUsername.getText().toString().equals("") || mfPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Now login", Toast.LENGTH_SHORT).show();
                    Response.Listener<String> mfResponselistener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                JSONObject mfJsonObject = new JSONObject(response);
                               // boolean success = mfJsonObject.getBoolean("success");
                                if (mfJsonObject.getString("status").equals("Success")) {
//                                    mfJsonObject.getString("Success");
                                    mfbuilder = new AlertDialog.Builder(LoginActivity.this);
//                                    mfbuilder.setMessage("Login Successful,Click to Continue");
                                    mfbuilder.setMessage(mfJsonObject.getString("Success"));
                                    mfbuilder.setNegativeButton("Thank you", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(LoginActivity.this, Cool.class);
                                    LoginActivity.this.startActivity(intent);
                                } else {
                                    mfbuilder = new AlertDialog.Builder(LoginActivity.this);
                                    mfbuilder.setMessage("Login Failed, Retry, Please check your details");
                                    mfbuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            mfPassword.setText("");
                                            mfUsername.setText("");
                                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                            LoginActivity.this.startActivity(intent);
                                        }
                                    });
                                    AlertDialog alertDialog = mfbuilder.create();
                                    alertDialog.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

//                    password1 = mfUsername.getText().toString();
//                    username1 = mfPassword.getText().toString();
                    token1 = "sLZqlsQmAegj";
                    LoginRequest loginRequest = new LoginRequest(mfResponselistener);
//                    password1 = "";
//                    username1 = "";
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    requestQueue.add(loginRequest);
                }
            }
        });

    }

    public void Register(View view){

        Intent intent = new Intent(this, Register.class);
        LoginActivity.this.startActivity(intent);
    }


}
