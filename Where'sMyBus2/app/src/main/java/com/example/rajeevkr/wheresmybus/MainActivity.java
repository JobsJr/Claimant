package com.example.rajeevkr.wheresmybus;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rajeevkr.wheresmybus.parser.PlatformParser;
import com.example.rajeevkr.wheresmybus.tasks.ParserTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private Button mScrapButton;
    private Element mRouteElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScrapButton = (Button) findViewById(R.id.btn_scrap);
        mScrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleScrapping();
            }
        });
    }

    private void handleScrapping() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                "http://www.travel2karnataka.com/bangalore_bus_routes.htm",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            new ParserTask(response,MainActivity.this).execute();
                        } else {
                            //TODO:Handle this case
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rajeev", "Failure!!");

            }
        });

        queue.add(stringRequest);
    }
}
