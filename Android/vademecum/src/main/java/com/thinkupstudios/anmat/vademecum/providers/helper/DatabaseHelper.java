package com.thinkupstudios.anmat.vademecum.providers.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.thinkupstudios.anmat.vademecum.providers.SQLiteDBService;
import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.

    private final static String DB_NAME = "anmat.sqlite";
    private final static int DB_VERSION = 15;

    private IRemoteDBService dbService;
    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context contexto
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
        this.dbService = new SQLiteDBService();
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            this.getReadableDatabase();
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }


    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String path = getPath();
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null;
    }

    private String getPath() {
        return myContext.getDatabasePath(DB_NAME).getPath();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {


        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = getPath();

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = getPath();
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        myContext.deleteDatabase(DB_NAME);

            File actual = new File(getPath());
            actual.renameTo(new File(getPath() + "_temp"));
        try {
            InputStream myInput = new FileInputStream(db.getPath());
            // Path to the just created empty db
            String outFileName = getPath();

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
            this.getReadableDatabase().getVersion();
        } catch (Exception e) {
            actual.renameTo(new File(getPath()));
            throw new Error("Error upgrading database");

        }
    }

    public void upgrade(File update) throws IOException {

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(update, null);

       File actualDB =  new File(myContext.getDatabasePath(DB_NAME).getPath());
        File backUp = this.bakupFile(actualDB);

        InputStream in = new FileInputStream(update);
        OutputStream out = new FileOutputStream(actualDB,false);
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }catch (IOException e){
            in.close();
            out.close();
            backUp.renameTo( new File(myContext.getDatabasePath(DB_NAME).getPath()));
            throw new IOException();
        }
            this.createDataBase();
            backUp.delete();

    }

    private File bakupFile(File actualDB) throws IOException {
        InputStream in = new FileInputStream(actualDB);
        File backUpFile = new File(myContext.getCacheDir()+ "temp_anmat");
        OutputStream out = new FileOutputStream(backUpFile);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        return backUpFile;
    }

}