package org.dragons.Dragons.scoutingapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by Stephen Ogden on 5/29/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

public class transmit extends AppCompatActivity {

    // To start, well need a stream to send the data over, so create an OutputStream
    public static OutputStream outStream;

    public void AlertBox(@SuppressWarnings("SameParameterValue") String title, String message ) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(message + " Press OK to exit." ).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        }).show();
    }


    public void sendData() {
        try {

            // Using that OutputStream defined before, set it to the bluetooth's outputstream
            outStream = settings.btSocket.getOutputStream();

            // Write that data to the data stream, dont have an offset, and set the length to that of the length of the data
            outStream.write(main_activity.data.getBytes(), 0, main_activity.data.getBytes().length);

            // Be sure to flush the stream, that way everything gets output to the reciever, and then it can parse the data from there
            outStream.flush();

            // If the phone used for testing is hooked up to a debugger, we can view the data being sent, and the bytes sent, by printing it to the log
            Log.e("outString", main_activity.data);
            Log.e("outBytes", Arrays.toString(main_activity.data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

            // There was an error during write? FUCK! Let the user know by creating an alert box with the error message, and then showing that for 3 seconds
            String msg = "An exception occurred during write: " + e.getMessage();
            msg = msg + ".\n\nCheck that the SPP UUID exists on server.\n\n";
            AlertBox("Error", msg);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        // Set the data to be null at this point, to reset the string
        main_activity.data = null;

        // Let the thread do anything else it has been waiting to do
        Thread.yield();

        try {
            // Attempt to close the string
            outStream.close();
        } catch (IOException e) {
            // If unsuccessful, show the error via toast
            Toast.makeText(transmit.this, "Error closing stream!", Toast.LENGTH_LONG).show();
        }
    }

}
