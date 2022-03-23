package com.gtech.payfast.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gtech.payfast.Model.Config.Fare;
import com.gtech.payfast.Model.Config.Station;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "METRO";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STATIONS_TABLE = " CREATE TABLE `stations` (" +
                "  `id` INTEGER PRIMARY KEY," +
                "  `station_name` TEXT NOT NULL," +
                "  `station_code` TEXT NOT NULL" +
                ")";

        String CREATE_FARE_TABLE = "CREATE TABLE `fares` (" +
                "  `id` INTEGER PRIMARY KEY," +
                "  `fare_for` INTEGER NOT NULL," +
                "  `source` TEXT NOT NULL," +
                "  `destination` TEXT NOT NULL," +
                "  `fare` REAL NOT NULL" +
                ")";

        db.execSQL(CREATE_STATIONS_TABLE);
        db.execSQL(CREATE_FARE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS stations");
            db.execSQL("DROP TABLE IF EXISTS fares");
            onCreate(db);
        }
    }

    // INSERT INTO STATIONS
    public void insertIntoStations(List<Station> stations) {

        SQLiteDatabase db = getWritableDatabase();

        for (Station station : stations) {

            ContentValues values = new ContentValues();
            values.put("id", station.getId());
            values.put("station_name", station.getStn_name());
            values.put("station_code", station.getStn_code());

            try {
                db.insertOrThrow("stations", null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.d("INSERTING STATIONS", "Error while trying to add post to database");
            }

        }

    }

    // INSERT INTO FARES
    public void insertIntoFares(List<Fare> fares) {

        SQLiteDatabase db = getWritableDatabase();

        for (Fare fare : fares) {

            ContentValues values = new ContentValues();
//            values.put("id", fare.getId());
//            values.put("fare_for", fare.getFare_for());
//            values.put("source", fare.getSource());
//            values.put("destination", fare.getDestination());
            values.put("fare", fare.getFare());

            try {
                db.insertOrThrow("fares", null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.d("INSERTING FARES", "Error while trying to add post to database");
            }

        }

    }

    // GET FARE
    public String getFare(String source, String destination, String fare_for) {

        SQLiteDatabase db = this.getReadableDatabase();
        String getFareQuery = "SELECT fare FROM fares WHERE source = ? AND destination = ? AND fare_for = ?";
        @SuppressLint("Recycle")
        Cursor getFareCursor = db.rawQuery(getFareQuery, new String[]{source, destination, fare_for});

        if (getFareCursor.moveToFirst()) return getFareCursor.getString(0);
        return "0";
    }

    // GET STATION ID
    public String getStationId(String station) {

        SQLiteDatabase db = this.getReadableDatabase();
        String getStationIdQuery = "SELECT id FROM stations WHERE station_name = ?";
        @SuppressLint("Recycle")
        Cursor getStationIdCursor = db.rawQuery(getStationIdQuery, new String[]{station});

        if (getStationIdCursor.moveToFirst()) return getStationIdCursor.getString(0);
        return "0";
    }

    // GET STATION LIST
    public ArrayList<String> getStations() {

        ArrayList<String> stations = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String getStationQuery = "SELECT station_name FROM stations";
        @SuppressLint("Recycle")
        Cursor getStationCursor = db.rawQuery(getStationQuery, null);

        if (getStationCursor.moveToFirst())
            do stations.add(getStationCursor.getString(0)); while (getStationCursor.moveToNext());
        return stations;
    }

    // GET STATION NAME
    public String getStationName(String stationId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String getStationNameQuery = "SELECT station_name FROM stations WHERE id = ?";
        @SuppressLint("Recycle")
        Cursor getStationNameCursor = db.rawQuery(getStationNameQuery, new String[]{stationId});

        if (getStationNameCursor.moveToFirst()) {
            Log.e("NAME", getStationNameCursor.getString(0));
            return getStationNameCursor.getString(0);
        }
        return "0";
    }

}
