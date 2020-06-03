package com.vivek.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class Select_District extends AppCompatActivity {

    Button selectDistrict;
    RequestQueue rQueue;
    int pos = State_Activity.pos;
    TextView confirm;
    TextView active;
    TextView recovered;
    TextView deaths;

    TextView statName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__district);
        selectDistrict = findViewById(R.id.selectDistrict);
        active = findViewById(R.id.indActiveData);
        confirm = findViewById(R.id.indConfirmData);
        recovered = findViewById(R.id.indRecoveredData);
        deaths = findViewById(R.id.indDeathsData);
        statName = findViewById(R.id.stateName);
        rQueue = Volley.newRequestQueue(this);
        String url = "https://api.covidindiatracker.com/state_data.json\n";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            JSONObject stateData = response.getJSONObject(pos);
                            active.setText(Integer.toString(stateData.getInt("active")));
                            confirm.setText(Integer.toString(stateData.getInt("confirmed")));
                            recovered.setText(Integer.toString(stateData.getInt("recovered")));
                            deaths.setText(Integer.toString(stateData.getInt("deaths")));
                            statName.setText(stateData.getString("state"));


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
        rQueue.add(jsonArrayRequest);


       final Intent intent1 = new Intent(this,District_Activity.class);
        selectDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });





    }
}
