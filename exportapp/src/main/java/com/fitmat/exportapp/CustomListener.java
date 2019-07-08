package com.fitmat.exportapp;

import android.os.Handler;
import android.util.Log;

import java.util.Arrays;

public class CustomListener {
    Handler h;
    public void checkHandeler(){
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {

                }
            };
        };
    }
}
