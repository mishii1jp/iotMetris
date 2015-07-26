package beach.daytona.metris.acvitities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jins_jp.meme.MemeConnectListener;
import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeRealtimeData;
import com.jins_jp.meme.MemeRealtimeListener;

import beach.daytona.metris.R;
import beach.daytona.metris.models.MEMEData;
import beach.daytona.metris.models.OSCMessenger;

public class MetrisActivity extends ActionBarActivity implements MemeRealtimeListener{

    private MemeLib memeLib;
    private final static String ADDRESS = "address";
    private OSCMessenger messenger = new OSCMessenger();

    public static Intent newIntent(Context context, String address) {
        Intent intent = new Intent(context, MetrisActivity.class);
        intent.putExtra(ADDRESS, address);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metris);
        setupFullScreen();
        init();
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

    private void init() {
        String deviceAddress = getIntent().getStringExtra(ADDRESS);
        memeLib = MemeLib.getInstance();
        memeLib.connect(deviceAddress, new MemeConnectListener() {
            @Override
            public void connectCallback(boolean status) {
                memeLib.startListen(MetrisActivity.this);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("MemeData", "onBackPressed");
        memeLib.disconnect();
    }

    @Override
    public void realtimeCallback(MemeRealtimeData memeRealtimeData) {
        MEMEData data = new MEMEData(memeRealtimeData);
        Log.d("MemeData", "MemeData getAccY: " + data.toJsonString());
        messenger.send(data);
    }
}
