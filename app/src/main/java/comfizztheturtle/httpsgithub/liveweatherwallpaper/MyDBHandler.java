package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = " weatherDB.db ";
    public static final String TABLE_NAME = "weather_db";
    public static final String COLUMN_ID = "id_weather_type";
    public static final String COLUMN_NAME = "link_to_file";
    //initialize the database

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = " CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT " + ")";
        db.execSQL(CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String loadHandler() {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addHandler(image_class weather_type) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, weather_type.get_ID());
        values.put(COLUMN_NAME, weather_type.get_link_to_file());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public image_class findHandler(String ltf) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_NAME + " = " + "'" + ltf + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        image_class weather_result = new image_class();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            weather_result.set_ID(Integer.parseInt(cursor.getString(0)));
            weather_result.set_link_to_file(cursor.getString(1));
            cursor.close();
        } else {
            weather_result = null;
        }
        db.close();
        return weather_result;
    }

    public image_class find_ID(int ID) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = " + "'" + ID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        image_class weather_result = new image_class();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            weather_result.set_ID(Integer.parseInt(cursor.getString(0)));
            weather_result.set_link_to_file(cursor.getString(1));
            cursor.close();
        } else {
            weather_result = null;
        }
        db.close();
        return weather_result;
    }

    public boolean deleteHandler(int ID) {

        boolean result = false;
        String query = " Select * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        image_class weather_id = new image_class();
        if (cursor.moveToFirst()) {
            weather_id.set_ID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + " = ? ",
                    new String[] {
                String.valueOf(weather_id.get_ID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateHandler(int ID, String ltf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, ltf);
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + ID, null) > 0;
    }

    public void update_ltf(int ID, String ltf){

        String query = " UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME + " = '" + String.valueOf(ltf) + "' WHERE " + COLUMN_ID + " = " +ID;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);

        db.close();

    }

}