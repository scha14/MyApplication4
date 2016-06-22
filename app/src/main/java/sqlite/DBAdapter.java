package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Contact;

/**
 * Created by Sukriti on 6/16/16.
 */
public class DBAdapter {

    private static final String DATABASE_NAME = "contacts_database";
    private static final String DATABASE_TABLE = "contacts_table";
    private static final int DATABASE_VERSION = 1;
    // Column Names!
    private static final String KEY_ID = "_id"; // Primary Key - Unique For Each Object
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String PHONE_NUMBER = "phone";
    private static final String EMAIL = "email";


    // Heirarchy - DatabseName -> we have multiple Tables -> Within each table we have Multiple Columns

    private static final String CREATE_DATABASE = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT NOT NULL," + AGE + " TEXT NOT NULL," + PHONE_NUMBER + " TEXT NOT NULL," + EMAIL + " TEXT NOT NULL" + ")";

    // CREATE TABLE TABLE_NAME()
    // Within the parameters we provide the columsn and their properties

    final Context context;
    DatabaseHelper DBHelper; // THis class helps create the database
    SQLiteDatabase db;

    public DBAdapter(Context c) {
        this.context = c;
        DBHelper = new DatabaseHelper(context);
    }

    // This Class Allows us to create Database, To Upgrade Database!
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            // create the database
            try {
                db.execSQL(CREATE_DATABASE); // Creates Table and Columns in the Database
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                // We will have tell the device to delete the older version and use the latest version
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);// DELETES The Table if present.
                onCreate(db);
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    } // End of DBHelper Class

    // Sqlite has C.R.U.D functions
    // Create Retrieve Update Delete
    // First have to open the database, Then Perform These Functions and then close the database.

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }


    // 1. Create Data

    public long createContact(Contact c) {
        // We will have one row of data when we call this method.
        // long is the unique id for each row which is automatially created.
        // We use ContentValues to insert data into the row

        ContentValues cV = new ContentValues();
        cV.put(NAME, c.getName());
        cV.put(AGE, c.getAge());
        cV.put(EMAIL, c.getEmail());
        cV.put(PHONE_NUMBER, c.getPhone());
        return db.insert(DATABASE_TABLE, null, cV); // insert returns a long value which is unique for each row and this long value is set as Id for each Contact while creating a contact

    }

    // 2. Delete Data

    public boolean deleteContact(long rowId) { // Row Id is the unique id for each row!
        // 2nd Parameter is we will have to use a query to search for that particular row!
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    // Retrieve All Data
    // Cursor helps us to navigate Colums and Rows!

    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ID, NAME, AGE, PHONE_NUMBER, EMAIL}, null, null, null, null, null);
    }


    // Retrieve Individual Data or Single Ro

    public Cursor getIndividualContact(long rowId) throws SQLException {

        Cursor c = db.query(true, DATABASE_TABLE, new String[]{KEY_ID, NAME, AGE, PHONE_NUMBER, EMAIL}, KEY_ID + "=" + rowId, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    public Cursor getSearchedName(String name) throws SQLException {

        Cursor c = db.query(true, DATABASE_TABLE, new String[]{KEY_ID, NAME, AGE, PHONE_NUMBER, EMAIL}, NAME + "=" + name, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    // Update Data in a Row

    public void updateContact(long rowId, Contact con) {

        ContentValues cV = new ContentValues();
        cV.put(NAME, con.getName());
        cV.put(AGE, con.getAge());
        cV.put(EMAIL, con.getEmail());
        cV.put(PHONE_NUMBER, con.getPhone());
        db.update(DATABASE_TABLE, cV, KEY_ID + "=" + rowId, null);

    }





}


