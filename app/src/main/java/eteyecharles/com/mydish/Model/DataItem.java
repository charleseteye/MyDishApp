package eteyecharles.com.mydish.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import eteyecharles.com.mydish.database.ItemTable;

/**
 * Created by CHARL on 21-Oct-17.
 */

public class DataItem  implements Parcelable{

    private String itemId;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private int itemSortPosition;
    private double itemPrice;
    private String itemImage;

    //constructor with no argument


    public DataItem() {
    }

    public DataItem(String itemId, String itemName, String itemCategory, String itemDescription, int itemSortPosition, double itemPrice, String itemImage) {
        if (itemId == null) {
            itemId= UUID.randomUUID().toString();

        }
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemSortPosition = itemSortPosition;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getItemSortPosition() {
        return itemSortPosition;
    }

    public void setItemSortPosition(int itemSortPosition) {
        this.itemSortPosition = itemSortPosition;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public ContentValues toValues() {
        ContentValues values=new ContentValues(7);
        values.put(ItemTable.COLUMN_ID, itemId);
        values.put(ItemTable.COLUMN_NAME, itemName);
        values.put(ItemTable.COLUMN_DESCRIPTION, itemDescription);
        values.put(ItemTable.COLUMN_CATEGORY, itemCategory);
        values.put(ItemTable.COLUMN_POSITION,itemSortPosition);
        values.put(ItemTable.COLUMN_IMAGE, itemPrice);
        values.put(ItemTable.COLUMN_IMAGE, itemImage);
        return  values;

    }

    @Override
    public String toString() {
        return "DataItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemSortPosition=" + itemSortPosition +
                ", itemPrice=" + itemPrice +
                ", itemImage='" + itemImage + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.itemName);
        dest.writeString(this.itemDescription);
        dest.writeString(this.itemCategory);
        dest.writeInt(this.itemSortPosition);
        dest.writeDouble(this.itemPrice);
        dest.writeString(this.itemImage);
    }

    protected DataItem(Parcel in) {
        this.itemId = in.readString();
        this.itemName = in.readString();
        this.itemDescription = in.readString();
        this.itemCategory = in.readString();
        this.itemSortPosition = in.readInt();
        this.itemPrice = in.readDouble();
        this.itemImage = in.readString();
    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}
