package beach.daytona.metris.models;

import android.widget.ImageView;

import lombok.Data;

/**
 * Created by sakemotoshinya on 15/07/26.
 */
@Data
public class MEMEListItem {

    private int imageId;
    private String address;


    public MEMEListItem(String address) {
        this.imageId = 0;
        this.address = address;
    }
}
