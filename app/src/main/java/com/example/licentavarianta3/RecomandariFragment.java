package com.example.licentavarianta3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.licentavarianta3.Adapter.CartonasAdapter;
import com.example.licentavarianta3.Clase.Cartonas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class RecomandariFragment extends Fragment {

    private ListView listView;
    private Spinner categorySpinner;
    private CartonasAdapter cartonasAdapter;
    private List<Cartonas> listaCartonase;
    private List<Cartonas> filteredCartonase;
    private static final String TAG = "RecomandariFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recomandari, container, false);

        listView = view.findViewById(R.id.listView);
        categorySpinner = view.findViewById(R.id.categorySpinner);

        listaCartonase = new ArrayList<>();
        filteredCartonase = new ArrayList<>();

        String url = BuildConfig.SERVER_URL + "/getRecommendations";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject recomandareJson = response.getJSONObject(i);
                                String category = recomandareJson.getString("category");
                                String name = recomandareJson.getString("name");
                                String description = recomandareJson.getString("description");
                                String imageUrl = recomandareJson.getString("image");

                                listaCartonase.add(new Cartonas(R.drawable.norisor_mov, name, category, description, imageUrl));
                            }

                            filteredCartonase.addAll(listaCartonase);
                            cartonasAdapter = new CartonasAdapter(getContext(), filteredCartonase);
                            listView.setAdapter(cartonasAdapter);

                            // Set default spinner selection after adapter initialization
                            categorySpinner.setSelection(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestionează eroarea de la cererea către server
                        Log.e(TAG, "Volley Error: " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(jsonArrayRequest);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filterRecommendations(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterRecommendations("Toate");
            }
        });

        return view;
    }

    private void filterRecommendations(String category) {
        if (cartonasAdapter == null) {
            // Adapter is not initialized, return early
            return;
        }

        filteredCartonase.clear();

        if (category.equals("Toate")) {
            filteredCartonase.addAll(listaCartonase);
        } else {
            for (Cartonas cartonas : listaCartonase) {
                if (cartonas.getCategory().equals(category)) {
                    filteredCartonase.add(cartonas);
                }
            }
        }

        cartonasAdapter.notifyDataSetChanged();
    }
}
