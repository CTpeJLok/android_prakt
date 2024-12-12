package com.example.prakt_12_sqlite;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    private Button button;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.list);

        dbHelper = new DBHelper(this);

        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void get(View view) {
        ArrayList<HashMap<String, String>> persons = new ArrayList<>();
        HashMap<String, String> person;

        Cursor cursor = database.rawQuery("SELECT * FROM persons;", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            person = new HashMap<>();
            person.put("name", cursor.getString(1));
            person.put("achievement", cursor.getString(2));
            persons.add(person);
            cursor.moveToNext();
        }

        cursor.close();

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, persons, R.layout.item,
                new String[] {"name", "achievement"},
                new int[] {R.id.person, R.id.achievement});
        listView.setAdapter(simpleAdapter);
    }
}