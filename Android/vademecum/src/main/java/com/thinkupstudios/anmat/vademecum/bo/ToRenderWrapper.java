package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;

/**
 * Created by fcostazini on 21/10/2015.
 * Wrapper para compatibilidad con versiones viejas de android
 */
public class ToRenderWrapper implements Serializable{
    PairHeadDetail[] toRender;

    public ToRenderWrapper(PairHeadDetail[] toRender) {
        this.toRender = toRender;
    }

    public PairHeadDetail[] getToRender() {
        return toRender;
    }

    public void setToRender(PairHeadDetail[] toRender) {
        this.toRender = toRender;
    }
}
