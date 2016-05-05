package com.example.simone.booksharing;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by simone on 05/05/2016.
 */
public class HomeCreationSlider {

    public static void start(){
        UnigeServerConnection unigeCon = new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public Map<String, String> getParams() {
                return null;
            }

            @Override
            public String getURL() {
                return null;
            }
        });
    }
}
