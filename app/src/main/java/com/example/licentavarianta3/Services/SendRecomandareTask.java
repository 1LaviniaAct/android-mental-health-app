package com.example.licentavarianta3.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.licentavarianta3.BuildConfig;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendRecomandareTask extends AsyncTask<Void, Void, Boolean> {

    private String categorie;
    private String categorieRosenberg;
    private String categorieStima;
    private String categorieDeznadejde;
    private String categorieEmotivitate;
    private String nume;
    private String descriere;
    private String imagineBase64;
    private TaskListener taskListener;

    public SendRecomandareTask(String categorie, String categorieRosenberg, String categorieStima, String categorieDeznadejde, String categorieEmotivitate, String nume, String descriere, String imagineBase64, TaskListener listener) {
        this.categorie = categorie;
        this.categorieRosenberg = categorieRosenberg;
        this.categorieStima = categorieStima;
        this.categorieDeznadejde = categorieDeznadejde;
        this.categorieEmotivitate = categorieEmotivitate;
        this.nume = nume;
        this.descriere = descriere;
        this.imagineBase64 = imagineBase64;
        this.taskListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL(BuildConfig.SERVER_URL + "/addRecommendation");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("category", categorie);
            jsonParam.put("categoryRosenberg", categorieRosenberg);
            jsonParam.put("categoryStima", categorieStima);
            jsonParam.put("categoryDeznadejde", categorieDeznadejde);
            jsonParam.put("categoryEmotivitate", categorieEmotivitate);
            jsonParam.put("name", nume);
            jsonParam.put("description", descriere);
            jsonParam.put("image", imagineBase64);

            OutputStream os = conn.getOutputStream();
            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.close();

            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            Log.i("SendRecomandareTask", "HTTP Response Code: " + responseCode);
            Log.i("SendRecomandareTask", "HTTP Response Message: " + responseMessage);

            conn.disconnect();

            // Return true if successful
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            Log.e("SendRecomandareTask", "Error sending recommendation", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (taskListener != null) {
            taskListener.onTaskComplete(success);
        }
    }

    // TaskListener interface for callback
    public interface TaskListener {
        void onTaskComplete(boolean success);
    }
}
