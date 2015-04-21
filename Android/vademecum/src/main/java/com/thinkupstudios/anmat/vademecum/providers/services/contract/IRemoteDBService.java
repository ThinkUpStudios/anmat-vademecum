package com.thinkupstudios.anmat.vademecum.providers.services.contract;

import java.io.File;

/**
 * Created by FaQ on 20/04/2015.
 *
 * Servicio que controla el versionado de la base de datos
 */
public interface IRemoteDBService {
    /**
     * Verifica si la versión pasada como parametro es la mas reciente
     *
     * @param version a verificar
     * @return true si la version es la mas reciente
     */
    public boolean isUpToDate(Integer version);

    /**
     * Obtiene el archivo de la base de datos mas reciente
      * @return File de base de datos
     */
    public File getNewDataBase();
}
