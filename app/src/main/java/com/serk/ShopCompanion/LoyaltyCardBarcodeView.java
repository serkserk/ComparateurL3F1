package com.serk.ShopCompanion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Codabar;
import com.onbarcode.barcode.android.IBarcode;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe s'occupe d'afficher le code barre lors d’un appui sur une carte de fidélité grâce à la librairie OnBarcode
 */

public class LoyaltyCardBarcodeView extends View {

    String code;

    public LoyaltyCardBarcodeView(Context context, String code) {
        super(context);
        this.code = code;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            CODABAR(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CODABAR(Canvas canvas) throws Exception {

        Codabar barcode = new Codabar();
        // barcode data to encode
        barcode.setData(code);
        // Codabar Start & Stop Char, Valid values are 'A', 'B', 'C', 'D'
        barcode.setStartChar('A');
        barcode.setStopChar('A');
        // Unit of Measure, pixel, cm, or inch
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode bar module width (X) in pixel
        barcode.setX(7f);
        // barcode bar module height (Y) in pixel
        barcode.setY(400f);
        // barcode image margins
        barcode.setLeftMargin(200F);
        barcode.setRightMargin(200f);
        barcode.setTopMargin(600f);
        barcode.setBottomMargin(0f);
        // barcode image resolution in dpi
        barcode.setResolution(72);
        // disply barcode encoding data below the barcode
        barcode.setShowText(true);
        // barcode encoding data font style
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 48));
        // space between barcode and barcode encoding data
        barcode.setTextMargin(6);
        barcode.setTextColor(AndroidColor.black);
        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);
        /*
          specify your barcode drawing area
        */
        RectF bounds = new RectF(30, 30, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }
}
