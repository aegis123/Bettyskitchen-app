package com.bettys.kitchen.recipes.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bettys.kitchen.recipes.app.models.Channel;
import com.bettys.kitchen.recipes.app.models.Item;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.convert.DefaultConverterFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Dylan on 30-9-13.
 */
public class CupboardSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bettyskitchen.db";
    private static final int DATABASE_VERSION = 1;

    static {
        CupboardFactory.setCupboard(new Cupboard(new DefaultConverterFactory(true)));
        // Register our models.
        cupboard().register(Item.class);
        cupboard().register(Channel.class);
    }

    public CupboardSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (true) {
            db.execSQL("DROP TABLE IF EXISTS Item;");
            db.execSQL("DROP TABLE IF EXISTS Channel;");
            cupboard().withDatabase(db).createTables();
            return;
        }
        // This will upgrade tables, adding columns and new tables.
        // Note that existing columns will NOT be converted to new data types.
        cupboard().withDatabase(db).upgradeTables();
        // Do any migration work using the SQLiteDatabase object.
    }
}
