package com.gh.mike.trylaravel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 10/6/17.
 */
public class RegisterRequest extends StringRequest {
    //private static final String REGISTER_REQUEST_URL = "http://fanco.dx.am/DoctorRegister.php";http://fanco.dx.am/Docregist2.php
    private static final String  REGISTER_REQUEST_URL= "";

    private Map<String, String> params;
    public RegisterRequest(String username, String password, String confirmPassword, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("confirmPassword",confirmPassword);
    }

    @Override
    public Map<String,String> getParams(){return params;}
}