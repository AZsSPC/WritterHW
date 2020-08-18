package com.azspc.writterhw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String title;
    String date;

    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = "";
        String[] d_f = new SimpleDateFormat("dd MMMM", new Locale("uk", "UA"))
                .format(Calendar.getInstance().getTime()).split(" ");
        date = d_f[0] + " " + ((d_f[1].charAt(0) + "").toUpperCase()) + d_f[1].substring(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(date);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.wtitles));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spin);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title = getResources().getStringArray(R.array.wtitles)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void saveHTML(View v) {
        ((ClipboardManager) Objects.requireNonNull(v.getContext().getSystemService(Context.CLIPBOARD_SERVICE)))
                .setPrimaryClip(ClipData.newPlainText("", parseHTML()));
        Toast.makeText(getBaseContext(), getString(R.string.copied), Toast.LENGTH_SHORT).show();
    }

    String parseHTML() {
        String in = ((EditText) findViewById(R.id.et)).getText() + "";
        String ret = "";
        return ret;
    }
}