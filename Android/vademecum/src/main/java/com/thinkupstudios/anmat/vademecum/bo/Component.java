package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;
import java.text.Normalizer;

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

        return removerAcentos(activeComponent).trim().equals(removerAcentos(component.getActiveComponent()).trim());
    }

    @Override
    public int hashCode() {
        return activeComponent.hashCode();
    }

    private String removerAcentos(String s) {

        String conAcentos = "áéíóú";
        String sinAcentos = "aeiou";

        for (int i = 0; i < conAcentos.length(); i++)
            s = s.toLowerCase().replace(conAcentos.charAt(i), sinAcentos.charAt(i));
        return s;
    }
}
