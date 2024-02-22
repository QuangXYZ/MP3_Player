package com.quang.mp3_player.Model;

import com.quang.mp3_player.Model.Song;

import java.util.ArrayList;
import java.util.List;

public class Album {
    public String name;
    public List<Song> songs = new ArrayList<Song>();

    public Album(String name) {
        this.name = name;

    }

    public void addSong(Song song) {
        songs.add(song);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
