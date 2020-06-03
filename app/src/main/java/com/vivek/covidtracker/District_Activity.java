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

public class District_Activity extends AppCompatActivity {

    ListView districtList;
    RequestQueue disQueue;
    ArrayList<String> disList = new ArrayList<>();

    int pos = State_Activity.pos;
    public  static int dispos;
    public  static  String stateName;
    public static String dirtrictAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_);
        Log.d("pos",Integer.toString(pos));
        disQueue = Volley.newRequestQueue(this);
        String url = "https://api.covidindiatracker.com/state_data.json\n";
        districtList = findViewById(R.id.districtList);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,disList);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            try {

                                JSONObject stateData = response.getJSONObject(pos);
                                stateName = response.getJSONObject(pos).getString("state");
                                JSONArray listOfDistrict = stateData.getJSONArray("districtData");
                                Log.d("district",Integer.toString(listOfDistrict.length()));
                                for(int i=0;i<listOfDistrict.length();i++){
                                    JSONObject districtData = listOfDistrict.getJSONObject(i);
                                    Log.d("district",districtData.getString("name"));

                                    disList.add(districtData.getString("name"));
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        districtList.setAdapter(arrayAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        disQueue.add(jsonArrayRequest);

        final Intent intent2 = new Intent(this,District_Data.class);
        districtList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dirtrictAdd = parent.getItemAtPosition(position).toString();
                dispos=position;
                startActivity(intent2);
            }
        });



    }
}
