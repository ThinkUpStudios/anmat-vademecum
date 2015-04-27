package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.VersionBo;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.VersionTable;

/**
 * Created by dcamarro on 23/04/2015.
 */
public class VersionProvider extends GenericProvider{

    public VersionProvider(DatabaseHelper db) {
        super(db);
    }

    public VersionBo getVersionBo(){
        Cursor cursor = null;
        VersionBo versionBo = null;
        try{
            cursor = helper.getReadableDatabase().query(VersionTable.TABLE_NAME, VersionTable.COLUMNS, null, null, null, null, null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                versionBo = cursorToVersionBo(cursor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(cursor!= null) cursor.close();
        }
        return versionBo;
    }

    private VersionBo cursorToVersionBo(Cursor cursor) {
        VersionBo version = new VersionBo(cursor.getInt(cursor.getColumnIndex(VersionTable.COLUMNS[0])),
                cursor.getString(cursor.getColumnIndex(VersionTable.COLUMNS[1])) );
        return version;
    }
}
