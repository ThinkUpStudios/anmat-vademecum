package com.thinkupstudios.anmat.vademecum.webservice.contract;

/**
 * Created by dcamarro on 23/04/2015.
 */
public class AnmatData {

    public int contentSize;
    public String Content;

    public int getContentSize() {
        return contentSize;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public byte[] getContent() {
        return Content;
    }

    public void setContent(byte[] content) {
        Content = content;
    }
}
