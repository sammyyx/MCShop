package com.weex.sammy.mcshop;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.io.IOException;

public class MainActivity extends AbsWeexActivity {
    public static MySqliteHelper sqliteHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContainer((ViewGroup) findViewById(R.id.activity_main));
        sqliteHelper = new MySqliteHelper(WeexApplication.getContext());
        try {
            sqliteHelper.createDatabase();
        } catch(IOException e) {
            //throw new Error("Unable to create database");
            Toast.makeText(mInstance.getContext(), "Unable to create database", Toast.LENGTH_SHORT).show();
        }
        try {
            sqliteHelper.openDatabase();
        } catch(SQLiteException e) {
            Toast.makeText(mInstance.getContext(), "SQLiteException", Toast.LENGTH_SHORT).show();
        }
        loadUrl("file://assets/weex/mc-main.js");
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onDestroy() {
        sqliteHelper.close();
        MCShoppingListHelper.mShoppingList = null;
    }

}

