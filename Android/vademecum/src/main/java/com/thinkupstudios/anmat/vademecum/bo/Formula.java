package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcamarro on 11/04/2015.
 */
public class Formula implements Serializable{

    private List<Component> components;

    public Formula()
    {
        this.components = new ArrayList<>();
    }

    public List<Component> getComponents() {
        return this.components;
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }
}
