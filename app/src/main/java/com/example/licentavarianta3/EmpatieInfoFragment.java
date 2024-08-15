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

public class EmpatieInfoFragment extends Fragment {

    TextView tvPodcast;

    public EmpatieInfoFragment() {
        // Required empty public constructor
    }


    public static EmpatieInfoFragment newInstance() {
       return new EmpatieInfoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_empatie_info, container, false);
        tvPodcast=rootView.findViewById(R.id.podcastEmpatie);
        String text="- Podcast-uri: Brené Brown: Unlocking Us, The Kindness Podcast, TED Radio Hour";
        SpannableString spannableString = new SpannableString(text);

        //seteaza urlSpan pe cuvintele care vor avea link-ul
        int start1=text.indexOf("Brené Brown: Unlocking Us");
        int end1=start1+"The Kindness Podcast".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/watch?v=vJAD4QXwLhQ"),start1,end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start2=text.indexOf("The Kindness Podcast");
        int end2=start2+"The Kindness Podcast".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/watch?v=44iAPrQoYU8"),start2,end2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start3=text.indexOf("TED Radio Hour");
        int end3=start3+"TED Radio Hour".length();
        spannableString.setSpan(new URLSpan("https://www.youtube.com/watch?v=scwXm3-KDZM&list=PLAWu34VO7OtKp0IWvv7YIUqGXb7Cw7eYi"),start3,end3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        tvPodcast.setMovementMethod(LinkMovementMethod.getInstance());
        tvPodcast.setText(spannableString);

        return rootView;
    }
}