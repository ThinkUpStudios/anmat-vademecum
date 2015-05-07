package com.thinkupstudios.anmat.vademecum.bo.comparadores;

import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;

import java.util.Comparator;

/**
 * Created by fcostazini on 06/05/2015.
 */

public class ComparadorPrecios implements Comparator<MedicamentoBO>{
    @Override
    public int compare(MedicamentoBO lhs, MedicamentoBO rhs) {

      if(this.getPesoOrden(lhs).equals(this.getPesoOrden(rhs))){
          if(this.getPesoOrden(lhs).equals(0) ){
              return Float.valueOf(lhs.getPrecio().replace("$","")).compareTo(Float.valueOf(rhs.getPrecio().replace("$", "")));
          }
          return lhs.getNombreGenerico().compareTo(rhs.getNombreGenerico());
      }else{
          return this.getPesoOrden(lhs).compareTo(this.getPesoOrden(rhs));
      }


    }

    private Integer getPesoOrden(MedicamentoBO m){
        if (m.esRemediar()) {
            return -1;

        } else if (m.getPrecio().equals(MedicamentoBO.SIN_PRECIO)) {
            return 10;
        } else if (m.getPrecio().equals(MedicamentoBO.UH)) {
            return 5;
        } else {
            return 0;

        }
    }
}
