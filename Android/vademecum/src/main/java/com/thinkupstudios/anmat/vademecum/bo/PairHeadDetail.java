package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;

/**
 * Created by Facundo on 19/10/2015.
 * Par cabecera detalle
 */
public class PairHeadDetail implements Serializable{

    private String header;
    private String detail;

    public PairHeadDetail(String header, String detail) {
        this.header = header;
        this.detail = detail;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
