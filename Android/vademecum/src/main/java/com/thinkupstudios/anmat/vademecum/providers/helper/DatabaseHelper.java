package com.thinkupstudios.anmat.vademecum.providers.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.

    private final static String DB_NAME = "anmat.sqlite";
    private final static int DB_VERSION = 117;


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


    }

     /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase() {

        SQLiteDatabase checkDB;

        try {
            String path = getPath();
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        } catch (Exception e) {
            checkDB = null;
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
    public void copyDataBase() throws IOException {


        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName;

        if(android.os.Build.VERSION.SDK_INT >= 17) {
            outFileName = myContext.getApplicationInfo().dataDir + "/databases/";
        }
        else {
            outFileName = "/data/data/" + myContext.getPackageName() + "/databases/";
        }


        //Open the empty db as the output stream
        // create a File object for the parent directory

        File wallpaperDirectory = new File(outFileName);
        // have the object build the directory structure, if needed.
        wallpaperDirectory.mkdirs();
        // create a File object for the output file
        File outputFile = new File(wallpaperDirectory, DB_NAME);
        // now attach the OutputStream to the file object, instead of a String representation
        FileOutputStream fos = new FileOutputStream(outputFile);

        //OutputStream myOutput = new FileOutputStream(outFileName+DB_NAME);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }

        //Close the streams
        fos.flush();
        fos.close();
        myInput.close();

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

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void upgrade(byte[] in) throws IOException {

        File actualDB =  new File(myContext.getDatabasePath(DB_NAME).getPath());
        File backUp = this.bakupFile(actualDB);

        InputStream inputStream = new ByteArrayInputStream(in);
        OutputStream out = new FileOutputStream(actualDB,false);
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            this.getReadableDatabase().getVersion();
        }catch (IOException e){

            inputStream.close();
            out.close();
            backUp.renameTo( new File(myContext.getDatabasePath(DB_NAME).getPath()));
            throw new IOException();
        }
        finally {
            inputStream.close();
            out.close();
            out.flush();
            this.close();
        }
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


    public void createIfFirstRun() throws IOException {
        if(!this.checkDataBase()){
            this.copyDataBase();
        }
    }

}