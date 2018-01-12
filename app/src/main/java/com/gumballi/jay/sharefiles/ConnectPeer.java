package com.gumballi.jay.sharefiles;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jay on 3/1/18.
 */

public class ConnectPeer {

    public static void connect(String deviceAddress, final WifiP2pManager manager, final WifiP2pManager.Channel channel, final Context context, WifiP2pManager.ActionListener listener){
        final WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = deviceAddress;
        config.groupOwnerIntent=0;

        Log.d("Connect","initiated");
        manager.connect(channel, config, listener);

    }

}
