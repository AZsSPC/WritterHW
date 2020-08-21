package com.azspc.writterhw;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.azspc.writterhw.MainActivity.html_str;
import static com.azspc.writterhw.MainActivity.lesson;

public class HtmlView extends AppCompatActivity {
    String html = "";
    final String saveTo = "/storage/emulated/0/AZsSPC/Homeworks/" + lesson;

    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_view);
        String heads = "<meta charset=\"utf-8\"/>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>" +
                "<style type=\"text/css\">" +
                " * {margin:0; padding:0; text-indent:12px}" +
                " .page {margin:6px;}" +
                " [centered] {text-align:center; width:100%; text-indent:0}" +
                " q {color:gray;}" +
                " u {color:green;}" +
                "</style>";
        html = "<head>" + heads + "</head><body>" + html_str + "</body>";
        ((WebView) findViewById(R.id.ww)).loadData(html, "text/html", "en_US");
    }

    public void copyHTML(View v) {
        ((ClipboardManager) Objects.requireNonNull(v.getContext().getSystemService(Context.CLIPBOARD_SERVICE)))
                .setPrimaryClip(ClipData.newPlainText("", html_str));
        Toast.makeText(getBaseContext(), getString(R.string.copied), Toast.LENGTH_SHORT).show();
    }

    public void saveHTML(View v) {
        if (!new File(saveTo).exists()) Log.e("try new file", "" + new File(saveTo).mkdirs());
        String file_name = new SimpleDateFormat("dd-MM", Locale.US).format(Calendar.getInstance().getTime()) + ".html";
        try (FileOutputStream fos = new FileOutputStream(saveTo + "/" + file_name)) {
            byte[] data = html.getBytes();
            fos.write(data, 0, data.length);
            Toast.makeText(getBaseContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Log.e("-------", ex + "");
        }

    }


}
