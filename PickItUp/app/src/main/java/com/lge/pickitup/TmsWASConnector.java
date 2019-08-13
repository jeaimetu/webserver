package com.lge.pickitup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;


public class TmsWASConnector {
    private static final String LOG_TAG = "TmsWASConnector";
    private static final TmsWASConnector ourInstance = new TmsWASConnector();
    private static final String SERVER_URL = "https://tmsproto-py.herokuapp.com/";

    public static TmsWASConnector getInstance() {
        return ourInstance;
    }

    private TmsWASConnector() {
    }

    private String parseJSON(JSONObject json) throws JSONException {
//        Weather w = new Weather();
//        w.setTemprature(json.getJSONObject("main").getInt("temp"));
//        w.setCity(json.getString("name"));
        return json.toString();
    }


    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public void getCluster(int clusterNum) {
        String urlString = SERVER_URL;

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
//            urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON
            Log.i(LOG_TAG, parseJSON(json));
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("URL Connection failed");
            e.printStackTrace();
        }
    }

    public void setClusters() {
        // TODO
    }

    public void setRoute(int clusterNum) {
        // TODO
    }
}
