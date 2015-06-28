package com.kougasoft.spotifyclone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

public class MainFragment extends Fragment {
    ArrayList<String> mArtistList = new ArrayList<>();
    ArrayAdapter<String> mAdapter;

    public MainFragment() {
        new FetchArtistsTask().execute("Imagine");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.lvArtists);
        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_artist, R.id.artistName, mArtistList);
        lv.setAdapter(mAdapter);

        return rootView;
    }

    public class FetchArtistsTask extends AsyncTask<String, Void, ArtistsPager> {

        @Override
        protected ArtistsPager doInBackground(String... params) {
            SpotifyApi spotify = new SpotifyApi();
            SpotifyService service = spotify.getService();
            ArtistsPager artists = service.searchArtists(params[0]);

            return artists;
        }

        @Override
        protected void onPostExecute(ArtistsPager artistsPager) {
            super.onPostExecute(artistsPager);

            List<Artist> items = artistsPager.artists.items;
            ArrayList<String> artists = new ArrayList<>();

            for(int i = 0; i < items.size(); i++)
            {
                artists.add(artistsPager.artists.items.get(i).name);
            }
            mArtistList.clear();
            mArtistList.addAll(artists);

            mAdapter.notifyDataSetChanged();
        }
    }

}
