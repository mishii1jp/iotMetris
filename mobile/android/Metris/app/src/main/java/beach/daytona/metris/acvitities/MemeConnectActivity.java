package beach.daytona.metris.acvitities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeScanListener;
import com.jins_jp.meme.MemeStatus;

import java.util.ArrayList;
import java.util.List;

import beach.daytona.metris.R;
import beach.daytona.metris.models.MEMEListItem;
import beach.daytona.metris.views.MemeListAdapter;

/**
 * Created by sakemotoshinya on 15/07/26.
 */
public class MemeConnectActivity extends ActionBarActivity {

    private MemeLib memeLib;
    private ListView deviceListView;
    private List<MEMEListItem> memeListItemList = new ArrayList<MEMEListItem>();
    private ArrayAdapter<MEMEListItem> memeListItemArrayAdapter;
    private TextView emptyView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MemeConnectActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_meme_connect);

        if (savedInstanceState == null) {
            setupFullScreen();
            init();
            setupListener();
            startScan();
        }
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
        memeLib = MemeLib.getInstance();

        deviceListView = (ListView)findViewById(R.id.deviceListView);
        emptyView = (TextView)findViewById(R.id.text_empty);
        memeListItemArrayAdapter = new MemeListAdapter(this, R.layout.list_item, memeListItemList);
        deviceListView.setAdapter(memeListItemArrayAdapter);
    }

    private void setupListener() {
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                stopScan();
                ListView listView = (ListView)adapterView;
                MEMEListItem item = (MEMEListItem)listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item + " clicked",
                        Toast.LENGTH_LONG).show();
                Intent intent = MetrisActivity.newIntent(MemeConnectActivity.this, item.getAddress());
                startActivity(intent);
            }
        });

        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                research();
            }
        });
    }

    private void research() {
        emptyView.setEnabled(false);
        emptyView.setVisibility(View.VISIBLE);
        memeListItemArrayAdapter.clear();
        memeListItemArrayAdapter.notifyDataSetChanged();
        startScan();
    }



    /**
     * Bluetoot端末を探す
     * */
    private void startScan() {
        if (memeLib.isScanning()) {
            Log.d("MemeData", "now searching");
            return;
        }

        MemeStatus status = memeLib.startScan(new MemeScanListener() {
            @Override
            public void scanCallback(String address) {
                emptyView.setEnabled(false);
                emptyView.setVisibility(View.INVISIBLE);
                memeListItemList.add(new MEMEListItem(address));
                /**
                 * 見つかったらリストを更新する。
                 * */
                runOnUiThread(new Runnable() {
                    public void run() {
                        memeListItemArrayAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        if (status == MemeStatus.MEME_ERROR_APP_AUTH) {
            Toast.makeText(this, "App Auth Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void stopScan() {
        if (!memeLib.isScanning())
            return;

        memeLib.stopScan();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("MemeData", "onBackPressed");
        stopScan();
    }


}
