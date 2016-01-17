package omarbizreh.com.trainingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.databinding.ObservableArrayList;
import android.provider.BaseColumns;

import java.util.UUID;

import omarbizreh.com.trainingapp.DataModels.UserModel;

/**
 * Created by Omar on 1/17/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TYPE_TEXT = " TEXT";
    public static final String SEPERATOR_COMMA = ",";
    public static final String SQL_CREATE_USERS_TABLE =
            "CREATE TABLE " + Users.TABLE_NAME + " ("
                    + Users._ID + " INTEGER PRIMARY KEY"
                    + SEPERATOR_COMMA + Users.COLUMN_NAME_ENTRY_ID
                    + TYPE_TEXT + SEPERATOR_COMMA + Users.COLUMN_NAME_DISPLAY_NAME
                    + TYPE_TEXT + SEPERATOR_COMMA + Users.COLUMN_NAME_PHONENUMBER
                    + TYPE_TEXT + ")";
    public static final String DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myapp.db";

    public DatabaseHandler(Context _Context) {
        super(_Context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_USERS);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public long InsertUser(UserModel mUser) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        ContentValues mValues = new ContentValues();
        mValues.put(Users.COLUMN_NAME_DISPLAY_NAME, "Omar Bizreh");
        mValues.put(Users.COLUMN_NAME_PHONENUMBER, "111-111111");
        mValues.put(Users.COLUMN_NAME_ENTRY_ID, UUID.randomUUID().toString());
        long mRowID = mDatabase.insert(Users.TABLE_NAME, null, mValues);
        return mRowID;
    }

    public ObservableArrayList<UserModel> GetUsers() {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        String[] projection = new String[]{
                Users._ID,
                Users.COLUMN_NAME_ENTRY_ID,
                Users.COLUMN_NAME_DISPLAY_NAME,
                Users.COLUMN_NAME_PHONENUMBER
        };

        String sortOrder = Users.COLUMN_NAME_DISPLAY_NAME + " asc";
        Cursor cursor = mDatabase.query(Users.TABLE_NAME,
                projection, null, null, null, null, sortOrder);

        ObservableArrayList<UserModel> mUsers = new ObservableArrayList<UserModel>();
        while (cursor.moveToNext()) {
            UserModel user = new UserModel(
                    cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_DISPLAY_NAME))
                    , cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_ENTRY_ID)));
            mUsers.add(user);
        }
        return mUsers;
    }

    public void DeleteUser(String EntryID) {
        String selection = Users.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = new String[]{EntryID};
        this.getWritableDatabase().delete(Users.TABLE_NAME, selection, selectionArgs);
    }

    public Integer UpdateUser(UserModel mUser) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        ContentValues mValues = new ContentValues();
        mValues.put(Users.COLUMN_NAME_DISPLAY_NAME, mUser.getDisplayName());
        mValues.put(Users.COLUMN_NAME_PHONENUMBER, mUser.getPhoneNumber());

        String selection = Users.COLUMN_NAME_ENTRY_ID + " LIKE = ?";
        String[] selectionArgs = new String[]{mUser.getPhoneNumber()};

        int mCount = mDatabase.update(Users.TABLE_NAME, mValues, selection, selectionArgs);
        return mCount;
    }


    public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "tblUsers";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DISPLAY_NAME = "display_name";
        public static final String COLUMN_NAME_PHONENUMBER = "phone_number";

    }

}
