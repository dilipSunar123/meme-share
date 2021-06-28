package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ViewTarget;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

//    ImageView img;
//    ProgressBar progressBar;

//    final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loader);
    String currentURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMeme();
    }


    private void loadMeme () {
//         progressBar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        currentURL = "https://meme-api.herokuapp.com/gimme";
        final ImageView imageView = (ImageView) findViewById(R.id.memeImg);
        
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentURL, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String display_url = response.getString("url");
                     Glide.with(MainActivity.this).load(display_url).into(imageView);
                    // Picasso.with(this).load(url).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d("myApp", "Error hai bhai..");
                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void shareMeme(View view) {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, "Hey Buddy! Checkout this awesome meme $url");
//        Intent chooser = Intent.createChooser(intent, "Checkout this meme..");
//        startActivity(chooser);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout this meme I got from Reddit! \n\n" + currentURL);

        Intent chooser = Intent.createChooser(intent, "Share this meme using..");


        startActivity(chooser);
    }

    public void nextMeme(View view) {
        loadMeme();
    }
}