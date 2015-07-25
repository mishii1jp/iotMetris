package beach.daytona.metris.models;

import com.jins_jp.meme.MemeRealtimeData;

/**
 * Created by sakemotoshinya on 15/07/25.
 */
public class MEMEData {

    private int accY;

    public MEMEData(MemeRealtimeData data) {
        setAccY(data.getAccY());
    }
    public void setAccY(int accY) {
        this.accY = accY;
    }

    public int getAccY() {
        return accY;
    }

    public String toJsonString() {
        StringBuffer buff = new StringBuffer();
        buff.append("{\"accY\":"+ getAccY() + "}");
        return buff.toString();
    }
}
