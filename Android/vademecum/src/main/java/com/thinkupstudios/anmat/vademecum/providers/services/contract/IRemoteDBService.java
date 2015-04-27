package com.thinkupstudios.anmat.vademecum.providers.services.contract;

import com.thinkupstudios.anmat.vademecum.exceptions.UpdateNotPosibleException;

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
     * @return true si la version es la mas reciente
     */
    public boolean isUpToDate();

    /**
     * Actualiza la Base de datos
      * @return File de base de datos
     */
    public boolean updateDatabase() throws UpdateNotPosibleException;

    void closeHelper();
}
