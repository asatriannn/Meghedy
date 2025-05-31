package com.example.meghedy;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import com.bumptech.glide.Glide;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<MusicModel> mFiles;

    MusicAdapter(Context mContext, ArrayList<MusicModel> mFiles){
       this.mContext = mContext;
       this.mFiles = mFiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.song_artist.setText(mFiles.get(position).getArtist());
        holder.song_title.setText(mFiles.get(position).getTitle());
        byte[] image = getCoverImg(mFiles.get(position).getPath());
        if (image!=null){
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.cover_img);
        }
        else {
            Glide.with(mContext)
                    .load(R.drawable.music_default_bg)
                    .into(holder.cover_img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent(mContext, PLayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView song_title, song_artist;
        ImageView cover_img;
        public MyViewHolder(View itemView){
            super(itemView);
            song_title = itemView.findViewById(R.id.song_title);
            song_artist = itemView.findViewById(R.id.song_artist);
            cover_img = itemView.findViewById(R.id.cover_img);
        }
    }

    private byte[] getCoverImg(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        byte[] cover = null;

        try {
            retriever.setDataSource(uri);
            cover = retriever.getEmbeddedPicture();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cover;
    }

}
