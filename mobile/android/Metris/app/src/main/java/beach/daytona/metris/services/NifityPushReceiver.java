package beach.daytona.metris.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jins_jp.meme.MemeLib;
import com.nifty.cloud.mb.NCMBDialogPushConfiguration;
import com.nifty.cloud.mb.NCMBPush;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import beach.daytona.metris.Utils.Const;

public class NifityPushReceiver extends BroadcastReceiver {
    public NifityPushReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MemeData", "onReceive");

        //NCMBDialogPushConfigurationクラスのインスタンスを作成
        NCMBDialogPushConfiguration dialogPushConfiguration = new NCMBDialogPushConfiguration();
        dialogPushConfiguration.setDisplayType(NCMBDialogPushConfiguration.DIALOG_DISPLAY_DIALOG);
        NCMBPush.dialogPushHandler(context, intent, dialogPushConfiguration);
        MemeLib.init(context, Const.APP_CLIENT_ID, Const.APP_CLIENT_SECRET);
        MemeLib.getInstance().disconnect();
    }
}