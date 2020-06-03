package com.vivek.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button selectState;
    RequestQueue requestQueue;
    TextView confirm;
    TextView active;
    TextView recovered;
    TextView deaths;
    TextView dangerRank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        active = findViewById(R.id.indActiveData);
        confirm = findViewById(R.id.indConfirmData);
        recovered = findViewById(R.id.indRecoveredData);
        deaths = findViewById(R.id.indDeathsData);
        selectState = findViewById(R.id.selectState);
        dangerRank =findViewById(R.id.dangerRank);

        String url = "https://api.thevirustracker.com/free-api?countryTotal=IN";

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray totalData = response.getJSONArray("countrydata");
                            JSONObject countryData = totalData.getJSONObject(0);
                            active.setText(Integer.toString(countryData.getInt("total_active_cases")));
                            confirm.setText(Integer.toString(countryData.getInt("total_cases")));
                            recovered.setText(Integer.toString(countryData.getInt("total_recovered")));
                            deaths.setText(Integer.toString(countryData.getInt("total_deaths")));
                            dangerRank.setText(Integer.toString(countryData.getInt("total_danger_rank")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);



        final Intent intent = new Intent(this,State_Activity.class);
        selectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
