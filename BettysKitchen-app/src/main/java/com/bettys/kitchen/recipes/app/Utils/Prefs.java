package com.bettys.kitchen.recipes.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bettys.kitchen.recipes.app.RecipeApplication;

public class Prefs {
    private static final Object PREFS_LOCK = new Object();

    private static SharedPreferences getPrefs() {
        synchronized (PREFS_LOCK) {
            return RecipeApplication.getContext().getSharedPreferences(RecipeApplication.class.getSimpleName(),
                    Context.MODE_PRIVATE);
        }
    }

    public static boolean getBoolean(String key, boolean def) {
        synchronized (PREFS_LOCK) {
            SharedPreferences prefs = getPrefs();
            return prefs.getBoolean(key, def);
        }
    }

    public static boolean putBoolean(String key, boolean value) {
        synchronized (PREFS_LOCK) {
            SharedPreferences.Editor editor = getPrefs().edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
    }

    public static long getLong(String key, long def) {
        synchronized (PREFS_LOCK) {
            SharedPreferences prefs = getPrefs();
            return prefs.getLong(key, def);
        }
    }

    public static boolean putLong(String key, long value) {
        synchronized (PREFS_LOCK) {
            SharedPreferences.Editor editor = getPrefs().edit();
            editor.putLong(key, value);
            return editor.commit();
        }
    }

    public static int getInt(String key, int def) {
        synchronized (PREFS_LOCK) {
            SharedPreferences prefs = getPrefs();
            return prefs.getInt(key, def);
        }
    }

    public static boolean putInt(String key, int value) {
        synchronized (PREFS_LOCK) {
            SharedPreferences.Editor editor = getPrefs().edit();
            editor.putInt(key, value);
            return editor.commit();
        }
    }

    public static String getString(String key, String def) {
        synchronized (PREFS_LOCK) {
            SharedPreferences prefs = getPrefs();
            return prefs.getString(key, def);
        }
    }

    public static boolean putString(String key, String value) {
        synchronized (PREFS_LOCK) {
            SharedPreferences.Editor editor = getPrefs().edit();
            editor.putString(key, value);
            return editor.commit();
        }
    }

    public static boolean contains(String key) {
        return getPrefs().contains(key);
    }

    public static SharedPreferences.Editor getEditor() {
        return getPrefs().edit();
    }
}
