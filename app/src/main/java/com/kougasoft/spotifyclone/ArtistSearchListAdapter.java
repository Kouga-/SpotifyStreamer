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

public class ArtistSearchListAdapter extends BaseAdapter {
    private List<Artist> mArtists = new ArrayList<>();

    public ArtistSearchListAdapter(List<Artist> Artists) {
        mArtists = Artists;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    @Override
    public int getCount() {
        return mArtists.size();
    }

    @Override
    public Object getItem(int position) {
        return mArtists.get(position);
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
            v = inflater.inflate(R.layout.list_item_artist, null);

            holder = new ViewHolder();
            holder.txtTitle = (TextView) v.findViewById(R.id.artistName);
            holder.imageView = (ImageView) v.findViewById(R.id.artistImage);
            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        holder.txtTitle.setText(mArtists.get(position).getName());
        if(mArtists.get(position).getImageURL() == "")
            Picasso.with(parent.getContext()).load(R.mipmap.ic_launcher).into(holder.imageView);
        else
            Picasso.with(parent.getContext()).load(mArtists.get(position).getImageURL()).into(holder.imageView);

        return v;
    }
}
