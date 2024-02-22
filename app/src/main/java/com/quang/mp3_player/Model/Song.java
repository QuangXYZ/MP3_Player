package com.quang.mp3_player.Model;

public class Song {
    public String name;
    public String downloadLink;
    public Boolean isDownload;
    public String storedLink;

    public Song(String name, String downloadLink) {
        this.name = name;
        this.downloadLink = downloadLink;
        isDownload = false;
        storedLink = "";
    }

    public String getStoredLink() {
        return storedLink;
    }

    public void setStoredLink(String storedLink) {
        this.storedLink = storedLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Boolean getDownload() {
        return isDownload;
    }

    public void setDownload(Boolean download) {
        isDownload = download;
    }
}
