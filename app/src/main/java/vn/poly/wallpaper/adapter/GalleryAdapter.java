package vn.poly.wallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.wallpaper.R;
import vn.poly.wallpaper.activity.PhotoGalleryActivity;
import vn.poly.wallpaper.model.Gallery;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyHolder> {
    List<Gallery> galleryList;
    Context context;

    public GalleryAdapter(List<Gallery> galleryList, Context context) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);

        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.tvTitle.setText(galleryList.get(position).getTitle().getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PhotoGalleryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("idPhoto",galleryList.get(position).getGalleryId());
                intent.putExtras(bundle);
                context.startActivity(intent);




            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CardView cvTitle;
        TextView tvTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cvTitle = itemView.findViewById(R.id.cvTitle);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }
}
