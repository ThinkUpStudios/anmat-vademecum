package com.thinkupstudios.anmat.vademecum.webservice.contract;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dcamarro on 23/04/2015.
 */
public class AnmatData {

    @SerializedName("Content")
    public String content;

    @SerializedName("ContentSize")
    public int contentSize;


    public int getContentSize() {
        return contentSize;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        content = content;
    }
}
