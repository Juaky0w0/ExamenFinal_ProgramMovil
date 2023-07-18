package xyz.android.finalcalderon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class HolidayDatabase {
    private static final String DATABASE_NAME = "holiday_database";
    private static final String TABLE_NAME = "holiday";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNTRY = "country";

    private DatabaseHelper databaseHelper;

    public HolidayDatabase(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertHoliday(Holiday holiday) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, holiday.getDate());
        values.put(COLUMN_NAME, holiday.getName());
        values.put(COLUMN_COUNTRY, holiday.getCountry());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteAllHolidays() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public List<Holiday> getAllHolidays() {
        List<Holiday> holidayList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
            Holiday holiday = new Holiday(date, name, country);
            holidayList.add(holiday);
        }

        cursor.close();
        db.close();

        return holidayList;
    }
}
