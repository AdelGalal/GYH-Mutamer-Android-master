package com.gama.mutamer.viewModels.utils;

/**
 * Created by mustafa on 8/24/16.
 * Release the GEEK
 */
public class MediaItem {
    // private byte[] value;
    private String filePath;
    private boolean isVideo;
    private long lastModified;
    private String mName;
    private String mDir;

    public MediaItem(String filePath, boolean isVideo, long lastModified, String name, String dir) {
        //  this.value = value;
        this.filePath = filePath;
        this.isVideo = isVideo;
        this.lastModified = lastModified;
        setName(name);
        setDir(dir);
    }

    public String getDir() {
        return mDir;
    }

    public void setDir(String dir) {
        mDir = dir;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
//
//    public byte[] getValue() {
//        return value;
//    }
//
//    public void setValue(byte[] value) {
//        this.value = value;
//    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
