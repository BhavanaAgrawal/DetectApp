package com.example.atry.detectapp;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.camerakit.CameraKitView;
import com.example.atry.detectapp.Helper.GraphicOverlay;
import com.example.atry.detectapp.Helper.RectOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class SampleActivity extends AppCompatActivity {



    CameraView cameraView;
    GraphicOverlay graphicOverlay;
    Button btnDetect;
   AlertDialog alertDialog;
    private CameraKitView cameraKitView;

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {

        super.onPause();
        cameraView.stop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //Init view
        //cameraKitView = findViewById(R.id.camera);

        cameraView=findViewById(R.id.camera_view);
        btnDetect=findViewById(R.id.btn_detect);
        graphicOverlay=findViewById(R.id.graphic_overlay);
        alertDialog=new SpotsDialog.Builder().setContext(this)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();





        //event
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
                graphicOverlay.clear();
            }
        });

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                    alertDialog.show();
                Bitmap bitmap=cameraKitImage.getBitmap();
                cameraView.stop();

                runFaceDetector(bitmap);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

    }

    private void runFaceDetector(Bitmap bitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetectorOptions realTimeOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .build();

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(realTimeOpts);

        detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        processFaceResult(firebaseVisionFaces);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SampleActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void processFaceResult(List<FirebaseVisionFace> firebaseVisionFaces) {

        int count=0;

        for(FirebaseVisionFace face : firebaseVisionFaces)
        {
            Rect bounds=face.getBoundingBox();
            //draw rectangle
            RectOverlay rectOverlay=new RectOverlay(graphicOverlay,bounds);
            graphicOverlay.add(rectOverlay);
            count++;
        }
        alertDialog.dismiss();
        Toast.makeText(this,String.format("Detected %d faces in image ",count),Toast.LENGTH_LONG).show();

    }


}
