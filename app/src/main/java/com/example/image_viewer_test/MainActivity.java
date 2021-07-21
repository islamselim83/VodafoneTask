package com.example.image_viewer_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //initialize variables
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    int page=1,limit=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign variables
        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        //initialize adapter
        adapter = new MainAdapter(MainActivity.this,dataArrayList);
        //set recycle View
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        // Get Data
        getData();
        // Start shimmer Effect
        shimmerFrameLayout.startShimmer();
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Check Condition
                if (scrollY==v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    // when reach last item position increase page value
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData();
                }
            }
        });
    }

    private void getData() {
        //initialize API url
        String surl = "https://picsum.photos/v2/list?page="+page+"&limit="+limit;
        //initialize string request
        StringRequest request = new StringRequest(surl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Check Condition
                if (response!=null){
                    //Hide Progress Bar
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    try {
                        // initialize json
                        JSONArray jsonArray = new JSONArray(response);
                        //parse array
                        parsearray(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Display Toast
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        //initialize request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void parsearray(JSONArray jsonArray) {
        for (int i=0; i<jsonArray.length(); i++){
            try {
                //initialize json object
                JSONObject object = jsonArray.getJSONObject(i);
                //initialize Main data
                MainData data = new MainData();
                data.setImage(object.getString("download_url"));
                data.setName(object.getString("author"));
                data.setUrl(object.getString("url"));
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // initialize adapter
            adapter = new MainAdapter(MainActivity.this,dataArrayList);
            recyclerView.setAdapter(adapter);
        }
    }
}