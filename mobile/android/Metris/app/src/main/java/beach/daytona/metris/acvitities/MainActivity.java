package beach.daytona.metris.acvitities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBInstallation;
import com.nifty.cloud.mb.NCMBPush;
import com.nifty.cloud.mb.RegistrationCallback;

import beach.daytona.metris.R;
import beach.daytona.metris.Utils.Const;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            setupFullScreen();
        }
        pushRegister();

        findViewById(R.id.button_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MemeConnectActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupFullScreen() {
        final View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("MemeData", "onWindowFocusChanged");
        final View decorView = this.getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    private void pushRegister() {
        final NCMBInstallation instllation = NCMBInstallation.getCurrentInstallation();
        instllation.getRegistrationIdInBackground(Const.SENDER_ID, new RegistrationCallback() {
            @Override
            public void done(NCMBException e) {
                if (e == null) {
                    Log.d("MemeData", "push send done");
                    // 成功
                    try {
                        instllation.save();
                        Log.d("MemeData", "push send success");
                    } catch (NCMBException le) {
                        // サーバ側への保存エラー
                        Log.d("MemeData", "push send server error");
                    }
                } else {
                    // エラー
                    Log.d("MemeData", "push send some error");
                }
            }
        });
        NCMBPush.setDefaultPushCallback(this, MainActivity.class);
        NCMBPush push = new NCMBPush();
        push.setDialog(true);
    }
}
