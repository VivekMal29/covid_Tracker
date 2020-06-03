package com.vivek.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class State_Activity extends AppCompatActivity {
    public static  int pos=0;
    ListView stateList;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    RequestQueue rQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_);


        stateList = findViewById(R.id.stateList);

       rQueue = Volley.newRequestQueue(this);
        String url = "https://api.covidindiatracker.com/state_data.json\n";
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for(int i=0;i<response.length();i++) {

                                JSONObject stateData = response.getJSONObject(i);
                                String stt = stateData.getString("state");
                                Log.d("states",stateData.getString("state"));
                                arrayList.add(stt);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        stateList.setAdapter(arrayAdapter);
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        rQueue.add(jsonArrayRequest);
        final Intent intent = new Intent(this,Select_District.class);
        stateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                startActivity(intent);
            }
        });




    }

}
