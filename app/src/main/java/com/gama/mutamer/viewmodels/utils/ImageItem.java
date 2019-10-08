package com.gama.mutamer.viewModels.utils;

import android.graphics.Bitmap;

public class ImageItem implements Comparable {

    private Bitmap bitmap;
    private String filePath;

    private boolean isVideo;
    private long lastModified;

    public ImageItem(Bitmap bitmap, String filePath, boolean isVideo, long lastModified) {
        this.bitmap = bitmap;
        this.filePath = filePath;
        this.isVideo = isVideo;
        this.lastModified = lastModified;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setIsVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public int compareTo(Object other) {
        if (other == null || !(other instanceof ImageItem))
            return -1;

        return ((ImageItem) other).getLastModified() > getLastModified() ? 1 : -1;
    }
}
