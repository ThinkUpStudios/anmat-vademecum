package com.thinkupstudios.anmat.vademecum.bo.comparadores;

import java.util.Comparator;

/**
 * Created by fcostazini on 11/05/2015.
 */
public class ComparadorStringAutocomplete implements Comparator<String>{
    private String prefix;



    public ComparadorStringAutocomplete(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public int compare(String lhs, String rhs) {
        if (lhs.startsWith(prefix.toUpperCase()) && !rhs.startsWith(prefix.toUpperCase())) {
            return -1;
        } else if (!lhs.startsWith(prefix.toUpperCase()) && rhs.startsWith(prefix.toUpperCase())) {
            return 1;
        } else {
            return lhs.compareTo(rhs);
        }
    }
}
