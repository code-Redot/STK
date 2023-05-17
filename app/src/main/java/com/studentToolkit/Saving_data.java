package com.studentToolkit;

import android.content.Context;
import android.content.SharedPreferences;
// This class is used to save the user data (id and username) so we can use it at any class


public class Saving_data {

    public void storing_user(Context context,String account_id, String account_name,String account_image,String account_email ) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("account_name",account_name);
        editor.putString("account_image",account_image);
        editor.putString("account_email",account_email);
        editor.putString("account_id",account_id);
        editor.apply();
    }



}
