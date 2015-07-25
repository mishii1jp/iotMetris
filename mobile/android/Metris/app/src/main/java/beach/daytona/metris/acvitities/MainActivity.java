package beach.daytona.metris.acvitities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeScanListener;
import com.jins_jp.meme.MemeStatus;

import java.util.ArrayList;
import java.util.List;

import beach.daytona.metris.R;

public class MainActivity extends ActionBarActivity {

    private MemeLib memeLib;
    private ListView deviceListView;
    private List<String> scannedAddresses;
    private ArrayAdapter<String> scannedAddressAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            init();
        }
    }

    private void init() {
        memeLib = MemeLib.getInstance();

        deviceListView = (ListView)findViewById(R.id.deviceListView);
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stopScan();
                String address = scannedAddresses.get(i);
                Intent intent = MetrisActivity.newIntent(MainActivity.this, address);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (memeLib.isScanning()) {
            menu.findItem(R.id.action_scan).setVisible(false);
            menu.findItem(R.id.action_stop).setVisible(true);
        } else {
            menu.findItem(R.id.action_scan).setVisible(true);
            menu.findItem(R.id.action_stop).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_scan) {
            startScan();
            return true;
        } else if (itemId == R.id.action_stop) {
            stopScan();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Bluetoot端末を探す
     * */
    private void startScan() {
        if (memeLib.isScanning())
            return;

        scannedAddresses = new ArrayList<>();
        scannedAddressAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scannedAddresses);
        deviceListView.setAdapter(scannedAddressAdapter);

        setProgressBarIndeterminateVisibility(true);
        invalidateOptionsMenu();
        MemeStatus status = memeLib.startScan(new MemeScanListener() {
            @Override
            public void scanCallback(String address) {
                scannedAddresses.add(address);

                /**
                 * 見つかったらリストを更新する。
                 * */
                runOnUiThread(new Runnable() {
                    public void run() {
                        scannedAddressAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        if (status == MemeStatus.MEME_ERROR_APP_AUTH) {
            Toast.makeText(this, "App Auth Failed", Toast.LENGTH_LONG).show();
            setProgressBarIndeterminateVisibility(false);
        }
    }

    void stopScan() {
        if (!memeLib.isScanning())
            return;

        memeLib.stopScan();
        setProgressBarIndeterminateVisibility(false);
        invalidateOptionsMenu();
    }


}
