package beach.daytona.metris.models;

import com.jins_jp.meme.MemeRealtimeData;

import lombok.Data;

/**
 * Created by sakemotoshinya on 15/07/25.
 */
@Data
public class MEMEData {

    private int eyeMoveUp;
    private int eyeMoveDown;
    private int eyeMoveLeft;
    private int eyeMoveRight;
    private int blinkSpeed;
    private int blinkStrength;
    private boolean walking;
    private int roll;
    private int pitch;
    private int yaw;
    private int accY;
    private int accX;
    private int accZ;

    public MEMEData(MemeRealtimeData data) {
        setEyeMoveUp(data.getEyeMoveUp());
        setEyeMoveDown(data.getEyeMoveDown());
        setEyeMoveLeft(data.getEyeMoveLeft());
        setEyeMoveRight(data.getEyeMoveRight());
        setBlinkSpeed(data.getBlinkSpeed());
        setBlinkStrength(data.getBlinkStrength());
        setWalking(data.isWalking());
        setRoll(data.getRoll());
        setPitch(data.getPitch());
        setYaw(data.getYaw());
        setAccX(data.getAccX());
        setAccY(data.getAccY());
        setAccZ(data.getAccZ());
    }

    public String toJsonString() {
        StringBuffer buff = new StringBuffer();
        buff.append("{");
        buff.append(String.format("\"eyeMoveUp\":%d,", eyeMoveUp));
        buff.append(String.format("\"eyeMoveDown\":%d,", eyeMoveDown));
        buff.append(String.format("\"eyeMoveLeft\":%d,", eyeMoveLeft));
        buff.append(String.format("\"eyeMoveRight\":%d,", eyeMoveRight));
        buff.append(String.format("\"blinkSpeed\":%d,", blinkSpeed));
        buff.append(String.format("\"blinkStrength\":%d,", blinkStrength));
        buff.append(String.format("\"walking\":%b,", walking));
        buff.append(String.format("\"roll\":%d,", roll));
        buff.append(String.format("\"pitch\":%d,", pitch));
        buff.append(String.format("\"yaw\":%d,", yaw));
        buff.append(String.format("\"accX\":%d,", accX));
        buff.append(String.format("\"accY\":%d,", accY));
        buff.append(String.format("\"accZ\":%d", accZ));
        buff.append("}");
        return buff.toString();
    }
}
