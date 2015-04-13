package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;

/**
 * Created by dcamarro on 11/04/2015.
 *
 * Componente de formula
 */
public class Component implements Serializable {
    private String activeComponent;
    private String proportion;

    public Component() {
    }

    public Component(String activeComponent, String proportion) {
        this.setActiveComponent(activeComponent);
        this.setProportion(proportion);
    }

    public String getActiveComponent() {
        return this.activeComponent;
    }

    public void setActiveComponent(String activeComponent) {
        this.activeComponent = activeComponent;
    }

    public String getProportion() {
        return this.proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        return activeComponent.equals(component.activeComponent);
    }

    @Override
    public int hashCode() {
        return activeComponent.hashCode();
    }
}
