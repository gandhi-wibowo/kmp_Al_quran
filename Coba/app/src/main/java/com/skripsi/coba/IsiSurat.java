package com.skripsi.coba;

import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.coba.adapter.Adapter_Pencarian;
import com.skripsi.coba.model.Model_Pencarian;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IsiSurat extends AppCompatActivity {
    String idSurat;
    private RecyclerView rv_surat;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lmSurat;
    private List<Model_Pencarian> model_pencarien = new ArrayList<Model_Pencarian>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_surat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Bundle b = getIntent().getExtras();
        idSurat = b.getString("no_surah");
        rv_surat = (RecyclerView) findViewById(R.id.rv_main);
        rv_surat.setHasFixedSize(true);
        lmSurat = new LinearLayoutManager(this);
        rv_surat.setLayoutManager(lmSurat);
        adapter = new Adapter_Pencarian(model_pencarien);
        rv_surat.setAdapter(adapter);
        GetSurat(idSurat);
    }

    private void GetSurat(String noSurat){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Surah+noSurat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            model_pencarien.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(jsonObject.getString("no_surah").equals("0")){
                                    Model_Pencarian modelPencarian = new Model_Pencarian();
                                    modelPencarian.setNoAyat(" - ");
                                    modelPencarian.setNamaSurah(" - ");
                                    modelPencarian.setTextArab(" - ");
                                    modelPencarian.setTextIndo(" Data yang dicari tidak ditemukan ");
                                    model_pencarien.add(modelPencarian);
                                }
                                else{
                                    Model_Pencarian modelPencarian = new Model_Pencarian();
                                    modelPencarian.setNoAyat("Ayat ke : "+jsonObject.getString("no_ayat"));
                                    modelPencarian.setNamaSurah(jsonObject.getString("nama_surah"));
                                    modelPencarian.setTextArab(jsonObject.getString("text_arab"));
                                    modelPencarian.setTextIndo(jsonObject.getString("text_indo"));
                                    model_pencarien.add(modelPencarian);
                                    // yang dicari ada
                                }
                                adapter = new Adapter_Pencarian(model_pencarien);
                                rv_surat.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(getCurrentFocus(), "Periksa koneksi internet ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
        ) {
            @Override
            public String getBodyContentType() { return "application/x-www-form-urlencoded";}
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
