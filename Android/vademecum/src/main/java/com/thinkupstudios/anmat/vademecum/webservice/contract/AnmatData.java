package com.thinkupstudios.anmat.vademecum.webservice.contract;

/**
 * Created by dcamarro on 23/04/2015.
 */
public class AnmatData {

    public int ContentSize;
    public byte[] Content;

    public int getContentSize() {
        return ContentSize;
    }

    public void setContentSize(int contentSize) {
        ContentSize = contentSize;
    }

    public byte[] getContent() {
        return Content;
    }

    public void setContent(byte[] content) {
        Content = content;
    }
}
