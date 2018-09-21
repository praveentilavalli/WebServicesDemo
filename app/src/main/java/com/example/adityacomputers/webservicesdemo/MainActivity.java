package com.example.adityacomputers.webservicesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    TextView txtweather;
    Button btnweather;
    RequestQueue requestqueue;
    //url to get weather information
    final String WEATHER_URL = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create the request queue of volley
        requestqueue = Volley.newRequestQueue(MainActivity.this);
        txtweather = (TextView) findViewById(R.id.txtweather);
        btnweather = (Button) findViewById(R.id.btnwheatherinfo);
        btnweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //  call the json requesting method
               sendJsonRequest();
            }
        });
    }

    //method to get weather information
    private void sendJsonRequest() {
        //initiate the jsonrequest object and attach method url and get the response in the onResponse method
        JsonObjectRequest jsonobjetrequest = new JsonObjectRequest(Request.Method.GET, WEATHER_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String result="";
                try {
                    StringBuffer buffer=new StringBuffer();
                    JSONObject object;
                    //code to parse the json data from web service
                    Iterator<String> iterator=response.keys();
                    while(iterator.hasNext())
                    {
                        String key=iterator.next();
                        if(response.get(key) instanceof JSONObject)
                        {
                            buffer.append(key);
                            buffer.append("\n");
                            object=response.getJSONObject(key);
                            Iterator<String> iterator1=object.keys();
                            while(iterator1.hasNext())
                            {
                                String key1=iterator1.next();
                                String value=object.getString(key1);
                                buffer.append(key1+"=");
                                buffer.append(value);
                                buffer.append("\n");
                            }
                        }
                        else if(response.get(key) instanceof JSONArray)
                        {
                            buffer.append(key);
                            buffer.append("\n");
                            JSONArray jsonArray=response.getJSONArray(key);
                            object=jsonArray.getJSONObject(0);
                            Iterator<String> iterator1=object.keys();
                            while(iterator1.hasNext())
                            {
                                String key1=iterator1.next();
                                String value=object.getString(key1);
                                buffer.append(key1+"=");
                                buffer.append(value);
                                buffer.append("\n");
                            }
                        }
                        else {
                            String value = response.getString(key);
                            buffer.append(key+"=");
                            buffer.append(value);
                            buffer.append("\n");
                        }
                    }
                    result=buffer.toString();
                } catch (JSONException e) {
                    txtweather.setText(e.getMessage());
                }
                //display the result in textview
                txtweather.setText(result);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //dispaly error message
                        txtweather.setText("data not came something error");
                    }
                });
        //add request to requestqueue
        requestqueue.add(jsonobjetrequest);

    }
}
