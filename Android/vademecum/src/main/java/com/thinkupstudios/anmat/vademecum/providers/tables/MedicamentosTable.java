package com.thinkupstudios.anmat.vademecum.providers.tables;

/**
 * Created by Dario on 24/02/2015.
 */
public class MedicamentosTable {

    public static final String[] COLUMN_GENERICO = {"generico"};
    public static final String[] COLUMN_COMERCIAL = {"comercial"};
    public static final String[] COLUMN_LABORATORIO = {"laboratorio"};

    public static String TABLE_NAME = "medicines";
    public static String[] COLUMNS =   {"id",
                                        "certificado",
                                        "cuit",
                                        "laboratorio",
                                        "gtin",
                                        "troquel",
                                        "comercial",
                                        "forma",
                                        "generico",
                                        "pais",
                                        "expendio",
                                        "trazabilidad",
                                        "presentacion",
                                        "precio"};


}
