package vn.poly.wallpaper.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.poly.wallpaper.R;
import vn.poly.wallpaper.adapter.GalleryAdapter;
import vn.poly.wallpaper.model.ExampleGallery;
import vn.poly.wallpaper.model.Gallery;

public class GalleriesActivity extends AppCompatActivity {
    RecyclerView rvGallery;
    SwipeRefreshLayout srlGallery;
    List<Gallery> galleryList;
    GalleryAdapter galleryAdapter;
    GridLayoutManager gridLayoutManager;
    ExampleGallery exampleGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleries);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvGallery = findViewById(R.id.rvGallery);
        srlGallery = findViewById(R.id.srlGallery);
        galleryList = new ArrayList<>();
        FastGallery();
        srlGallery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FastGallery();
                srlGallery.setRefreshing(false);
            }
        });
    }

    private void FastGallery() {
        AndroidNetworking.get("https://www.flickr.com/services/rest/?method=flickr.galleries.getList&api_key=93b953040857b25af85dccc492c02c95&user_id=189489416%40N08&continuation=10&photo_ids=72157715267608028&format=json&nojsoncallback=1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responseq) {
                        exampleGallery = new Gson().fromJson(responseq.toString(), ExampleGallery.class);
                        galleryList = exampleGallery.getGalleries().getGallery();
                        galleryAdapter = new GalleryAdapter(galleryList, GalleriesActivity.this);
                        rvGallery.setAdapter(galleryAdapter);
                        rvGallery.setHasFixedSize(true);
                        gridLayoutManager = new GridLayoutManager(GalleriesActivity.this,2);
                        rvGallery.setLayoutManager(gridLayoutManager);
                        Log.e("GL", galleryList.size() + "");
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.slide_in_from_left, R.xml.slide_out_to_right);

    }
}