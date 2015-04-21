package com.thinkupstudios.anmat.vademecum.providers;

import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by FaQ on 20/04/2015.
 * IMplementacion para SQLite remoto
 */
public class SQLiteDBService implements IRemoteDBService{
    @Override
    public boolean isUpToDate(Integer version) {
        return true;
    }

    @Override
    public File getNewDataBase() {
         return null;
    }
}
