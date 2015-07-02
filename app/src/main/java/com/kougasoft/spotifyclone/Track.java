package com.kougasoft.spotifyclone;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
    String artistName;
    String trackName;
    String albumName;
    String albumArtThumb;
    String previewURL;

    public Track(String artistName, String trackName, String albumName, String albumArtThumb, String previewURL) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.albumName = albumName;
        this.albumArtThumb = albumArtThumb;
        this.previewURL = previewURL;
    }

    public Track(Parcel parcel) {
        this.artistName = parcel.readString();
        this.trackName = parcel.readString();
        this.albumName = parcel.readString();
        this.albumArtThumb = parcel.readString();
        this.previewURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistName);
        dest.writeString(trackName);
        dest.writeString(albumName);
        dest.writeString(albumArtThumb);
        dest.writeString(previewURL);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Track[size];
        }
    };
}
