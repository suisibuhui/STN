package com.example.stn.stn.utils.download;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * asyncTask异步下载类
 * created by xulong
 */

public class FileDownloadTask extends AsyncTask<Void, Long, Boolean> {

    private OkHttpClient okHttpClient;
    private FileDownloadCallback callback;
    private String url;
    private File targetFile;

    public FileDownloadTask(String url, File target, FileDownloadCallback callback) {
        this.url = url;
        this.okHttpClient = OkHttpManager.getInstance().getDefaultOkHttpClient();
        this.callback = callback;
        this.targetFile = target;

        FileUtils.mkdirs(target.getParentFile());
        if (target.exists()) {
            target.delete();
        }
    }

    //ui
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback != null) {
            callback.onStart();
        }
    }

    //非ui
    @Override
    protected Boolean doInBackground(Void... params) {

        final Request request = new Request.Builder()
                .url(url)
                .build();

        boolean suc = false;
        try {
            Response response = okHttpClient.newCall(request).execute();
            long total = response.body().contentLength();
            saveFile(response);
            if (total == targetFile.length()) {
                suc = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            suc = false;
        }

        return suc;
    }


    //ui
    @Override
    protected void onPostExecute(Boolean suc) {
        super.onPostExecute(suc);
        if (callback != null) {
            if (suc) {
                callback.onDone();
            } else {
                callback.onFailure();
            }
        }
    }

    public String saveFile(Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            FileUtils.mkdirs(targetFile.getParentFile());

            fos = new FileOutputStream(targetFile);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                if (callback != null) {
                    publishProgress(sum, total);
                }
            }
            fos.flush();

            return targetFile.getAbsolutePath();
        } finally {
            try {
                if (is != null) { is.close(); }
            } catch (IOException e) {
            }
            try {
                if (fos != null) { fos.close(); }
            } catch (IOException e) {
            }
        }
    }



}
