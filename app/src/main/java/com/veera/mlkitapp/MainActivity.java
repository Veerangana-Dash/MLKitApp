package com.veera.mlkitapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private TextView tv1,tv2,tv3;
    private Button capture, detect;
    private static final int REQUESTCODE=1;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.image);
        tv1=findViewById(R.id.text1);
        tv2=findViewById(R.id.text2);
        tv3=findViewById(R.id.text3);
        capture=findViewById(R.id.capture);
        detect=findViewById(R.id.detect);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open camera and click a picture
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityIfNeeded(i,REQUESTCODE);
            }
        });
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateLabels();
            }
        });

    }

    private void generateLabels() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bmp);
        FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
                .getVisionLabelDetector();

        Task<List<FirebaseVisionLabel>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionLabel>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionLabel> labels) {
                                        // Task completed successfully
                                        for (FirebaseVisionLabel label: labels) {
                                            String text = label.getLabel();
                                            String entityId = label.getEntityId();
                                            float confidence = label.getConfidence();
                                            tv1.setText("Label Identified="+text);
                                            tv2.setText("Entity ID="+entityId);
                                            tv3.setText("Confidence="+confidence);
                                        }
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                    }
                                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE && resultCode==RESULT_OK) {
            //Image is obtained
            Bundle bundle=data.getExtras();
            bmp=(Bitmap) bundle.get("data");
            image.setImageBitmap(bmp);
        }
    }
}
