package com.example.egeudareniafinal;

public class FinishItem {
    private int mImageResource;
    private String mTitle;
    private String mDescription;
    private boolean correct;

    public FinishItem(int mImageResource, String mTitle, String mDescription, boolean correct) {
        this.mImageResource = mImageResource;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.correct = correct;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public boolean isCorrect() {
        return correct;
    }
}