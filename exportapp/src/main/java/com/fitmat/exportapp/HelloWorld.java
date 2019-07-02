package com.fitmat.exportapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;

import pub.devrel.easypermissions.EasyPermissions;

public class HelloWorld extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        Toast.makeText(HelloWorld.this,"Resultstarted",Toast.LENGTH_SHORT).show();
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//            // Forward results to EasyPermissions
//            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//        }

        Intent i = new Intent(HelloWorld.this, QrCodeActivity.class);
        startActivityForResult( i,REQUEST_CODE_QR_SCAN);
    }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            Toast.makeText(HelloWorld.this,"ResultFetched",Toast.LENGTH_SHORT).show();

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

            this.finish();

//            Intent i = new Intent(HelloWorld.this, MainActivity.class);



        }
    }

}
