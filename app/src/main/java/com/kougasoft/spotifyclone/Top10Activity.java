package com.kougasoft.spotifyclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Top10Activity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_top10);
        getSupportActionBar().setSubtitle(getIntent().getExtras().getString(ArtistSearchFragment.ARTIST_NAME));
        if (savedInstanceState == null) {
            Top10ListFragment fragment = new Top10ListFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
