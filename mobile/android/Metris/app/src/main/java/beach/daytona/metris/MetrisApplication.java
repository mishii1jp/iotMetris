package beach.daytona.metris;

import android.app.Application;

import com.jins_jp.meme.MemeLib;
import com.nifty.cloud.mb.NCMB;

import beach.daytona.metris.Utils.Const;

/**
 * Created by sakemotoshinya on 15/07/25.
 */
public class MetrisApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        MemeLib.init(getApplicationContext(), Const.APP_CLIENT_ID, Const.APP_CLIENT_SECRET);
        NCMB.initialize(this, Const.NCMB_APPLICATION_KEY, Const.NCMB_CLIENT_KEY);

    }
}
