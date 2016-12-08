package com.weex.sammy.mcshop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sammy on 2016/11/26.
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mcshop.db";
    public static final String DATABASE_PATH = "/data/data/com.weex.sammy.mcshop/databases/";
    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public void createDatabase() throws IOException {
        boolean isDatabaseExist = checkDatabase();
        if(isDatabaseExist) {
            //DO NOTHING
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public boolean checkDatabase() {
        SQLiteDatabase checkDatabase = null;

        try {
            String mPath = DATABASE_PATH + DATABASE_NAME;
            checkDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e) {

        }
        if (checkDatabase != null) {
            checkDatabase.close();
        }
        return checkDatabase != null ? true : false;
    }

    public void copyDatabase() throws IOException {
        InputStream mInputStream = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = mInputStream.read(buffer)) > 0) {
            mOutputStream.write(buffer, 0, length);
        }
        mOutputStream.flush();
        mOutputStream.close();
        mInputStream.close();
    }

    public void openDatabase() throws SQLiteException {
        String mPath = DATABASE_PATH + DATABASE_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public List<MCItem> getAllEntriesFromTableItems(String category) {
        String[] projection = {"id", "name", "icon", "category"};
        String selection = "category=?";
        String[] selectionArgs={category};
        Cursor c = mDatabase.query("items", projection, selection, selectionArgs, null, null, null);
        int numberOfItems = c.getCount();
        c.moveToFirst();
        ArrayList itemList = new ArrayList();
        for(int i = 0; i < numberOfItems; i++) {
            MCItem item = new MCItem();
            item.name = c.getString(c.getColumnIndexOrThrow("name"));
            item.icon = c.getString(c.getColumnIndexOrThrow("icon"));
            item.id = c.getString(c.getColumnIndexOrThrow("id"));
            item.category = c.getString(c.getColumnIndexOrThrow("category"));
            itemList.add(item);
            c.moveToNext();
        }
        c.close();
        return itemList;
    }

    public MCItem getItemFromId(String id) {
        MCItem item = new MCItem();
        String selection = "id=?";
        String[] selectionArgs = {id};
        String[] projection = null;


        Cursor c = mDatabase.query("items", projection, selection, selectionArgs, null, null, null);
        if(c.getCount() == 0) return null;
        c.moveToFirst();
        item.name = c.getString(c.getColumnIndexOrThrow("name"));
        item.icon = c.getString(c.getColumnIndexOrThrow("icon"));
        item.id = c.getString(c.getColumnIndexOrThrow("id"));
        item.category = c.getString(c.getColumnIndexOrThrow("category"));
        c.close();
        return item;
    }

    public MCItemFormula getItemFormulaFromId(String id) {
        MCItemFormula formula = new MCItemFormula();
        String selection = "id=?";
        String[] selectionArgs = {id};
        String[] projection = {"formula"};

        Cursor c = mDatabase.query("items", projection, selection, selectionArgs, null, null, null);
        if(c.getCount() == 0) {
            return null;
        } else {
            c.moveToFirst();
            MCItem targetItem = getItemFromId(id);
            String formulas = c.getString(c.getColumnIndexOrThrow("formula"));
            if(formulas.isEmpty()) return null;
            String[] formulaArray = formulas.split("_");
            formula.outputNumber = formulaArray[0];
            List<MCItemForFormula> formulaList = new ArrayList<>();
            for(int i = 1; i < 10; i++) {
                if(formulaArray[i].equals("null")) continue;
                MCItem item = getItemFromId(formulaArray[i]);
                MCItemForFormula formulaItem = new MCItemForFormula();
                formulaItem.id = item.id;
                formulaItem.name = item.name;
                formulaItem.icon = item.icon;
                formulaItem.category = item.category;
                formulaItem.position = i;
                formulaList.add(formulaItem);
            }
            formula.items = formulaList;
            formula.item = targetItem;
            formula.isRequiredFurnace = formulaArray[10];
            c.close();
            return formula;
        }
    }

    public List<MCItem> getItemFromIdOrName(String idOrName) {
        List<MCItem> items = new ArrayList<MCItem>();
        String selection = "id=? or name LIKE ?";
        String[] selectionArgs = {idOrName, "%"+idOrName+"%"};
        String[] projection = null;

        Cursor c = mDatabase.query("items", projection, selection, selectionArgs, null, null, null);
        int ret = c.getCount();
        if(c.getCount() == 0) return null;
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            MCItem item = getItemFromId(c.getString(c.getColumnIndexOrThrow("id")));
            items.add(item);
            c.moveToNext();
        }
        c.close();
        return items;
    }

    @Override
    public synchronized void close() {
        if(mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
