package com.example.prakt_12_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "sqlite.db"; // Имя базы данных в папке assets
    private static final int DATABASE_VERSION = 1; // Версия базы данных
    private static String DB_LOCATION; // Полный путь к базе данных в устройстве
    private final Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;

        // Определяем путь для базы данных
        DB_LOCATION = context.getApplicationInfo().dataDir + "/databases/";
        copyDatabaseIfNeeded();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Если создаём базу с нуля (не требуется для копируемой базы из assets)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Логика обновления базы данных, если версия изменилась
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
    }

    private boolean checkDB() {
        File fileDB = new File(DB_LOCATION + DB_NAME);
        return fileDB.exists();
    }

    /**
     * Копирует базу данных из assets в папку приложения, если она ещё не существует.
     */
    private void copyDatabaseIfNeeded() {
        File dbFile = new File(DB_LOCATION + DB_NAME);

        if (!dbFile.exists()) {
            // Если база не существует, создаём директорию и копируем файл из assets
            try {
                File databaseDir = new File(DB_LOCATION);
                if (!databaseDir.exists()) {
                    databaseDir.mkdir();
                }
                copyDatabase();
                Log.d(TAG, "Database copied successfully.");
            } catch (IOException e) {
                Log.e(TAG, "Error copying database", e);
            }
        } else {
            Log.d(TAG, "Database already exists.");
        }
    }

    /**
     * Копирует базу данных из папки assets.
     *
     * @throws IOException если не удаётся скопировать файл
     */
    private void copyDatabase() throws IOException {
        InputStream input = context.getAssets().open(DB_NAME); // Читаем файл из assets
        String outFileName = DB_LOCATION + DB_NAME; // Полный путь, куда копировать
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();
    }

    /**
     * Открывает базу данных для чтения/записи.
     *
     * @return объект SQLiteDatabase
     */
    public SQLiteDatabase openDatabase() {
        String path = DB_LOCATION + DB_NAME;
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
