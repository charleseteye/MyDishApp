package eteyecharles.com.mydish.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import eteyecharles.com.mydish.Model.DataItem;

/**
 * Created by CHARL on 15-Nov-17.
 */

public class DataSource {
    private Context mContext;
    SQLiteOpenHelper mDbHelper;
    SQLiteDatabase mDatabse;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper=new DBHelper(mContext);
        mDatabse=mDbHelper.getWritableDatabase();
    }
    public void open(){
        mDatabse=mDbHelper.getWritableDatabase();

    }
    public  void close(){
        mDbHelper.close();

    }
    public DataItem createItem(DataItem item){
        ContentValues values=item.toValues();
        mDatabse.insert(ItemTable.TABLE_ITEMS ,null,values);
        return item;
    }
}
