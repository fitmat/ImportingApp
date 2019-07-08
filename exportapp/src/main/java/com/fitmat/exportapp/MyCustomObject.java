package com.fitmat.exportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyCustomObject extends AppCompatActivity {

    Handler h;

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private static int flag = 0;
    private static final String TAG = "bluetooth2";


    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_object);
    }

    public interface MyCustomObjectListener {
        public void onObjectReady(String title);
        public void onDataLoaded(String data);
    }

    // Member variable was defined earlier
    private MyCustomObjectListener listener;

    // Constructor where listener events are ignored
    public MyCustomObject(String macid) {
        // set null or default listener or accept as argument to constructor
        Log.d("MAC ID",macid);
        this.address = macid;
        this.listener = null;
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
        connectBluetooth();
        loadDataAsync();

    }

    public void loadDataAsync() {
        //      AsyncHttpClient client = new AsyncHttpClient();
//        client.get("https://mycustomapi.com/data/get.json", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) throws JSONException {
//                // Networking is finished loading data, data is processed
//            //    SomeData data = SomeData.processData(response.get("data"));
//                // Do some other stuff as needed....
//                // Now let's trigger the event
//                if (listener != null)
//                    listener.onDataLoaded(data); // <---- fire listener here
//            }
//        });
//
//



//
//        for (int i=0;i<10;i++){
//
//
//
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    //do something
//
//                    if (listener != null)
//                        listener.onDataLoaded("Received"); // <---- fire listener here
//                }
//            }, 5000 );//time in milisecond
//
//        }


        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:

                        byte[] readBuf = (byte[]) msg.obj;
                        //Log.i("BYTES  ", Arrays.toString(readBuf));
                        String parsedByteString = bytesToHex(readBuf);
                        String[] bytes = splitEqually(parsedByteString, 2);
                        Log.i("BYTES  ", Arrays.toString(bytes));
                        if (listener != null)
                            listener.onDataLoaded( Arrays.toString(bytes));
                        //---------------------------------
                        //   Log.i("Response", DriverControl.sendDataBuffer(readBuf));

                        break;
                }
            };
        };


    }

    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }
    public void connectBluetooth(){
        Log.e("Address",address);

        Log.d(TAG, "...onResume - try connect..."+address);

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }
    @Override
    public void onResume() {
        super.onResume();


    }

    public void closeConnection(){
        Log.d(TAG, "...In onPause()...");
        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }
    @Override
    public void onPause() {
        super.onPause();




    }


    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }
    public static String[] splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        String[] ret = new String[((text.length() + size - 1) / size)];
        int i =0;
        for (int start = 0; start < text.length(); start += size) {
            ret[i] = (text.substring(start, Math.min(text.length(), start + size)));
            i++;
        }
        return ret;
    }

    public static String bytesToHex(byte[] bytes) {
        //Log.i("BYTE LENGTH:", String.valueOf(bytes.length));
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            //hexChars[j * 2 + 2] = ' ';
        }
        return new String(hexChars);
    }


    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()


            StringBuilder readMessage = new StringBuilder();
            while (true) {
                try {


                    buffer = new byte[102];

                    bytes = mmInStream.available();
                    String dataRec = "";
                    int offset = 0;
                    while(buffer.length-offset != 0)
                    {
                        bytes = mmInStream.read(buffer, offset, buffer.length-offset);
                        dataRec = bytesToHex(buffer);
                        if(dataRec.charAt(0) == 'A' && dataRec.charAt(1) == '5' && dataRec.charAt(2) == 'A' && dataRec.charAt(3) == '5'){

                            offset += bytes;
                        }
                        else {

                            continue;
                        }

                    }

                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();


                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }


}
