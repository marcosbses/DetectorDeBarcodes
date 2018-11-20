package com.example.marcos.detectordebarcodes;

import android.util.Log;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.FocusingProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by marcos on 11/6/18.
 */

public class FirstBarcodeFocusingProcessor extends FocusingProcessor<Barcode> {


    public FirstBarcodeFocusingProcessor(Detector<Barcode> detector, Tracker<Barcode> tracker) {
        super(detector, tracker);
    }

    @Override
    public int selectFocus(Detector.Detections<Barcode> detections) {
        Log.i("infor","select focus");
        return detections.getDetectedItems().keyAt(0);
    }
}
