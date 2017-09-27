package com.skripsi.coba.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.coba.IsiSurat;
import com.skripsi.coba.R;
import com.skripsi.coba.model.Model_Pencarian;

import java.util.List;

/**
 * Created by gandhi on 9/27/17.
 */

public class Adapter_Surat extends RecyclerView.Adapter<Adapter_Surat.ViewHolder> {
    private List<Model_Pencarian> model_pencarien;
    public Adapter_Surat(List<Model_Pencarian> model_pencarien){this.model_pencarien = model_pencarien;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model_Pencarian model_pencarian = model_pencarien.get(position);
        holder.NoAyat.setText(model_pencarian.getNoAyat());
        holder.NamaSurah.setText(model_pencarian.getNamaSurah());
    }

    @Override
    public int getItemCount() {
        return this.model_pencarien.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView NoAyat,NamaSurah;
        public ViewHolder(View itemView) {
            super(itemView);
            NoAyat = (TextView) itemView.findViewById(R.id.NoSurah);
            NamaSurah = (TextView) itemView.findViewById(R.id.NamaSurat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Model_Pencarian model_pencarian = model_pencarien.get(position);
                    Intent intent = new Intent(v.getContext(), IsiSurat.class);
                    intent.putExtra("no_surah",model_pencarian.getNoSurah());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
