package com.kougasoft.spotifyclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Top10ListAdapter extends BaseAdapter {
    private List<Track> mTracks  = new ArrayList<>();

    public Top10ListAdapter(List<Track> tracks) {
        mTracks = tracks;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTrackName;
        TextView txtAlbumName;
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return mTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;

        if(v == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_track, null);

            holder = new ViewHolder();
            holder.txtTrackName = (TextView) v.findViewById(R.id.trackName);
            holder.txtAlbumName = (TextView) v.findViewById(R.id.albumName);
            holder.imageView = (ImageView) v.findViewById(R.id.trackImage);
            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        holder.txtTrackName.setText(mTracks.get(position).trackName);
        holder.txtAlbumName.setText(mTracks.get(position).albumName);
        if(mTracks.get(position).albumArtThumb == "")
            Picasso.with(parent.getContext()).load(R.mipmap.ic_launcher).into(holder.imageView);
        else
            Picasso.with(parent.getContext()).load(mTracks.get(position).albumArtThumb).into(holder.imageView);

        return v;
    }
}
