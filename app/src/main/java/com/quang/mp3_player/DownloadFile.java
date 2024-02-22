package com.quang.mp3_player;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile extends AsyncTask<String, Integer, String> {


    private AsyncTaskCompleteListener<File> callback;
    private DownloadProgressListener progressListener;
    File storageDir;

    public DownloadFile(AsyncTaskCompleteListener<File> fileAsyncTaskCompleteListener, DownloadProgressListener progressListener) {
        this.callback = fileAsyncTaskCompleteListener;
        this.progressListener = progressListener;
    }


    @Override
    protected String doInBackground(String... params) {
        String fileUrl = params[0];
        String fileName = params[1];
        InputStream input = null;
        FileOutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // Tạo một thư mục mới trong bộ nhớ ngoài của thiết bị (nếu không tồn tại)
            storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "MyMusic");
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    return null;
                }
            }
            // Tạo tệp MP3 mới trong thư mục đã tạo
            File outputFile = new File(storageDir, fileName);

            // Tạo một luồng đầu vào
            input = new BufferedInputStream(url.openStream());

            // Tạo một luồng đầu ra
            output = new FileOutputStream(outputFile);

            byte data[] = new byte[1024];
            int count;

            int fileLength = connection.getContentLength();
            int total = 0;

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
                total += count;
                int progress = (int) ((total * 100) / fileLength);
                publishProgress(progress);
            }
        } catch (Exception e) {

            return null;
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }
            if (connection != null)
                connection.disconnect();
        }
        return fileName;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            callback.onTaskComplete(storageDir);
        } else {

            // Xử lý lỗi khi tải xuống file không thành công
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (callback != null) {
            progressListener.onProgressUpdate(values[0]);
        }
    }
}


interface AsyncTaskCompleteListener<File> {
    void onTaskComplete(File url);
}
interface DownloadProgressListener {
    void onProgressUpdate(int progress);
}