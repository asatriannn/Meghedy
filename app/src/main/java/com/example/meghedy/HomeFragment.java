package com.example.meghedy;

import static com.example.meghedy.MainActivity.musicModelList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {

    RecyclerView songs;
    MusicAdapter musicAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        songs = view.findViewById(R.id.songs);
        songs.setHasFixedSize(true);
        if (musicModelList.size() > 0) {
            musicAdapter = new MusicAdapter(getContext(), musicModelList);
            songs.setAdapter(musicAdapter);
            songs.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }



        return view;
    }
}