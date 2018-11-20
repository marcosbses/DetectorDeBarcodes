package com.example.marcos.detectordebarcodes;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by marcos on 11/6/18.
 */

public class QrBarcodeTracker extends Tracker<Barcode> {
    private Context context;
    public QrBarcodeTracker(Context context){
        super();
        this.context=context;
    }
    @Override
    public void onNewItem(int id, final Barcode barcode){
        Log.i("infor","on new item");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,barcode.displayValue,Toast.LENGTH_LONG).show();
            }
        });


    }
}
