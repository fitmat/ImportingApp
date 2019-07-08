package com.fitmat.exportapp;

import androidx.annotation.MainThread;
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


    public static String address="";

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        HelloWorld.address = address;
    }






    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        Toast.makeText(HelloWorld.this,"Resultstarted",Toast.LENGTH_SHORT).show();

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
//                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                alertDialog.setTitle("Scan Error");
//                alertDialog.setMessage("QR Code could not be scanned");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
////
////                Intent i = new Intent(this, com.fitmat.importingapp.MainActivity.class);
////                String strName = null;
////                i.putExtra("STRING_I_NEED", strName);
//
//                alertDialog.show();
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
            Intent resultIntent = new Intent();
            resultIntent.putExtra("QR-RESULT", result);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();



            this.finish();





        }
    }

}
