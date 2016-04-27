package com.example.simone.booksharing;

/**
 * Created by simone on 27/04/2016.
 */
public class ItemBook {




    /*
  RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=isbn:"
                + isbn.getText(), new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ris = new JSONObject(response);
                            JSONArray arr = ris.getJSONArray("items");
                            ris = arr.getJSONObject(0);
                            ris = ris.getJSONObject("volumeInfo");
                                    title.setText(ris.getString("title"));
                        }
                        catch(Exception e){

                            }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                title.setText("Nessuna connesione");
            }
        });


     */
}
