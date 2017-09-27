package com.skripsi.coba.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.coba.R;
import com.skripsi.coba.model.Model_Pencarian;

import java.util.List;

/**
 * Created by gandhi on 9/27/17.
 */

public class Adapter_Pencarian extends RecyclerView.Adapter<Adapter_Pencarian.ViewHolder> {
    private List<Model_Pencarian> model_pencarien;
    public Adapter_Pencarian(List<Model_Pencarian> model_pencarien){this.model_pencarien = model_pencarien;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pencarian, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model_Pencarian model_pencarian = model_pencarien.get(position);
        holder.NoAyat.setText(model_pencarian.getNoAyat());
        holder.TextArab.setText(model_pencarian.getTextArab());
        holder.TextIndo.setText(model_pencarian.getTextIndo());
        holder.NamaSurah.setText(model_pencarian.getNamaSurah());
    }

    @Override
    public int getItemCount() {
        return this.model_pencarien.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView NoAyat,NamaSurah,TextIndo,TextArab;
        public ViewHolder(View itemView) {
            super(itemView);
            NoAyat = (TextView) itemView.findViewById(R.id.NoAyat);
            NamaSurah = (TextView) itemView.findViewById(R.id.NamaSurat);
            TextIndo = (TextView) itemView.findViewById(R.id.Indo);
            TextArab = (TextView) itemView.findViewById(R.id.Arab);

        }
    }
}
