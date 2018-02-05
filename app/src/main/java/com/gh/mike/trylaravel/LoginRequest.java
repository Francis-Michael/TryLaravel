package com.gh.mike.trylaravel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 8/30/17.
 */

public class LoginRequest extends StringRequest {
   //private static final String  REGISTER_REQUEST_URL="https://convulsant-verbs.000webhostapp.com/mlogin?api_token=sLZqlsQmAegj&email=me@g.com&password=123456";



      public static String  REGISTER_REQUEST_URL="https://convulsant-verbs.000webhostapp.com/mlogin?api_token="+LoginActivity.token1+"&email="+LoginActivity.username1+"&password="+LoginActivity.password1;

    private Map<String, String> params;
    public LoginRequest( Response.Listener<String> listener) {
        super(Request.Method.GET, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        LoginActivity.password1 ="";
        LoginActivity.username1 ="";//        params.put("email", username);
//        params.put("password", password);
//        params.put("api_token", "sLZqlsQmAegj");
    }

    @Override
    public Map<String,String> getParams(){return params;}
}
