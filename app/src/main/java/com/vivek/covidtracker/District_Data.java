package com.vivek.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
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

public class District_Data extends AppCompatActivity {

    int dispos = District_Activity.dispos;
    int pos = State_Activity.pos;
    RequestQueue qRequest;
    RequestQueue newQueue;
    String stateName= District_Activity.stateName;
    String districtName = District_Activity.dirtrictAdd;
    TextView disCon ;
    TextView confirm;
    TextView active;
    TextView recovered;
    TextView deaths;
    LottieAnimationView lottie;
    String zone="GREEN";
    TextView disName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district__data);
        lottie = findViewById(R.id.lottie1);
       qRequest = Volley.newRequestQueue(this);
       newQueue = Volley.newRequestQueue(this);
        active = findViewById(R.id.indActiveData);
        confirm = findViewById(R.id.indConfirmData);
        recovered = findViewById(R.id.indRecoveredData);
        deaths = findViewById(R.id.indDeathsData);
        disName = findViewById(R.id.disName);
            String url = "https://api.covidindiatracker.com/state_data.json\n";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            JSONObject stateData = response.getJSONObject(pos);
                            JSONArray districtData = stateData.getJSONArray("districtData");
                            stateName = response.getJSONObject(pos).getString("state");
                            zone = districtData.getJSONObject(dispos).getString("zone");
                            Log.d("vdistrict", districtData.getJSONObject(dispos).getString("zone"));
                            String district = districtData.getJSONObject(dispos).getString("name");
                            confirm.setText(Integer.toString(districtData.getJSONObject(dispos).getInt("confirmed")));
                            disName.setText(district);
                            Log.d("vdistrict",district);
                            if(zone.equals("RED")){
                                lottie.setAnimation("red_blink.json");
                            }else if(zone.equals("ORANGE")){
                                lottie.setAnimation("orange_blink.json");
                            }
                            else{
                                lottie.setAnimation("green_check.json");
                            }
                            lottie.loop(true);
                            lottie.playAnimation();


                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        qRequest.add(jsonArrayRequest);



        String newurl = "https://api.covid19india.org/state_district_wise.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject StateData = response.getJSONObject(stateName);
                            JSONObject DisWise = StateData.getJSONObject("districtData");
                            JSONObject DistrictData = DisWise.getJSONObject(districtName);
                            Log.d("vkm","hello");
                            Log.d("vkm", Integer.toString(DistrictData.getInt("active")));
                            active.setText(Integer.toString(DistrictData.getInt("active")));
                           recovered.setText(Integer.toString(DistrictData.getInt("recovered")));
                           deaths.setText(Integer.toString(DistrictData.getInt("deceased")));
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
        newQueue.add(jsonObjectRequest);

    }
}
