package com.kougasoft.spotifyclone;

public class Track {
    String trackName;
    String albumName;
    String albumArtThumb;
    String previewURL;

    public Track(String trackName, String albumName, String albumArtThumb, String previewURL) {
        this.trackName = trackName;
        this.albumName = albumName;
        this.albumArtThumb = albumArtThumb;
        this.previewURL = previewURL;
    }
}
