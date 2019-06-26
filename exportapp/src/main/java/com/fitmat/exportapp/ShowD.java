package com.fitmat.exportapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fitmat.exportapp.R;

public class ShowD extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showd);
    }


    public void showDialogue(Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(
              context  ).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert Dialog");

        // Setting Dialog Message
        alertDialog.setMessage("Welcome to AndroidHive.info");

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.tick);

//        // Setting OK Button
//        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // Write your code here to execute after dialog closed
//                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
//            }
//        });

        // Showing Alert Message
        alertDialog.show();
    }
}
