package com.weex.sammy.mcshop;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.taobao.weex.WXSDKInstance;

/**
 * 为了页面跳转而设计的Activity
 */
public class PageActivity extends AbsWeexActivity {

    public static Activity wxPageActivityInstance;

    public static void setCurrentWxPageActivity(Activity activity) {
        wxPageActivityInstance = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        setContainer((ViewGroup) findViewById(R.id.activity_page));
        setCurrentWxPageActivity(this);
        Uri mUri = getIntent().getData();
       // Toast.makeText(PageActivity.this, mUri.toString(), Toast.LENGTH_SHORT).show();
        loadUrl(mUri.toString());
        //eloadUrl("file://assets/weex/mc-np.js");
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        //Toast.makeText(PageActivity.this, "Success Rendering", Toast.LENGTH_SHORT).show();
    }

}
