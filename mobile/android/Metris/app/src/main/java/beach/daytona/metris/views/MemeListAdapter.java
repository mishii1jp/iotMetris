package beach.daytona.metris.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import beach.daytona.metris.R;
import beach.daytona.metris.models.MEMEListItem;

/**
 * Created by sakemotoshinya on 15/07/26.
 */
public class MemeListAdapter extends ArrayAdapter<MEMEListItem> {

    private List<MEMEListItem> mList;

    public MemeListAdapter(Context context, int resource, List<MEMEListItem> objects) {
        super(context, resource, objects);
        mList = objects;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MEMEListItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(MEMEListItem item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textAddress.setText(mList.get(position).getAddress());
        holder.imageMeme.setImageResource(mList.get(position).getImageId());
        return convertView;
    }


    private static class ViewHolder {

        ImageView imageMeme;
        TextView textAddress;

        public ViewHolder(View view) {
            this.imageMeme = (ImageView) view.findViewById(R.id.image_meme);
            this.textAddress = (TextView) view.findViewById(R.id.text_address);
        }

    }
}
