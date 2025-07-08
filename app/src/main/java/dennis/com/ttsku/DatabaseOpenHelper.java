package dennis.com.ttsku;


import android.content.Context;


public class DatabaseOpenHelper extends ExternalSQLiteOpenHelper {
    /**
     * Name of the database.
     */
   // private static final String DATABASE_NAME = "renungan.db";


    /**
     * Version of the database. Only used to import from assets.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Use this constructor if you want to import database from assets/database directory.
     */
    public DatabaseOpenHelper(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Use this constructor if you want to import database from external directory.
     */
    public DatabaseOpenHelper(Context context, String DATABASE_NAME, String sourceDirectory) {
        super(context, DATABASE_NAME, sourceDirectory, null);
    }
}
