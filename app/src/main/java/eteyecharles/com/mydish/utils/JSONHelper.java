package eteyecharles.com.mydish.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import eteyecharles.com.mydish.Model.DataItem;

/**
 * Created by CHARL on 13-Nov-17.
 */

public class JSONHelper {
    public static final String FILE_NAME="menuitem.json";
    public static final String  TAG="JSONHelper";


    public static boolean exportToJSON(Context context, List<DataItem> dataItemList) {
        DataItems menuData=new DataItems();
        menuData.setDataItems(dataItemList);

        Gson gson=new Gson();
        String jsonString=gson.toJson(menuData);
        Log.i(TAG,"exportToJSON"+   jsonString);

        FileOutputStream fileOutputStream=null;
        File file=new File(Environment.getExternalStorageDirectory(),FILE_NAME);

        try {
            fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream == null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  false;

    }

    public static List<DataItem> importFromJSON(Context context) {
        FileReader  fileReader=null;

        try {
            File file=new File(Environment.getExternalStorageDirectory(),FILE_NAME);
            fileReader=new FileReader(file);
            Gson gson=new Gson();
            DataItems dataItems=gson.fromJson(fileReader, DataItems.class );
            return  dataItems.getDataItems();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileReader == null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return null;
    }

    static  class  DataItems{
        List<DataItem> dataItems;

        public List<DataItem> getDataItems() {
            return dataItems;
        }

        public void setDataItems(List<DataItem> dataItems) {
            this.dataItems = dataItems;
        }
    }
}
