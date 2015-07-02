package com.kougasoft.spotifyclone;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PlayerFragment extends Fragment {
    ImageButton ibPrev;
    ImageButton ibPlay;
    ImageButton ibNext;
    SeekBar sbSscrubBar;
    boolean playerIsPrepared = false;
    MediaPlayer mediaPlayer = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);

        TextView tvArtistName = (TextView) rootView.findViewById(R.id.artistName);
        TextView tvAlbumName = (TextView) rootView.findViewById(R.id.albumName);
        TextView tvTrackName = (TextView) rootView.findViewById(R.id.trackName);
        ImageView ivAlbumArt = (ImageView) rootView.findViewById(R.id.albumArt);
        sbSscrubBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        ibPrev = (ImageButton) rootView.findViewById(R.id.previousButton);
        ibPlay = (ImageButton) rootView.findViewById(R.id.playButton);
        ibNext = (ImageButton) rootView.findViewById(R.id.nextButton);

        ibPrev.setClickable(false);
        ibPlay.setClickable(false);
        ibNext.setClickable(false);
        sbSscrubBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tvArtistName.setText(getArguments().getString(Top10ListFragment.ARTIST_NAME));
        tvAlbumName.setText(getArguments().getString(Top10ListFragment.ALBUM_NAME));
        tvTrackName.setText(getArguments().getString(Top10ListFragment.TRACK_NAME));
        String albumArtURL = getArguments().getString(Top10ListFragment.ALBUM_ART_URL);
        if(albumArtURL != "")
            Picasso.with(getActivity()).load(albumArtURL).into(ivAlbumArt);
        else
            Picasso.with(getActivity()).load(R.mipmap.ic_launcher).into(ivAlbumArt);

        mediaPlayer = getMediaPlayer(getArguments().getString(Top10ListFragment.PREVIEW_URL));

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            playerIsPrepared = false;
            ibPlay.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mediaPlayer == null)
            mediaPlayer = getMediaPlayer(getArguments().getString(Top10ListFragment.PREVIEW_URL));
    }

    public MediaPlayer getMediaPlayer(String previewUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        playerIsPrepared = false;
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(previewUrl);
            mediaPlayer.setOnPreparedListener(new onPreparedListener());
            mediaPlayer.prepareAsync();
        }
        catch (IOException ex) {
            Toast.makeText(getActivity(), "Error retrieving track preview!", Toast.LENGTH_LONG);
            return null;
        }

        return mediaPlayer;
    }

    public class onPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            playerIsPrepared = true;
            ibPrev.setClickable(true);
            ibPlay.setClickable(true);
            ibNext.setClickable(true);

            ibPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mediaPlayer.isPlaying() && playerIsPrepared) {
                        mediaPlayer.start();
                        ibPlay.setImageResource(android.R.drawable.ic_media_pause);
                    } else {
                        mediaPlayer.pause();
                        ibPlay.setImageResource(android.R.drawable.ic_media_play);
                    }
                }
            });

            ibPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
