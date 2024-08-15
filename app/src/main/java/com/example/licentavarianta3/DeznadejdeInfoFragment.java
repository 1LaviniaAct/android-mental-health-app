package com.example.licentavarianta3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.example.licentavarianta3.R;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeznadejdeInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeznadejdeInfoFragment extends Fragment {

    TextView tvPodcast;

    public DeznadejdeInfoFragment() {
        // Required empty public constructor
    }


    public static DeznadejdeInfoFragment newInstance() {
        return new DeznadejdeInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Presupunând că ai două argumente pasate, le poți prelua așa:
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deznadejde_info, container, false);
        tvPodcast=rootView.findViewById(R.id.podcastDeznadejde);
        String text="- Podcast-uri: The Hilarious World of Depression, The Mental Illness Happy Hour,The Happiness Lab";
        SpannableString spannableString = new SpannableString(text);

        //seteaza urlSpan pe cuvintele care vor avea link-ul
        int start1=text.indexOf("The Hilarious World of Depression");
        int end1=start1+"The Hilarious World of Depression".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/@thehilariousworldofdepress2874"),start1,end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start2=text.indexOf("The Mental Illness Happy Hour");
        int end2=start2+"The Mental Illness Happy Hour".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/@thementalillnesshappyhour5293"),start2,end2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start3=text.indexOf("The Happiness Lab");
        int end3=start3+"The Happiness Lab".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/@DrLaurieSantos"),start3,end3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        tvPodcast.setMovementMethod(LinkMovementMethod.getInstance());
        tvPodcast.setText(spannableString);

        return rootView;
    }
}