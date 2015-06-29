package com.kougasoft.spotifyclone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;

public class ArtistSearchFragment extends Fragment {
    List<MyArtist> mArtistList = new ArrayList<>();
    MyAdapter mAdapter;

    public ArtistSearchFragment() {
        new FetchArtistsTask().execute("Imagine");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.lvArtists);

        mAdapter = new MyAdapter(mArtistList);
        lv.setAdapter(mAdapter);

        return rootView;
    }

    public class FetchArtistsTask extends AsyncTask<String, Void, ArtistsPager> {

        @Override
        protected ArtistsPager doInBackground(String... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            return spotify.searchArtists(params[0]);
        }

        @Override
        protected void onPostExecute(ArtistsPager artistsPager) {
            super.onPostExecute(artistsPager);
            if(artistsPager == null)
                return;

            mArtistList.clear();
            for (int i = 0; i < artistsPager.artists.items.size(); i++) {
                String image = artistsPager.artists.items.get(i).images.isEmpty() ? "" : artistsPager.artists.items.get(i).images.get(0).url;
                mArtistList.add(new MyArtist(artistsPager.artists.items.get(i).name, artistsPager.artists.items.get(i).id, image));
            }

            mAdapter.notifyDataSetChanged();
        }
    }
}
