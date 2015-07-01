package com.kougasoft.spotifyclone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;

public class Top10ListFragment extends Fragment {
    List<Track> mTrackList = new ArrayList<>();
    Top10ListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top10_tracks, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.lvTop10);

        String spotifyID = getArguments().getString(ArtistSearchFragment.SPOTIFY_ID);
        String artistName = getArguments().getString(ArtistSearchFragment.ARTIST_NAME);

        if(spotifyID != null) {
            new FetchTop10Task().execute(spotifyID, artistName);
        }

        mAdapter = new Top10ListAdapter(mTrackList);
        lv.setAdapter(mAdapter);

        return rootView;
    }

    public class FetchTop10Task extends AsyncTask<String, Void, Tracks> {

        @Override
        protected Tracks doInBackground(String... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();

            Map<String, Object> options = new Hashtable<>();
            options.put("country", "CA");

            return spotify.getArtistTopTrack(params[0], options);
        }

        @Override
        protected void onPostExecute(Tracks tracksPager) {
            super.onPostExecute(tracksPager);
            if(tracksPager == null)
                return;
            if(tracksPager.tracks.size() == 0)
                Toast.makeText(getActivity(), "No tracks found!", Toast.LENGTH_LONG).show();
            mTrackList.clear();
            for (int i = 0; i < tracksPager.tracks.size(); i++) {
                String image = tracksPager.tracks.get(i).album.images.isEmpty() ? "" : tracksPager.tracks.get(i).album.images.get(1).url;
                mTrackList.add(new Track(tracksPager.tracks.get(i).name, tracksPager.tracks.get(i).album.name, image, tracksPager.tracks.get(i).preview_url));
            }

            mAdapter.notifyDataSetChanged();
        }
    }
}
