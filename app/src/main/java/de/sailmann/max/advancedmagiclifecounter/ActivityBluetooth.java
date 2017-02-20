package de.sailmann.max.advancedmagiclifecounter;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;

/*Copyright 2008 Google Inc.
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
*/

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Created by Admin on 16.01.2017.
 */

public class ActivityBluetooth extends AppCompatActivity implements View.OnClickListener {


    private Statistic statistic;
    private ListView listViewGames;
    ArrayList<String> json;

    private TextView tvSelectedDevice;
    private Button bConnect;
    private Button bShare;

    private Dialog dialog;
    private BluetoothAdapter bluetoothAdapter;

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_RECEIVE = 2;
    public static final int MESSAGE_SEND = 3;
    public static final int MESSAGE_DEVICE_OBJECT = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String DEVICE_OBJECT = "device_name";

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final int REQUEST_DISCOVER_BLUETOOTH = 2;
    private MyBluetoothController myBluetoothController;
    private BluetoothDevice selectedDevice;
    private ArrayAdapter<String> discoveredDevicesAdapter;
    private ArrayAdapter<String> pairedDevicesAdapter;

    private IntentFilter filter;
    private Gson gson;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutbluetooth);

        init();
        initializeListView();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, getResources().getString(R.string.canNotContiniueWOBluetooth), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void init() {

        gson = new Gson();

        statistic = ((Globals) this.getApplication()).getStatistic();
        findViewById(R.id.b_lbluetooth_share).setOnClickListener(this);

        tvSelectedDevice = (TextView) findViewById(R.id.tv_lbluetooth_selecteddevice);

        bConnect = (Button) findViewById(R.id.b_lbluetooth_connect);
        bConnect.setOnClickListener(this);
        bConnect.setTypeface(((Globals) this.getApplication()).getTypeface());

        bShare = (Button) findViewById(R.id.b_lbluetooth_share);
        bShare.setOnClickListener(this);
        bShare.setTypeface(((Globals) this.getApplication()).getTypeface());


        tvSelectedDevice.setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lbluetooth_selectgames)).setTypeface(((Globals) this.getApplication()).getTypeface());

    }

    public void initializeListView() {

        if (statistic.getGames().size() > 0) {
            listViewGames = (ListView) findViewById(R.id.lv_lbluetooth_games);
            MyAdapterBluetooth myAdapter = new MyAdapterBluetooth(this, statistic.getGames(), ((Globals) this.getApplication()).getTypeface());
            listViewGames.setAdapter(myAdapter);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.b_lbluetooth_share) {

            for (int i = 0; i < listViewGames.getCount(); i++) {

                CheckBox checkbox = (CheckBox) listViewGames.getChildAt(i).findViewById(R.id.cb_flistviewbluetooth_check);

                if (checkbox.isChecked()) {

                    Game game = statistic.getGames().get(statistic.getNumberOfGames() - i - 1);
                    json.add(gson.toJson(game));
                }
            }

            if (json.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.pleaseSelectGames), Toast.LENGTH_SHORT).show();
            } else {

                for (String s : json) {
                    Log.d(this.getClass().getSimpleName(), s);
                    sendMessage(s);
                }
            }
        }
        if (v.getId() == R.id.b_lbluetooth_connect) {
            showDialogToSelectDevice();
        }

    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case MyBluetoothController.STATE_CONNECTED:
                            setStatus(getResources().getString(R.string.connectedTo) + selectedDevice.getName());
                            findViewById(R.id.b_lbluetooth_connect).setEnabled(false);
                            break;
                        case MyBluetoothController.STATE_CONNECTING:
                            setStatus(getResources().getString(R.string.connecting));
                            findViewById(R.id.b_lbluetooth_connect).setEnabled(false);
                            break;
                        case MyBluetoothController.STATE_LISTEN:
                        case MyBluetoothController.STATE_NONE:
                            setStatus(getResources().getString(R.string.notConnected));
                            break;
                    }
                    break;
                case MESSAGE_SEND:
                    byte[] writeBuf = (byte[]) msg.obj;

                    String writeMessage = new String(writeBuf);

                    break;
                case MESSAGE_RECEIVE:
                    byte[] readBuf = (byte[]) msg.obj;

                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Game game = gson.fromJson(readMessage, Game.class);
                    statistic.add(game);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.addGameToStatistic), Toast.LENGTH_SHORT);
                    break;
                case MESSAGE_DEVICE_OBJECT:
                    selectedDevice = msg.getData().getParcelable(DEVICE_OBJECT);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.connectedTo) + selectedDevice.getName(),
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString("toast"),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    private void setStatus(String s) {
        tvSelectedDevice.setText(s);
    }


    private void showDialogToSelectDevice() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_bluetooth);
        dialog.setTitle(getResources().getString(R.string.availableBluetoothDevices));

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();


        pairedDevicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        discoveredDevicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);


        ListView listViewPaired = (ListView) dialog.findViewById(R.id.lv_fbluetooth_paired);
        ListView listViewDiscovered = (ListView) dialog.findViewById(R.id.lv_fbluetooth_discovered);

        listViewPaired.setAdapter(pairedDevicesAdapter);
        listViewDiscovered.setAdapter(discoveredDevicesAdapter);

        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(discoveryFinishReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(discoveryFinishReceiver, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            pairedDevicesAdapter.add(getResources().getString(R.string.noPairedDevicesAvailable));
        }

        listViewDiscovered.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bluetoothAdapter.cancelDiscovery();
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                connectToDevice(address);
                dialog.dismiss();
            }
        });

        listViewPaired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bluetoothAdapter.cancelDiscovery();
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                connectToDevice(address);
                dialog.dismiss();
            }
        });
        Button button = (Button) dialog.findViewById(R.id.b_fbluetooth_cancel);
        button.setTypeface(((Globals) this.getApplication()).getTypeface());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void connectToDevice(String deviceAddress) {
        bluetoothAdapter.cancelDiscovery();
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
        Log.d(this.getClass().getSimpleName(), deviceAddress);
        myBluetoothController.connect(device);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BLUETOOTH:
                if (resultCode == Activity.RESULT_OK) {
                    myBluetoothController = new MyBluetoothController(this, handler);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.canNotContiniueWOBluetooth), Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case REQUEST_DISCOVER_BLUETOOTH:
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, getResources().getString(R.string.yourDeviceMayNotBeFound), Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void sendMessage(String message) {
        if (myBluetoothController.getState() != MyBluetoothController.STATE_CONNECTED) {
            Toast.makeText(this, getResources().getString(R.string.noConnection), Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.length() > 0) {
            byte[] send = message.getBytes();
            myBluetoothController.write(send);
        }
    }

    private void sendMessage(Game game) throws IOException {


        if (myBluetoothController.getState() != MyBluetoothController.STATE_CONNECTED) {
            Toast.makeText(this, getResources().getString(R.string.noConnection), Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(game);
        byte[] send = out.toByteArray();

        myBluetoothController.write(send);
    }


    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        if (data != null) {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        }
        return null;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);


            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableIntent, REQUEST_DISCOVER_BLUETOOTH);

        } else {
            myBluetoothController = new MyBluetoothController(this, handler);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (myBluetoothController != null) {
            if (myBluetoothController.getState() == MyBluetoothController.STATE_NONE) {
                myBluetoothController.start();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (filter != null) {
            unregisterReceiver(discoveryFinishReceiver);
        }

        if (myBluetoothController != null)
            myBluetoothController.stop();
    }

    private final BroadcastReceiver discoveryFinishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    discoveredDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (discoveredDevicesAdapter.getCount() == 0) {
                    discoveredDevicesAdapter.add(getResources().getString(R.string.noDevicesFound));
                }
            }
        }
    };
}
