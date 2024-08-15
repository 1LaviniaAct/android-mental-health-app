package com.example.licentavarianta3;

import com.example.licentavarianta3.R;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
public class InfoFragment extends Fragment {
    CardView cardViewInfoTot;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        cardViewInfoTot = rootView.findViewById(R.id.cardViewInfo);

        LinearLayout despreStima = rootView.findViewById(R.id.layoutStima);
        LinearLayout despreDepresie = rootView.findViewById(R.id.layoutDepresie);
        LinearLayout despreEmpatie = rootView.findViewById(R.id.layoutEmpatie);

        despreDepresie.setOnClickListener(v -> openDeznadejdeInfoFragment());
        despreStima.setOnClickListener(v -> openStimaInfoFragment());
        despreEmpatie.setOnClickListener(v -> openEmpatieInfoFragment());

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getParentFragmentManager().addOnBackStackChangedListener(() -> {
            if (isFragmentVisibleToUser()) {
                cardViewInfoTot.setVisibility(View.VISIBLE);
            } else {
                cardViewInfoTot.setVisibility(View.GONE);
            }
        });
    }

    private boolean isFragmentVisibleToUser() {
        if (isAdded() && getParentFragmentManager() != null) {
            Fragment currentFragment = getParentFragmentManager().findFragmentById(R.id.fragment_container);
            return currentFragment instanceof InfoFragment;
        }
        return false;
    }




    private void openStimaInfoFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (!(fragmentManager.findFragmentById(R.id.fragment_container) instanceof StimaInfoFragment)) {
            switchFragment(new StimaInfoFragment());
        }
    }

    private void openDeznadejdeInfoFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (!(fragmentManager.findFragmentById(R.id.fragment_container) instanceof DeznadejdeInfoFragment)) {
            switchFragment(new DeznadejdeInfoFragment());
        }
    }

    private void openEmpatieInfoFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (!(fragmentManager.findFragmentById(R.id.fragment_container) instanceof EmpatieInfoFragment)) {
            switchFragment(new EmpatieInfoFragment());
        }
    }

    private boolean isCurrentFragment(Class<? extends Fragment> fragmentClass) {
        Fragment currentFragment = getParentFragmentManager().findFragmentById(R.id.fragment_container);
        return fragmentClass.isInstance(currentFragment);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
