package com.example.keval.roomonrent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by keval Choudhary on 10-04-2017.
 * For more visit www.creativek.me
 */

public abstract class POSTConnection {

    // DECLARATION
    private URL serverUrl = null;
    private HttpURLConnection connection = null;
    private HashMap<String, String> data = new HashMap<>();
    private JSONObject jsonResponse = null;
    private Bitmap bitmap = null;

    // GETTER AND SETTER
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }


    public POSTConnection(String serverUrl) throws MalformedURLException {
        this.serverUrl = new URL(serverUrl);
    }

    public void Connect() {
        new BackgroundWorker().execute();
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(JSONObject jsonResponse) {
        this.jsonResponse = jsonResponse;
    }


    public Boolean HttpConnect() throws IOException {

        // Connection Establish
        connection = (HttpURLConnection) serverUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setChunkedStreamingMode(0);

        // Write parameters
        WriteParameters();

        // Connect
        connection.connect();
        StringBuilder sb = new StringBuilder();


        //Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        if (sb.toString().isEmpty()) {
            setJsonResponse(null);
        } else {
            try {
                setJsonResponse(new JSONObject(sb.toString()));
            } catch (JSONException e) {
                setJsonResponse(null);
                e.printStackTrace();
            }
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return true;
        }
        return false;
    }

    // Parameter writer
    public void WriteParameters() throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(out);
        writer.write((parameterEncoder()));
        writer.flush();
        writer.close();
    }


    public boolean getImage() throws IOException {
        // Connection Establish
        connection = (HttpURLConnection) serverUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setChunkedStreamingMode(0);

        // Write parameters
        WriteParameters();

        // Connect
        connection.connect();
        StringBuilder sb = new StringBuilder();


        //Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        setBitmap(BitmapFactory.decodeStream(connection.getInputStream()));


        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return true;
        }
        return false;
    }

    // Parameter Encoder
    String parameterEncoder() throws UnsupportedEncodingException {
        StringBuilder string = new StringBuilder();
        Set<String> keys = data.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            string.append(URLEncoder.encode(key, "UTF-8"));
            string.append("=");
            string.append(URLEncoder.encode(data.get(key), "UTF-8"));
            if (iterator.hasNext()) {
                string.append("&");
            }
        }
        return string.toString();
    }

    // Programmer specified methods
    public abstract void doOnSuccess();

    public abstract void doOnFail();


    // Download Image
    public void downloadImage() {
        new BackgroundWorkerImage().execute();
    }

    // Async Class
    public class BackgroundWorker extends AsyncTask<String, Integer, Boolean> {
        boolean result = false;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                result = HttpConnect();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (result) {
                doOnSuccess();
            } else {
                doOnFail();
            }
        }
    }


    // Async Class
    public class BackgroundWorkerImage extends AsyncTask<String, Integer, Boolean> {
        boolean result = false;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                result = getImage();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (result) {
                doOnSuccess();
            } else {
                doOnFail();
            }
        }
    }


}
