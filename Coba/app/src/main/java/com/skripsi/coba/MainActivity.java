package com.skripsi.coba;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.coba.adapter.Adapter_Pencarian;
import com.skripsi.coba.adapter.Adapter_Surat;
import com.skripsi.coba.model.Model_Pencarian;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvView,rvSecond;
    private RecyclerView.Adapter adapter,adapterSecond;
    private RecyclerView.LayoutManager layoutManager,lmSecond;
    private List<Model_Pencarian> modelPencarien = new ArrayList<Model_Pencarian>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        rvSecond = (RecyclerView) findViewById(R.id.rv_second);
        rvSecond.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lmSecond = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        rvSecond.setLayoutManager(lmSecond);
        GetAll();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Cari(query.replace(" ", "%20"));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cari(newText.replace(" ", "%20"));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void Cari(String keyword){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Cari+keyword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            modelPencarien.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(jsonObject.getString("no_surah").equals("0")){
                                    Model_Pencarian modelPencarian = new Model_Pencarian();
                                    modelPencarian.setNoAyat(" - ");
                                    modelPencarian.setNamaSurah(" - ");
                                    modelPencarian.setTextArab(" - ");
                                    modelPencarian.setTextIndo(" Data yang dicari tidak ditemukan ");
                                    modelPencarien.add(modelPencarian);

                                }
                                else{
                                    Model_Pencarian modelPencarian = new Model_Pencarian();
                                    modelPencarian.setNoAyat("Ayat ke : "+jsonObject.getString("no_ayat"));
                                    modelPencarian.setNamaSurah(jsonObject.getString("nama_surah"));
                                    modelPencarian.setTextArab(jsonObject.getString("text_arab"));
                                    modelPencarian.setTextIndo(jsonObject.getString("text_indo"));
                                    modelPencarien.add(modelPencarian);
                                    // yang dicari ada
                                }
                                adapter = new Adapter_Pencarian(modelPencarien);
                                rvView.setAdapter(adapter);
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

    public void GetAll(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.All,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            modelPencarien.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(jsonObject.getString("no_surah").equals("0")){
                                    Model_Pencarian modelPencarian = new Model_Pencarian();
                                    modelPencarian.setNoAyat(" - ");
                                    modelPencarian.setNamaSurah(" Data yang dicari tidak ditemukan ");
                                    modelPencarien.add(modelPencarian);

                                }
                                else{
                                    Model_Pencarian modelPencarian = new Model_Pencarian();
                                    modelPencarian.setNoAyat("Surat ke : "+jsonObject.getString("no_surah"));
                                    modelPencarian.setNoSurah(jsonObject.getString("no_surah"));
                                    modelPencarian.setNamaSurah(jsonObject.getString("nama_surah"));
                                    modelPencarien.add(modelPencarian);
                                    // yang dicari ada
                                }
                                adapterSecond = new Adapter_Surat(modelPencarien);
                                rvSecond.setAdapter(adapterSecond);
                                adapterSecond.notifyDataSetChanged();

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
