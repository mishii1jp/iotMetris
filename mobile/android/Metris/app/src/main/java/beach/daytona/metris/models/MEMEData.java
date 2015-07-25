package beach.daytona.metris.models;

import com.jins_jp.meme.MemeRealtimeData;

import lombok.Data;

/**
 * Created by sakemotoshinya on 15/07/25.
 */
@Data
public class MEMEData {

    private int accY;
    private int accX;
    private int accZ;
    private int roll;
    private int pitch;
    private int yaw;

    public MEMEData(MemeRealtimeData data) {
        setAccY(data.getAccY());
        setAccX(data.getAccX());
        setAccZ(data.getAccZ());
        setPitch(data.getPitch());
        setRoll(data.getRoll());
        setYaw(data.getYaw());
    }

    public String toJsonString() {
        StringBuffer buff = new StringBuffer();
        buff.append("{");
        buff.append(String.format("\"accX\":%d,", accX));
        buff.append(String.format("\"accY\":%d,", accY));
        buff.append(String.format("\"accZ\":%d,", accZ));
        buff.append(String.format("\"roll\":%d,", roll));
        buff.append(String.format("\"pitch\":%d,", pitch));
        buff.append(String.format("\"yaw\":%d", yaw));
        buff.append("}");
        return buff.toString();
    }
}
