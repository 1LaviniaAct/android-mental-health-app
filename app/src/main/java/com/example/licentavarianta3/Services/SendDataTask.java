package com.example.licentavarianta3.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.licentavarianta3.BuildConfig;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class SendDataTask extends AsyncTask<Void, Void, Void> {
    private int punctajStima;
    private int punctajDeznadejde;
    private int punctajEmotivitate;
    private String userId;

    public SendDataTask(int punctajStima, int punctajDeznadejde, int punctajEmotivitate, String userId) {
        this.punctajStima = punctajStima;
        this.punctajDeznadejde = punctajDeznadejde;
        this.punctajEmotivitate = punctajEmotivitate;
        this.userId = userId;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(BuildConfig.SERVER_URL + "/saveResults");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("punctajStima", punctajStima);
            jsonParam.put("punctajDeznadejde", punctajDeznadejde);
            jsonParam.put("punctajEmotivitate", punctajEmotivitate);
            jsonParam.put("userId", userId);
            Log.d("DATA: ", String.valueOf(System.currentTimeMillis()));
            jsonParam.put("data", System.currentTimeMillis()); // Adăugare data actuală

            Log.i("JSON", jsonParam.toString());
            OutputStream os = conn.getOutputStream();
            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

