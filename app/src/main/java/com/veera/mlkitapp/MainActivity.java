package com.veera.mlkitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private TextView tv;
    private Button capture, detect;
    private static final int REQUESTCODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.image);
        tv=findViewById(R.id.text);
        capture=findViewById(R.id.capture);
        detect=findViewById(R.id.detect);
    }
}
