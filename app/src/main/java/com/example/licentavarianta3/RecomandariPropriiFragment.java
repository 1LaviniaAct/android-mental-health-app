package com.example.licentavarianta3;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licentavarianta3.Adapter.CartonasAdapter;
import com.example.licentavarianta3.Clase.Cartonas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecomandariPropriiFragment extends Fragment {

    private ListView listView;
    private CartonasAdapter cartonasAdapter;
    private List<Cartonas> listaCartonase;
    private List<Cartonas> filteredCartonase;
    private static final String TAG = "RecomandariFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recomandari, container, false);

        listView = view.findViewById(R.id.listView);
        Spinner categorySpinner = view.findViewById(R.id.categorySpinner);

        listaCartonase = new ArrayList<>();
        filteredCartonase = new ArrayList<>();

        // Get user ID from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        if (!userId.isEmpty()) {
            fetchRecommendations(userId);
        } else {
            Log.e(TAG, "User ID is empty.");
        }

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filterRecommendations(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Show all recommendations when no category is selected
                filterRecommendations(null);
            }
        });

        return view;
    }

    private void fetchRecommendations(String userId) {
        String url = BuildConfig.SERVER_URL + "/getAllRecommendationsForUser?userId=" + userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray recommendations = response.getJSONArray("recommendations");
                            for (int i = 0; i < recommendations.length(); i++) {
                                JSONObject recomandareJson = recommendations.getJSONObject(i);
                                String category = recomandareJson.getString("categoryRosenberg");
                                String name = recomandareJson.getString("name");
                                String description = recomandareJson.getString("description");
                                String imageUrl = recomandareJson.getString("image");

                                // Log for verifying image URLs
                                Log.d(TAG, "Image URL: " + imageUrl);

                                listaCartonase.add(new Cartonas(R.drawable.norisor_mov, name, category, description, imageUrl));
                            }

                            filteredCartonase.addAll(listaCartonase);
                            cartonasAdapter = new CartonasAdapter(getContext(), filteredCartonase);
                            listView.setAdapter(cartonasAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error from server request
                        Log.e(TAG, "Volley Error: " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void filterRecommendations(String category) {
        if (cartonasAdapter == null) {
            return;
        }

        filteredCartonase.clear();

        if (category == null || category.equals("Toate")) {
            filteredCartonase.addAll(listaCartonase);
        } else {
            for (Cartonas cartonas : listaCartonase) {
                if (cartonas.getCategory().equalsIgnoreCase(category)) {
                    filteredCartonase.add(cartonas);
                }
            }
        }

        cartonasAdapter.notifyDataSetChanged();
    }
}
