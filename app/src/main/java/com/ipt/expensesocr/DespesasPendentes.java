package com.ipt.expensesocr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class DespesasPendentes extends AppCompatActivity {

    ScrollView viewDespesas;
    Button buttonRefresh;
    DespesaPendente despesaPendente;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas_pendentes);
        viewDespesas = findViewById(R.id.scrollViewDespesas);
        buttonRefresh = findViewById(R.id.buttonGetDespesas);
        getDespesas();
    }


    protected void getDespesas(){
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(com.ipt.expensesocr.DespesasPendentes.this);
                String url ="https://my-json-server.typicode.com/helderfoca/ExpensesOCR/despesas";
                // Request a string response from the provided URL.
                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray res) {
                            for (int i = 0; i < res.length() ; i++) {
                                try{
                                    JSONObject obj = res.getJSONObject(i);
                                    despesaPendente=new DespesaPendente(context);
                                    viewDespesas.addView(despesaPendente.despesTextView(getApplicationContext(),obj.toString()));
                                    Log.e("here","here");
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                viewDespesas.addView(despesaPendente.despesTextView(getApplicationContext(),"Erro ao receber dados."));
                                Log.e("here2","here2");
                            }
                        });

                // Add the request to the RequestQueue.
                queue.add(req);
            }
        });
    }
}
