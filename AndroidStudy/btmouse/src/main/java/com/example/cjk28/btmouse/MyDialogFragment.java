package com.example.cjk28.btmouse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.Set;

/**
 * Created by cjk28 on 2016-11-09.
 */

public class MyDialogFragment extends DialogFragment {
    private final static int DEVICES_DIALOG = 1;
    private final static int ERROR_DIALOG = 2;

    public MyDialogFragment(){ }

    public static MyDialogFragment newInstance(int id, String text){
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString("content",text);
        args.putInt("id", id);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String content = getArguments().getString("contant");
        int id = getArguments().getInt("id");
        AlertDialog.Builder alertDialogBuilder = null;

        switch (id){
            case DEVICES_DIALOG:
                alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Select devices");

                Set<BluetoothDevice> pairedDevices = MainActivity.getPairedDevices();
                final BluetoothDevice[] devices = pairedDevices.toArray(new BluetoothDevice[0]);
                String[] items = new String[devices.length];
                for(int i = 0; i < devices.length; i++){
                    items[i] = devices[i].getName();
                }

                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((MainActivity)MainActivity.mContext).doConnect(devices[which]);
                    }
                });
                alertDialogBuilder.setCancelable(false);
                break;
            case ERROR_DIALOG:
                alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("ERROR");
                alertDialogBuilder.setMessage(content);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((MainActivity)MainActivity.mContext).finish();
                    }
                });
                break;
            /*       alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            getActivity().finish();
            }
             });
             */
        }
        return super.onCreateDialog(savedInstanceState);
    }
}