package com.azspc.writterhw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String title;
    String date;
    static public String lesson = "";
    static public String html_str = "";

    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = "";
        String[] d_f = new SimpleDateFormat("dd MMMM", new Locale("uk", "UA")).format(Calendar.getInstance().getTime()).split(" ");
        date = d_f[0] + " " + ((d_f[1].charAt(0) + "").toUpperCase()) + d_f[1].substring(1);
        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);}
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy newPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(newPolicy);
        setTitle(date);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.titles));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spin);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title = getResources().getStringArray(R.array.titles)[position];
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lessons));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spin2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lesson = getResources().getStringArray(R.array.lessons)[position];
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void openHtml(View v) {
        html_str = parseHTML(((EditText) findViewById(R.id.et)).getText() + "");
        startActivity(new Intent(this, HtmlView.class));
    }

    String parseHTML(String in) {
        String[][] s = {
                {"!.!", "</u>"},
                {"-.-", "</p>"},
                {"_._", "</b>"},
                {"\"\"", "<q>"},
                {"\".\"", "</q>"},
                {"--", "<p centered>"},
                {"__", "<b>"},
                {"!!", "<u>"},
                {"\n", "</p><p>"},
                {"\r\n", "</p><p>"}
        };
        String ret = "<div class=\"page\"><p centered>" + date + "<br>" + title + "<br><br></p><p>" + in + "</p></div>";
        for (String[] strings : s) ret = ret.replaceAll(strings[0], strings[1]);
        Log.e("asd ret", ret);
        Log.e("asd in", in);
        return ret;
    }
}