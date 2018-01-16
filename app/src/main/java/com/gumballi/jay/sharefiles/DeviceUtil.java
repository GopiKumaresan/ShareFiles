package com.gumballi.jay.sharefiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jay on 16/1/18.
 */

public class DeviceUtil {
    public static void changeDeviceName(Context context, final WifiP2pManager manager, final WifiP2pManager.Channel channel,View root){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(R.string.alert_dialog_title);
        View view=View.inflate(context,R.layout.alert_dialog,(ViewGroup) root);
        builder.setView(view);
        builder.setNegativeButton("Cancel", null);
        final EditText editText=view.findViewById(R.id.change_name_edittext);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName=editText.getText().toString();
                if(newName.isEmpty()) return;
                Log.d("DeviceUtil",(newName.isEmpty())+"");
                try {
                    callHiddenMethod(manager,channel,newName);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.create().show();
    }

    private static void callHiddenMethod(WifiP2pManager manager, WifiP2pManager.Channel channel, String newName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class[] paramTypes = new Class[3];
        paramTypes[0] = WifiP2pManager.Channel.class;
        paramTypes[1] = String.class;
        paramTypes[2] = WifiP2pManager.ActionListener.class;
        Method setDeviceName = manager.getClass().getMethod(
                "setDeviceName", paramTypes);
        setDeviceName.setAccessible(true);

        Object arglist[] = new Object[3];
        arglist[0] = channel;
        arglist[1] = newName;
        arglist[2] = new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d("setDeviceName succeeded", "true");
            }

            @Override
            public void onFailure(int reason) {
                Log.d("setDeviceName failed", "true");
            }
        };
        setDeviceName.invoke(manager, arglist);
    }
}
