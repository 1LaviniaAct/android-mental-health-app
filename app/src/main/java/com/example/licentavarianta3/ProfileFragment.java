package com.example.licentavarianta3;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    TextView tvStima;
    TextView tvDeznadejde;
    TextView tvEmotivitate;
    TextView tvName;
    TextView tvPosition;
    TextView tvDescription;
    Button buttonAddProfileDetails;

    // Variabile pentru a stoca detaliile profilului
    private String currentName = "";
    private String currentPosition = "";
    private String currentDescription = "";
    private int currentStima = 0;
    private int currentDeznadejde = 0;
    private int currentEmotivitate = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvStima = view.findViewById(R.id.tvStima);
        tvDeznadejde = view.findViewById(R.id.tvDeznadejde);
        tvEmotivitate = view.findViewById(R.id.tvEmotivitate);
        tvName = view.findViewById(R.id.textViewName);
        tvPosition = view.findViewById(R.id.textViewPosition);
        tvDescription = view.findViewById(R.id.textViewDescription);
        buttonAddProfileDetails = view.findViewById(R.id.buttonAddProfileDetails);

        buttonAddProfileDetails.setOnClickListener(v -> showProfileDetailsDialog());

        // Fetch profile details
        getProfileDetails();

        return view;
    }

    private void showProfileDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_profile_details, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextPosition = dialogView.findViewById(R.id.editTextPosition);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        EditText editTextStima = dialogView.findViewById(R.id.editTextStima);
        EditText editTextDeznadejde = dialogView.findViewById(R.id.editTextDeznadejde);
        EditText editTextEmotivitate = dialogView.findViewById(R.id.editTextEmotivitate);

        // Prepopulează câmpurile cu datele existente
        editTextName.setText(currentName);
        editTextPosition.setText(currentPosition);
        editTextDescription.setText(currentDescription);
        editTextStima.setText(String.valueOf(currentStima));
        editTextDeznadejde.setText(String.valueOf(currentDeznadejde));
        editTextEmotivitate.setText(String.valueOf(currentEmotivitate));

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = editTextName.getText().toString();
            String position = editTextPosition.getText().toString();
            String description = editTextDescription.getText().toString();
            int stima = Integer.parseInt(editTextStima.getText().toString());
            int deznadejde = Integer.parseInt(editTextDeznadejde.getText().toString());
            int emotivitate = Integer.parseInt(editTextEmotivitate.getText().toString());

            saveProfileDetails(name, position, description, stima, deznadejde, emotivitate);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void saveProfileDetails(String name, String position, String description, int stima, int deznadejde, int emotivitate) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        String url = BuildConfig.SERVER_URL + "/saveProfileDetails";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userId", userId);
            jsonBody.put("name", name);
            jsonBody.put("position", position);
            jsonBody.put("description", description);
            jsonBody.put("punctajStima", stima);
            jsonBody.put("punctajDeznadejde", deznadejde);
            jsonBody.put("punctajEmotivitate", emotivitate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    // Update the UI with the new data
                    tvName.setText(name);
                    tvPosition.setText(position);
                    tvDescription.setText(description);
                    tvStima.setText(String.format("Stima de sine: %d", stima));
                    tvDeznadejde.setText(String.format("Deznadejde: %d", deznadejde));
                    tvEmotivitate.setText(String.format("Emotivitate: %d", emotivitate));

                    // Actualizează variabilele curente
                    currentName = name;
                    currentPosition = position;
                    currentDescription = description;
                    currentStima = stima;
                    currentDeznadejde = deznadejde;
                    currentEmotivitate = emotivitate;
                },
                error -> Log.e("ProfileFragment", "Error: " + error.getMessage()));

        requestQueue.add(jsonObjectRequest);
    }

    private void getProfileDetails() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        String url = BuildConfig.SERVER_URL + "/getProfileResults?userId=" + userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String name = response.getString("name");
                        String position = response.getString("position");
                        String description = response.getString("description");
                        int stima = response.getInt("punctajStima");
                        int deznadejde = response.getInt("punctajDeznadejde");
                        int emotivitate = response.getInt("punctajEmotivitate");

                        tvName.setText(name);
                        tvPosition.setText(position);
                        tvDescription.setText(description);
                        tvStima.setText(String.format("Stima de sine: %d", stima));
                        tvDeznadejde.setText(String.format("Deznadejde: %d", deznadejde));
                        tvEmotivitate.setText(String.format("Emotivitate: %d", emotivitate));

                        // Salvează detaliile curente în variabile
                        currentName = name;
                        currentPosition = position;
                        currentDescription = description;
                        currentStima = stima;
                        currentDeznadejde = deznadejde;
                        currentEmotivitate = emotivitate;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("ProfileFragment", "Error: " + error.getMessage()));

        requestQueue.add(jsonObjectRequest);
    }
}
