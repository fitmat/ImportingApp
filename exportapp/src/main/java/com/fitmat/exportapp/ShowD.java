package com.fitmat.exportapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.fitmat.exportapp.R;

public class ShowD extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showd);
    }


    public void showDialogue(Context context) {
//        AlertDialog alertDialog = new AlertDialog.Builder(
//              context  ).create();
//
//        // Setting Dialog Title
//        alertDialog.setTitle("Alert Dialog");
//
//        // Setting Dialog Message
//        alertDialog.setMessage("Welcome to AndroidHive.info");
//
//        // Setting Icon to Dialog
////        alertDialog.setIcon(R.drawable.tick);
//
////        // Setting OK Button
////        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int which) {
////                // Write your code here to execute after dialog closed
////                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        // Showing Alert Message
//        alertDialog.show();
//    }


        /*********************Working Code************************/
       /*
       AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

        /******************************************************************/

        Intent i = new Intent(context, QrCodeActivity.class);
        startActivityForResult( i,REQUEST_CODE_QR_SCAN);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            Log.d("MSG","COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d("MSG","Have scan result in your app activity :"+ result);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
    }
}
