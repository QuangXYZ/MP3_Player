package com.quang.mp3_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.quang.mp3_player.Model.Album;
import com.quang.mp3_player.Model.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_album;
    SongAdapter songAdapter;
    RecyclerView recyclerView;
    List<Album> albums;
    ArrayList<Song> AdapterSongs;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        settingsUpListener();

    }
    void init() {
        spinner_album = findViewById(R.id.spinner_album);
        recyclerView = findViewById(R.id.recycle_view);

        Album chineseAlbum = new Album("Trung Quốc");
        Album vietnameseAlbum = new Album("Việt Nam");
        Album japaneseAlbum = new Album("Nhật bản");

        chineseAlbum.addSong(new Song("Bất vấn biệt ly","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/x2mate.com%20-%20%5BVietsub%20%2B%20Pinyin%5D%20B%E1%BA%A5t%20V%E1%BA%A5n%20Bi%E1%BB%87t%20Ly%20-%20Ch%E1%BB%89%20Ti%C3%AAm%20Ti%E1%BA%BFu%20_%20%E6%8C%87%E5%B0%96%E7%AC%91%20-%20%E4%B8%8D%E9%97%AE%E5%88%AB%E7%A6%BB%20(320%20kbps).mp3?alt=media&token=46f1784d-9ef3-4e59-9f14-e962ef80e687"));
        chineseAlbum.addSong(new Song("Văn phong tác tửu","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20%E2%99%AC%20PH%E1%BA%ACN%20DUY%C3%8AN%20L%E1%BB%A0%20L%C3%80NG%20-%20PH%C3%81T%20HUY%20T4%20X%20TRUZG%20_%20OFFICIAL%20MUSIC%20VIDEO%20(128%20kbps).mp3?alt=media&token=05946644-3846-475d-891c-0b6ed6ae6f23"));
        chineseAlbum.addSong(new Song("Quan sơn tửu","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20Quan%20S%C6%A1n%20T%E1%BB%ADu%20-%20%C4%90%E1%BA%B3ng%20Th%E1%BA%ADp%20Y%C3%AAu%20Qu%C3%A2n%20__%20%E5%85%B3%E5%B1%B1%E9%85%92%20-%20%E7%AD%89%E4%BB%80%E4%B9%88%E5%90%9B%20(320%20kbps).mp3?alt=media&token=c1d7af41-be86-4be5-a608-1cb0429e151a"));
        chineseAlbum.addSong(new Song("Tinh vệ","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20G%C3%B3c%20Tr%E1%BB%9Di%20C%E1%BB%A7a%20N%C3%A0ng%20(320%20kbps).mp3?alt=media&token=96e1116c-9689-4186-aedf-6a627ae10636"));
        chineseAlbum.addSong(new Song("Hạ sơn","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20%5BVietsub%5D%20H%E1%BA%A1%20S%C6%A1n%20(%20Gi%E1%BB%8Dng%20n%E1%BB%AF%20)%20-%20Khanh%20Qu%C3%A2n%20_%20%E4%B8%8B%E5%B1%B1%20-%20%E5%8D%BF%E5%90%9B%20(128%20kbps).mp3?alt=media&token=6e0d2772-c650-4698-949e-c46db2b66e1c"));
        chineseAlbum.addSong(new Song("Váy cưới của em như những bông tuyết","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20%5BPinyin%20%2B%20Vietsub%5D%20V%C3%A1y%20C%C6%B0%E1%BB%9Bi%20C%E1%BB%A7a%20Em%20Gi%E1%BB%91ng%20Nh%C6%B0%20B%C3%B4ng%20Tuy%E1%BA%BFt%20-%20L%C3%BD%20Ph%C3%A1t%20Ph%C3%A1t%20(%E4%BD%A0%E7%9A%84%E5%A9%9A%E7%BA%B1%E5%83%8F%E9%9B%AA%E8%8A%B1%20-%20%E6%9D%8E%E5%8F%91%E5%8F%91)%20(128%20kbps)%20(1).mp3?alt=media&token=4a0b42bb-49aa-42c6-9652-d5cf6972abf3"));
        chineseAlbum.addSong(new Song("Không đợi được anh","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20%5BVietsub%5D%20Kh%C3%B4ng%20%C4%91%E1%BB%A3i%20%C4%91%C6%B0%E1%BB%A3c%20anh%20%E2%80%A2%20M%E1%BB%99t%20ch%C3%BA%20b%E1%BA%A1ch%20b%C6%B0%C6%A1ng%20%E2%99%AA%20%E7%AD%89%E4%B8%8D%E5%88%B0%E7%9A%84%E4%BD%A0%20%E2%80%A2%20%E4%B8%80%E5%8F%AA%E7%99%BD%E7%BE%8A%20(128%20kbps).mp3?alt=media&token=5ae1996b-ccf7-4eb4-84cc-b316f94982d6"));

        vietnameseAlbum.addSong(new Song("Phận duyên lỡ làng","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20%E2%99%AC%20PH%E1%BA%ACN%20DUY%C3%8AN%20L%E1%BB%A0%20L%C3%80NG%20-%20PH%C3%81T%20HUY%20T4%20X%20TRUZG%20_%20OFFICIAL%20MUSIC%20VIDEO%20(128%20kbps).mp3?alt=media&token=05946644-3846-475d-891c-0b6ed6ae6f23"));
        vietnameseAlbum.addSong(new Song("Góc trời của nàng","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20%5BVietsub%5D%20Kh%C3%B4ng%20%C4%91%E1%BB%A3i%20%C4%91%C6%B0%E1%BB%A3c%20anh%20%E2%80%A2%20M%E1%BB%99t%20ch%C3%BA%20b%E1%BA%A1ch%20b%C6%B0%C6%A1ng%20%E2%99%AA%20%E7%AD%89%E4%B8%8D%E5%88%B0%E7%9A%84%E4%BD%A0%20%E2%80%A2%20%E4%B8%80%E5%8F%AA%E7%99%BD%E7%BE%8A%20(128%20kbps).mp3?alt=media&token=5ae1996b-ccf7-4eb4-84cc-b316f94982d6"));
        vietnameseAlbum.addSong(new Song("Dáng em","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20D%C3%A1ng%20em%20-%20Th%E1%BA%BF%20Khoa%20_%20OpenShare%20Gone%20Live%20(128%20kbps).mp3?alt=media&token=3561849e-cf85-4595-8269-823365447632"));
        vietnameseAlbum.addSong(new Song("Tìm em trong mơ","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20T%C3%ACm%20Em%20Trong%20M%C6%A1%20-%20Chi%20D%C3%A2n%20_%20Official%20Music%20Video%20(128%20kbps).mp3?alt=media&token=a7091a2a-00c6-429c-9da5-4735b0d11b55"));
        vietnameseAlbum.addSong(new Song("Là anh","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20L%C3%80%20ANH%20-%20Cover%20Nh%E1%BA%A1c%20Ngo%E1%BA%A1i%20L%E1%BB%9Di%20Vi%E1%BB%87t%20by%20PH%E1%BA%A0M%20L%E1%BB%8ACH%20(%20It%E2%80%99s%20You%20-M%E1%BB%99ng%20Nhi%C3%AAn)%20(128%20kbps).mp3?alt=media&token=8185f4f9-71f0-4e28-a60f-eea2f26e07f5"));


        japaneseAlbum.addSong(new Song("×ファイトソング","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20%E3%83%95%E3%82%A1%E3%82%A4%E3%83%88%E3%82%BD%E3%83%B3%E3%82%B0%20(Fight%20Song)%20-%20Eve%20Music%20Video%20(128%20kbps).mp3?alt=media&token=b6baec7b-4f0d-43ea-9503-c57b7f0b2c61"));
        japaneseAlbum.addSong(new Song("Cyperpunk","https://firebasestorage.googleapis.com/v0/b/academic-actor-360810.appspot.com/o/Y2meta.app%20-%20Cyberpunk_%20Edgerunners%20%E2%80%94%20Ending%20Theme%20_%20Let%20You%20Down%20by%20Dawid%20Podsiad%C5%82o%20_%20Netflix%20(128%20kbps).mp3?alt=media&token=6ddf5f47-2247-4c64-8b4a-4d1f99c47c10"));

        albums = new ArrayList<>();
        albums.add(chineseAlbum);
        albums.add(vietnameseAlbum);
        albums.add(japaneseAlbum);

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < albums.size(); i++) {
            names.add(albums.get(i).getName());
        }

        //Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_album.setAdapter(adapter);

        //RecyclerView setup
        AdapterSongs = new ArrayList<>();
        songAdapter = new SongAdapter(AdapterSongs,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(songAdapter);
        recyclerView.setNestedScrollingEnabled(false);



    }
    void settingsUpListener(){
        spinner_album.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Album album = albums.get(position);
                AdapterSongs.clear();
                AdapterSongs.addAll(album.getSongs());
                songAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void playMusic() {
        File musicFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "MyMusic/vaycuoi.mp3");

        // Kiểm tra xem tệp âm thanh có tồn tại không
        if (!musicFile.exists()) {

            return;
        }

        // Tạo một MediaPlayer mới và cài đặt nguồn dữ liệu âm thanh
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(musicFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}