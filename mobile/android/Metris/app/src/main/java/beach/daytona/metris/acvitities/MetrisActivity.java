package beach.daytona.metris.acvitities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
        init();
    }

    void init() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_metris, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
