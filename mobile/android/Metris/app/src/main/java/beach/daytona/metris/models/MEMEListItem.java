package beach.daytona.metris.models;

import android.widget.ImageView;

import beach.daytona.metris.R;
import lombok.Data;

/**
 * Created by sakemotoshinya on 15/07/26.
 */
@Data
public class MEMEListItem {

    private int imageId;
    private String address;


    public MEMEListItem(String address) {
        this.imageId = R.drawable.icon_glass;
        this.address = address;
    }
}
