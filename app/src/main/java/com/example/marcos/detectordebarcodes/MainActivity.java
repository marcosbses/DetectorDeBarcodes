package com.example.marcos.detectordebarcodes;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private CameraSource cameraSource;
    private SurfaceView surfaceView;
    private boolean surfaceAvailable;
    private boolean cameraStartRequested;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraStartRequested = false;
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);



    }

    @Override
    protected void onResume(){
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            iniciarCamara();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 22);
        }
    }

    private void crearCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE|Barcode.UPC_A).build();
        Tracker<Barcode> barcodeTracker=new QrBarcodeTracker(getApplicationContext());
        FirstBarcodeFocusingProcessor firstBarcodeFocusingProcessor=new FirstBarcodeFocusingProcessor(barcodeDetector,barcodeTracker);
        barcodeDetector.setProcessor(firstBarcodeFocusingProcessor);
        CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).build();
        this.cameraSource = cameraSource;

    }

    private void iniciarCamara() {
        Log.i("infor","iniciar camara");
        cameraStartRequested = true;
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (cameraSource == null) {
                crearCameraSource();
            }
            if (surfaceAvailable) {
                Log.i("infor","hay superficie");
                cameraSource.start(surfaceView.getHolder());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 22: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    iniciarCamara();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("infor","surface created");
        surfaceAvailable = true;
        iniciarCamara();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        surfaceAvailable=false;
    }

    @Override
    protected void onStop(){
        super.onStop();
        cameraSource.stop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        cameraSource.release();
    }

}
