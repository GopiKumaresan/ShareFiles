package com.gumballi.jay.sharefiles;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 2/1/18.
 */

public class PeersAdapter extends RecyclerView.Adapter<PeersViewHolder> {

    List<WifiP2pDevice> peersList;
    Context context;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    WifiP2pManager.ActionListener listener;
    Activity activity;
    WifiP2pManager.ConnectionInfoListener infoListener;

    public PeersAdapter(List<WifiP2pDevice> peersList, final Context context, final WifiP2pManager manager, final WifiP2pManager.Channel channel, final Activity activity, final WifiP2pManager.ConnectionInfoListener infoListener){
        this.peersList=peersList;
        this.context=context;
        this.manager=manager;
        this.channel=channel;
        this.activity=activity;
        this.infoListener=infoListener;
        listener=new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context,"Connected!",Toast.LENGTH_LONG).show();
                //manager.requestConnectionInfo(channel,infoListener);
                //ChooseFile.fileChooser(activity);
                //Log.d("ConnectPeer ","Success");
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show();
                Log.d("ConnectPeer ","Fail");
            }
        };
    }

    @Override
    public PeersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.peers_list_item,parent,false);

        PeersViewHolder viewHolder=new PeersViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PeersViewHolder holder, int position) {
        Log.d("onBind","test");
        holder.setPeer(peersList.get(position));
        try {
            final String deviceAddress = holder.device.deviceAddress;

            holder.peerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectPeer.connect(deviceAddress,manager,channel,context,listener);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return peersList.size();
    }

    public void updateList(List<WifiP2pDevice> devices){
        peersList=devices;
        Log.d("Adapter ","wololoooo");
        Log.d("Adapter ",String.valueOf(peersList.size()));
    }
}
