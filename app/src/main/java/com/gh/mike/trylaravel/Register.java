package com.gh.mike.trylaravel;

/**
 * Created by mike on 10/6/17.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * Created by mike on 20/08/2016.
 */
public class Register extends Activity {

    ProgressBar progressBar ;
    EditText etUsername,etPassword,etConfirmPassword;
    Button btRegister;




    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        /**
         * Defining layout items for Doctor Registration
         */
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btRegister = (Button) findViewById(R.id.btRegister);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        /**
         * Register Button click event.
         * A Toast is set to alert when the fields are empty.
         * Another toast is set to alert Username must be at least 5 characters.
         * Another toast is set to alert if password does not match confirm password
         **/

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String username = etUsername.getText().toString();
                String  password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                Pattern pswNamePtrn = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
                Matcher mtch = pswNamePtrn.matcher(password);


                if ((etUsername.getText().toString().equals("")) && (etPassword.getText().toString().equals("")) && (etConfirmPassword.getText().toString().equals(""))){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();

                }
                else if (etUsername.getText().toString().length() < 6){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Username  must be at least 6 Characters", Toast.LENGTH_SHORT).show();

                }

                else if (!mtch.matches()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Password  must be at least 6 Characters and should contain at least an Upper Case, a Lower Case and a Special Character(s)", Toast.LENGTH_LONG).show();
                }
                else if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Password does not much", Toast.LENGTH_SHORT).show();
                }




                else {

                    Toast.makeText(getApplicationContext(), "Now registering", Toast.LENGTH_SHORT).show();


                    /**
                     * Implementing JSON
                     *
                     */

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    builder = new AlertDialog.Builder(Register.this);
                                    builder.setMessage("Registration Successful,Click to Continue");
                                    builder.setNegativeButton("Thank you Doctor", null)
                                            .create()
                                            .show();
                                    /**
                                     * Storing username and password to be displayed in another activity
                                     */
                                    Intent intent = new Intent(Register.this, RegisterDetails.class);
                                    intent.putExtra("username",etUsername.getText().toString());
                                    Register.this.startActivity(intent);




                                } else {

                                    builder = new AlertDialog.Builder(Register.this);
                                    builder.setMessage("Registration Failed, Retry, Please check your details");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            etConfirmPassword.setText("");
                                            etPassword.setText("");
                                            etUsername.setText("");
                                            Intent intent = new Intent(Register.this, Register.class);

                                            Register.this.startActivity(intent);
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    };




                    RegisterRequest registerRequest = new RegisterRequest(username, password, confirmPassword, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register.this);
                    queue.add(registerRequest);

                }

            }

        });


    }


}

