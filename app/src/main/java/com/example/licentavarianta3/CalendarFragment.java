package com.example.licentavarianta3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private List<String> resultList;
    private List<String> originalResults;
    private CalendarView calendarView;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);

        resultList = new ArrayList<>();
        originalResults = new ArrayList<>();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date change
                String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, month + 1, year);
                displayResultForDate(selectedDate);
            }
        });

        fetchResults();

        return view;
    }

    private void fetchResults() {
        String url = BuildConfig.SERVER_URL + "/getAllResults";//end point catre backend, apelez ruta getAllResults din backend

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");

                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject resultObject = resultsArray.getJSONObject(i);

                                String punctajStima = resultObject.getString("punctajStima");
                                String punctajDeznadejde = resultObject.getString("punctajDeznadejde");
                                String punctajEmotivitate = resultObject.getString("punctajEmotivitate");
                                String userId = resultObject.getString("userId");
                                String data = resultObject.getString("data");

                                String resultInfo = "ID: " + userId + "\n" +
                                        "Punctaj Stima: " + punctajStima + "\n" +
                                        "Punctaj Deznadejde: " + punctajDeznadejde + "\n" +
                                        "Punctaj Emotivitate: " + punctajEmotivitate + "\n" +
                                        "Data: " + formatDate(data) + "\n"; // Format date

                                originalResults.add(resultInfo); // Add to original results
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void displayResultForDate(String selectedDate) {
        resultList.clear(); // Clear previous results

        for (String result : originalResults) {
            if (result.contains("Data: " + selectedDate)) {
                resultList.add(result);
            }
        }

        showResultsInDialog();
    }

    private void showResultsInDialog() {
        if (resultList.isEmpty()) {
            // Show a message if no results are found
            new AlertDialog.Builder(requireContext())
                    .setTitle("Results")
                    .setMessage("No results found for the selected date.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            // Display results in a dialog
            StringBuilder resultsMessage = new StringBuilder();
            for (String result : resultList) {
                resultsMessage.append(result).append("\n\n");
            }

            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_results, null);
            TextView resultsTextView = dialogView.findViewById(R.id.resultsTextView);
            resultsTextView.setText(resultsMessage.toString());

            new AlertDialog.Builder(requireContext())
                    .setTitle("Results")
                    .setView(dialogView)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    // Helper method to format date from yyyy-MM-dd'T'HH:mm:ss.SSS'Z' to dd-MM-yyyy
    private String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return dateString;
        }
    }
}
