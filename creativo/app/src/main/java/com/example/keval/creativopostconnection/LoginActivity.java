package com.example.keval.creativopostconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by keval on 28-03-2017.
 * for more visit www.creativek.me
 */

public class LoginActivity extends AppCompatActivity {


    POSTConnection connection = null;

    public static String LOGIN_CORRECT = "RIGHT CREDENTIAL";
    public static String LOGIN_WRONG = "WRONG CREDENTIAL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button login = (Button) findViewById(R.id.login_submit);


        /**
         * Create POSTConnection object and implement doOnSuccess() and doOnFail()
         */

        try {
            connection = new POSTConnection("http://192.168.1.5:81/creativo/login.php") {

                boolean result;
                String message;

                @Override
                public void doOnSuccess() {
                    JSONObject json = getJsonResponse();
                    try {
                        result = json.getBoolean("result");
                        message = json.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result) {
                        if (message.equals(LOGIN_CORRECT)) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error : " + message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void doOnFail() {
                    try {
                        Toast.makeText(getApplicationContext(), "Error : " + getConnection().getResponseCode(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            /**
             * Now, set parameters from login button click handler
             */
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve EditText Information and create a  HashMap for parameters
                String id;
                String password;
                TextView query = (TextView) findViewById(R.id.login_id);
                id = query.getText().toString();
                query = (TextView) findViewById(R.id.login_password);
                password = query.getText().toString();

                // creating hashmap
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user-name", id);
                params.put("password", password);

                // set paramaters
                connection.setData(params);

                // You can make any changes to  HttpURLConnection object before calling Connect()

                // Execute
                connection.Connect();

            }
        });
    }
}
