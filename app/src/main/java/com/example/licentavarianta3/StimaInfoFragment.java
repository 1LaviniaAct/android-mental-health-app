package com.example.licentavarianta3;

import android.annotation.SuppressLint;
import com.example.licentavarianta3.R;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StimaInfoFragment extends Fragment {

    CardView cardViewInfoStima;
    TextView tvPodcast;

    public StimaInfoFragment() {
        // Required empty public constructor
    }

    public static StimaInfoFragment newInstance() {
        return new StimaInfoFragment();
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

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stima_info, container, false);
        cardViewInfoStima=rootView.findViewById(R.id.cardViewStimaInfo);
        tvPodcast=rootView.findViewById(R.id.podcastStima);
        String text="- Podcast-uri: The Confidence Chronicles, The Happiness Lab, The Mindset Mentor";
        SpannableString spannableString = new SpannableString(text);

        //seteaza urlSpan pe cuvintele care vor avea link-ul
        int start1=text.indexOf("The Confidence Chronicles");
        int end1=start1+"The Confidence Chronicles".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/watch?v=yKLaPUZ2ZYA&list=PLWtf2VIsUYg-vkERa16r_VcFNz3Bt6kLI"),start1,end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start2=text.indexOf("The Happiness Lab");
        int end2=start2+"The Happiness Lab".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/watch?v=yFALFaOMnik&list=PLWHJHzhtiAuT9cD_OArtvpBLUqupRdozE"),start2,end2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start3=text.indexOf("The Mindset Mentor");
        int end3=start3+"The Mindset Mentor".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/@mindsetmentorpodcast"),start3,end3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        tvPodcast.setMovementMethod(LinkMovementMethod.getInstance());
        tvPodcast.setText(spannableString);

        return rootView;
    }


}