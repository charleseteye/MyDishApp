package eteyecharles.com.mydish;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import eteyecharles.com.mydish.Model.DataItem;

/**
 * Created by CHARL on 21-Oct-17.
 */

public class DataItemAdapterListView extends ArrayAdapter<DataItem> {
    List<DataItem> mdataItems;
    LayoutInflater mInflater;
    public DataItemAdapterListView(Context context, List<DataItem> objects) {
        super(context, R.layout.list_item, objects);
        mdataItems =objects;
        mInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.list_item,parent,false);

        }
        TextView tvName= (TextView) convertView.findViewById(R.id.itemNameText);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView);

        DataItem item =mdataItems.get(position);
        tvName.setText(item.getItemName());
       // imageView.setImageResource(R.drawable.apple_pie);
        InputStream inputStream = null;
        try {
            String imageFile=item.getItemImage();
            inputStream = getContext().getAssets().open(imageFile);
            Drawable d=Drawable.createFromStream(inputStream,null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}
