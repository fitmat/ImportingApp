package com.fitmat.importingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitmat.exportapp.HelloWorld;
import com.fitmat.exportapp.MyCustomObject;
import com.fitmat.exportapp.ShowD;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1010;
    String address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ShowD s = new ShowD();
//                s.showDialogue(MainActivity.this);

                Intent intent   =  new Intent(MainActivity.this, HelloWorld.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(this,"REACHED IN MAIN Activity",Toast.LENGTH_SHORT).show();

        if(requestCode == REQUEST_CODE)
        {
            if(data==null)
                return;

            //Getting the passed result
            address = data.getStringExtra("QR-RESULT");

            Log.e("In activityResult",address);

            Toast.makeText(this,address,Toast.LENGTH_SHORT).show();


        }

        MyCustomObject object = new MyCustomObject(address);
        // Step 4 - Setup the listener for this object
        object.setCustomObjectListener(new MyCustomObject.MyCustomObjectListener() {
            @Override
            public void onObjectReady(String title) {

            }

            @Override
            public void onDataLoaded(String data) {

            }


        });



    }
}