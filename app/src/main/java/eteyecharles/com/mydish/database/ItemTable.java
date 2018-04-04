package eteyecharles.com.mydish.database;

/**
 * Created by CHARL on 15-Nov-17.
 */

public class ItemTable {
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_DESCRIPTION = "itemDescription";
    public static final String COLUMN_CATEGORY = "itemCategory";
    public static final String COLUMN_POSITION = "itemSortPosition";
    public static final String COLUMN_PRICE = "itemPrice";
    public static final String COLUMN_IMAGE = "itemImage";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_POSITION + " INTEGER," +
                    COLUMN_PRICE + " REAL," +
                    COLUMN_IMAGE + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;


}
