package eteyecharles.com.mydish;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import eteyecharles.com.mydish.Model.DataItem;
import eteyecharles.com.mydish.Sample.SampleDataProvider;
import eteyecharles.com.mydish.database.DBHelper;
import eteyecharles.com.mydish.database.DataSource;
import eteyecharles.com.mydish.utils.JSONHelper;

public class MainActivity extends AppCompatActivity {
    private static final int SIGNIN_REQUEST = 1001;
    public static final String MY_GLOBAL_PREFS = "my_global_prefs";
    private static final String TAG = "MainActivity";
    List<DataItem> dataItemList = SampleDataProvider.dataItemList;
    private boolean permissionGranted;
    private static final int REQUEST_PERMISSION_WRITE = 1002;

    DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataSource=new DataSource(this);
        mDataSource.open();
        Toast.makeText(this,"DATABASE ACQUIRED",Toast.LENGTH_SHORT).show();

        for (DataItem item: dataItemList){
            try {
                mDataSource.createItem(item);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }

        checkPermissions();

        List<String> itemNames=new ArrayList<>();

        //Declare instance of new DataItem class
        // DataItem item =new DataItem(null,"My Menu item\n","a category\n","description\n",1,9.95,"apple_pie.jpg");

//        TextView tvOut = (TextView) findViewById(R.id.tvOut);
//        tvOut.setText("");

        Collections.sort(dataItemList, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });
//
        for (DataItem item: dataItemList){
            //           tvOut.append(item.getItemName()+"\n");
            itemNames.add(item.getItemName());
        }
        Collections.sort(itemNames);

//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,itemNames);
        DataItemAdapter adapter=new DataItemAdapter( this,dataItemList);
        SharedPreferences settings=PreferenceManager.getDefaultSharedPreferences(this);
        boolean grid=settings.getBoolean(getString(R.string.pref_display_grid),false);

//        SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
//        boolean grid=settings.getBoolean(getString(R.string.pref_display_grid), false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);

        if(grid){
            recyclerView.setLayoutManager(new GridLayoutManager(this ,3));
        }

//        if (grid){
//            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        }
        recyclerView.setAdapter(adapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signin:
                Intent intent = new Intent(this, SignActivity.class);
                startActivityForResult(intent, SIGNIN_REQUEST);
                return true;
            case R.id.action_settings:
                Intent settingsIntent=new Intent(this,PrefsActivity.class);
                startActivity(settingsIntent);
                return  true;
            case R.id.action_export:
                boolean result = JSONHelper.exportToJSON(this, dataItemList);
                if (result) {
                    Toast.makeText(this, "Data exported", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Export failed", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_import:
                List<DataItem> dataItems=JSONHelper.importFromJSON(this);
                if (dataItems == null) {
                    for (DataItem dataItem:dataItems)
                    {
                        Log.i(TAG,"OnoptionItemSelected"+dataItem.getItemName());
                    }
                    
                }
//            case  R.id.action_settings:
//                Intent  intent1=new Intent(this,PrefsActivity.class);
//                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST) {
            String email = data.getStringExtra(SignActivity.EMAIL_KEY);
            Toast.makeText(this, "You signed in as " + email, Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor =
                    getSharedPreferences(MY_GLOBAL_PREFS,MODE_PRIVATE).edit();
            editor.putString(SignActivity.EMAIL_KEY,email);
            editor.apply();

//            SharedPreferences.Editor editor =
//                    getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE).edit();
//            editor.putString(SignActivity.EMAIL_KEY, email);
//            editor.apply();

        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
