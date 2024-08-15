package com.example.licentavarianta3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.licentavarianta3.Services.SendRecomandareTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AdaugaRecomandareFragment extends Fragment implements SendRecomandareTask.TaskListener {

    private EditText etNume, etDescriere;
    private Spinner spinnerCategorie, spinnerCategorieRosenberg, spinnerCategorieStima, spinnerCategorieDeznadejde, spinnerCategorieEmotivitate;
    private Button btnAdauga, btnSelecteazaImagine;
    private ImageView imageView;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap imagineSelectata;

    public AdaugaRecomandareFragment() {
        // Constructor gol obligatoriu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_adauga_recomandare, container, false);

        etNume = view.findViewById(R.id.etNume);
        etDescriere = view.findViewById(R.id.etDescriere);
        spinnerCategorie = view.findViewById(R.id.spinnerCategorie);
        spinnerCategorieRosenberg = view.findViewById(R.id.spinnerCategorieRosenberg);

        //28 iunie
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.categoriiRosenberg, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerCategorieRosenberg.setAdapter(adapter);
        //pana aici pe 28 iunie

        spinnerCategorieStima = view.findViewById(R.id.spinnerCategorieStima);
        spinnerCategorieDeznadejde = view.findViewById(R.id.spinnerCategorieDeznadejde);
        spinnerCategorieEmotivitate = view.findViewById(R.id.spinnerCategorieEmotivitate);
        btnAdauga = view.findViewById(R.id.btnAdauga);
        btnSelecteazaImagine = view.findViewById(R.id.btnSelecteazaImagine);
        imageView = view.findViewById(R.id.imageView);

        btnSelecteazaImagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteazaImagine();
            }
        });

        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaugaRecomandare();
            }
        });

        return view;
    }

    private void selecteazaImagine() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/png", "image/jpeg", "image/jpg"});
        startActivityForResult(Intent.createChooser(intent, "Selectați imaginea"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            try {
                // Verifica tipul imaginii selectate
                String fileType = getActivity().getContentResolver().getType(data.getData());
                if (fileType != null && (fileType.equals("image/png") || fileType.equals("image/jpeg") || fileType.equals("image/jpg"))) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    imageView.setImageBitmap(bitmap);
                    imagineSelectata = bitmap;
                } else {
                    Toast.makeText(getActivity(), "Selectați o imagine validă (PNG, JPG sau JPEG)!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Eroare la încărcarea imaginii!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void adaugaRecomandare() {
        String categorie = spinnerCategorie.getSelectedItem().toString();
        String categorieRosenberg = spinnerCategorieRosenberg.getSelectedItem().toString();
        String categorieStima = spinnerCategorieStima.getSelectedItem().toString();
        String categorieDeznadejde = spinnerCategorieDeznadejde.getSelectedItem().toString();
        String categorieEmotivitate = spinnerCategorieEmotivitate.getSelectedItem().toString();
        String nume = etNume.getText().toString().trim();
        String descriere = etDescriere.getText().toString().trim();

        if (nume.isEmpty() || descriere.isEmpty() || imagineSelectata == null) {
            Toast.makeText(getActivity(), "Completați toate câmpurile și selectați o imagine!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Redimensionare și compresie a imaginii
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imagineSelectata, imagineSelectata.getWidth() / 2, imagineSelectata.getHeight() / 2, true);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] imagineBytes = byteArrayOutputStream.toByteArray();
        String imagineBase64 = Base64.encodeToString(imagineBytes, Base64.DEFAULT);

        // Trimite datele către server folosind SendRecomandareTask
        new SendRecomandareTask(categorie, categorieRosenberg, categorieStima, categorieDeznadejde, categorieEmotivitate, nume, descriere, imagineBase64, this).execute();
    }

    @Override
    public void onTaskComplete(boolean success) {
        if (success) {
            Toast.makeText(getActivity(), "Recomandarea a fost adăugată cu succes!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Eroare la adăugarea recomandării!", Toast.LENGTH_SHORT).show();
        }
    }
}
