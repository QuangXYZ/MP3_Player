package com.quang.mp3_player;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.quang.mp3_player.Model.Song;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder>{
    List<Song> songs;
    Activity context;
    private MediaPlayer mediaPlayer;
    private Boolean isPlaying = false;


    public SongAdapter(List<Song> songs, Activity context) {
        this.songs = songs;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_song,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.songName.setText(song.name);
        if (song.isDownload) {
            holder.download.setVisibility(View.GONE);
            holder.play.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isPlaying) {
                        mediaPlayer.pause();
                        holder.play.setIconResource(R.drawable.baseline_play_arrow_24);
                        isPlaying = false;
                    }
                    else {
                        File musicFile = new File(song.storedLink+"/"+song.name+".mp3");
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
                            holder.play.setIconResource(R.drawable.baseline_stop_24);
                            isPlaying = true;
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }

                }
            });
        }
        else {

            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.downloadProgress.setVisibility(View.VISIBLE);
                    new DownloadFile(new AsyncTaskCompleteListener<File>() {
                        @Override
                        public void onTaskComplete(File url) {
                            song.isDownload = true;
                            song.storedLink = url.toString();
                            notifyDataSetChanged();
                            holder.play.setEnabled(true);
                            holder.download.setVisibility(View.GONE);
                            holder.downloadProgress.setVisibility(View.GONE);
                        }
                    }, new DownloadProgressListener() {
                        @Override
                        public void onProgressUpdate(int progress) {
                            holder.downloadProgress.setText("Downloading " + progress + "%...");
                            Log.e("Progress", progress+"...");
                        }
                    }).execute(song.downloadLink, song.name + ".mp3");

                }
            });
            holder.play.setEnabled(false);
        }



    }
    public void deleteItem(int index) {
        songs.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView songName,downloadProgress;
        MaterialButton download, play;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.single_song_name_text);
            download = itemView.findViewById(R.id.single_song_download_btn);
            play = itemView.findViewById(R.id.single_song_play_btn);
            downloadProgress = itemView.findViewById(R.id.single_song_download_progress_txt);

        }

    }

}
