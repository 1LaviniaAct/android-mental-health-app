package com.example.licentavarianta3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.licentavarianta3.Clase.Cartonas;
import com.example.licentavarianta3.R;

import java.util.List;

public class CartonasAdapter extends ArrayAdapter<Cartonas> {

    private Context context;
    private List<Cartonas> listaCartonase;

    public CartonasAdapter(@NonNull Context context, @NonNull List<Cartonas> objects) {
        super(context, 0, objects);
        this.context = context;
        this.listaCartonase = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.element_cartonas, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.imageView);
            viewHolder.textView = view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Cartonas cartonas = listaCartonase.get(position);

        // Prefixează imaginea cu "data:image/jpeg;base64," sau "data:image/png;base64,"
        String base64Image = "data:image/png;base64," + cartonas.getImageUrl();

        // Încarcă imaginea cu Glide și setează un placeholder
        Glide.with(context)
                .load(base64Image)
                .placeholder(R.drawable.norisor_mov)  // Imaginea placeholder
                .into(viewHolder.imageView);

        viewHolder.textView.setText(cartonas.getText());

        return view;
    }

    @Override
    public int getCount() {
        return listaCartonase.size();
    }

    @Nullable
    @Override
    public Cartonas getItem(int position) {
        return listaCartonase.get(position);
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
