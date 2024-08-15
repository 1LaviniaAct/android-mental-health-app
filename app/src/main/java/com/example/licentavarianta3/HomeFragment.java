package com.example.licentavarianta3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.licentavarianta3.Components.ModalDialog;

public class HomeFragment extends Fragment {

     Button butonChestionar;
     Button butonRecomandari;
     Button buttonCalendar;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        butonChestionar = rootView.findViewById(R.id.butonChestionar);
        butonRecomandari = rootView.findViewById(R.id.buttonRecomandari);
        buttonCalendar = rootView.findViewById(R.id.buttonCalendar);
//        ImageView imageView = rootView.findViewById(R.id.imageView);

        butonChestionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openModal("Track Progress", "Here you can track your progress.");
                Intent intent = new Intent(getActivity(), StimaDeSineActivity.class);
                startActivity(intent);
            }
        });

        butonRecomandari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openModal("Discover Resources", "Discover resources for your mental health journey.");
                RecomandariPropriiFragment recomandariFragment = new RecomandariPropriiFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, recomandariFragment) // Presupunând că ai un container în layout-ul activității tale principale
                        .addToBackStack(null)
                        .commit();
            }
        });

        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openModal("Join Community", "Join a supportive community for mental health.");
                CalendarFragment calendarFragment = new CalendarFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, calendarFragment) // Asigură-te că există un container cu id-ul `container` în layout
                        .addToBackStack(null)
                        .commit();
            }
        });

//        Animation pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse);
//        imageView.startAnimation(pulseAnimation);

        return rootView;
    }

    private void openModal(String title, String message) {
        ModalDialog dialog = ModalDialog.newInstance(title, message);
        dialog.show(getParentFragmentManager(), "ModalDialog");
    }
}
