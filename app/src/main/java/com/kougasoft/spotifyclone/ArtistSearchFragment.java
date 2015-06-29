package com.kougasoft.spotifyclone;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;

public class ArtistSearchFragment extends Fragment {
    List<MyArtist> mArtistList = new ArrayList<>();
    MyAdapter mAdapter;

    public ArtistSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.lvArtists);
        final EditText searchField = (EditText) rootView.findViewById(R.id.etSearchTerm);

        searchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                    if(keyCode == KeyEvent.KEYCODE_ENTER) {
                        InputMethodManager imm =
                                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
                        new FetchArtistsTask().execute(searchField.getText().toString());
                        return true;
                    }
                return false;
            }
        });

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
            if(artistsPager.artists.items.size() == 0)
                Toast.makeText(getActivity(), "No matches found!", Toast.LENGTH_LONG).show();
            mArtistList.clear();
            for (int i = 0; i < artistsPager.artists.items.size(); i++) {
                String image = artistsPager.artists.items.get(i).images.isEmpty() ? "" : artistsPager.artists.items.get(i).images.get(0).url;
                mArtistList.add(new MyArtist(artistsPager.artists.items.get(i).name, artistsPager.artists.items.get(i).id, image));
            }

            mAdapter.notifyDataSetChanged();
        }
    }
}
