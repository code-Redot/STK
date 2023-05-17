package com.studentToolkit.Notification;

import android.content.Context;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMSend {

    private static String BASE_URL="https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY="key=AAAAjkaPku4:APA91bFNeWtH3rzAvwiJLRSt2_t6RIhepm33FEcxWmsrPAtM4qTH-iytE4WYjnxiKR7b4sTCqhB45-BWb-SfZ5v1l63WuxjQTCpZSJ_B_q-TQ3-zWuJ0AgD5VD-4TWOZy6OsrKS23Gf3";

    public static void pushNotification(Context context, String token,String title,String message,String type,String id)
    {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RequestQueue queue= Volley.newRequestQueue(context);
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("to",token);
            JSONObject notification=new JSONObject();
            notification.put("title",title);
            notification.put("body",message);
            jsonObject.put("notification",notification);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError{
                    Map<String,String>params=new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("Authorization",SERVER_KEY);
                    return params;
                }
            };
            queue.add(jsonObjectRequest);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
