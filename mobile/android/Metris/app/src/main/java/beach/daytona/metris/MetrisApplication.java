package beach.daytona.metris;

import android.app.Application;

import com.jins_jp.meme.MemeLib;

import beach.daytona.metris.Utils.Const;

/**
 * Created by sakemotoshinya on 15/07/25.
 */
public class MetrisApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        MemeLib.init(getApplicationContext(), Const.appClientId, Const.appClientSecret);
    }
}
