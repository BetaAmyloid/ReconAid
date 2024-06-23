package com.example.reconaiddev;

import static android.os.Looper.getMainLooper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.OutputStream;
import java.nio.ByteOrder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Button markAsSafe;
    private Button requestRescue;

    private Button reliefGoods;
    private Button evacuation;
    private Button trapped;

    private String esp32IpAddress = "192.168.254.179"; // Replace with your ESP32 IP address
    private int esp32Port = 80;

    private String phoneNumber = "+639663801375";

    private String Name = "Kim David Clamor";

    private String rescueType;

    private String command;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected double latitude = 0.0;
    protected double longitude = 0.0;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //hidden buttons

        markAsSafe = view.findViewById(R.id.markAsSafeButton);
        requestRescue = view.findViewById(R.id.requestRescue);
        reliefGoods = view.findViewById(R.id.reliefGoods);
        evacuation = view.findViewById(R.id.evacuation);
        trapped = view.findViewById(R.id.trapped);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (!isGranted) {
                        Toast.makeText(view.getContext(), "Please allow the permissions", Toast.LENGTH_LONG).show();
                    }
                });

        //hidden buttons show


        markAsSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Marked as safe", Toast.LENGTH_SHORT).show();
            }
        });

        requestRescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleVisibility(reliefGoods);
                toggleVisibility(evacuation);
                toggleVisibility(trapped);
            }
        });

        reliefGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rescueType = "reliefGoods";
                command = "verifyAndRequest";

                if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    getLocation(view);
                }
            }
        });

        evacuation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                rescueType = "evacuation";
                command = "verifyAndRequest";

                if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    getLocation(view);
                }
            }
        });

        trapped.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                rescueType = "trapped";
                command = "verifyAndRequest";

                if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    getLocation(view);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void sendSMS() {

        String message = "latitude: " + latitude + ", longitude: " + longitude;
        sendDataToESP32(); //also send data to esp32

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getContext(), "SMS sent.", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getLocation(View view) {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("Latitude", String.valueOf(latitude));
                    Log.d("Longitude", String.valueOf(longitude));
                    sendSMS(); // Send SMS after location is updated
                    //stop sending msgs after first location is sent
                    fusedLocationClient.removeLocationUpdates(this);

                }
            }
        }, getMainLooper());
    }
    //send to esp32
    private void sendDataToESP32() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(esp32IpAddress, esp32Port);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write((command + "\n").getBytes());
                    outputStream.write((phoneNumber + "\n").getBytes());
                    outputStream.write((Name + "\n").getBytes());
                    outputStream.write((rescueType + "\n").getBytes());
                    outputStream.write(doubleToBytes(longitude));
                    outputStream.write(doubleToBytes(latitude));
                    outputStream.flush();

                    BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(socket.getInputStream())));
                    String response = bufferedReader.readLine();
                    socket.close();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("Data already exists".equals(response)) {
                                showAlertDialog();
                            } else {
                                Toast.makeText(getContext(), "Data sent successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void toggleVisibility(Button button) {
        if (button.getVisibility() == View.VISIBLE) {
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
        }
    }

    private byte[] doubleToBytes(double value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putDouble(value);
        return buffer.array();
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Data Already Exists")
                .setMessage("You have already requested rescue. Do you want to update and your information?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        command = "updateDatabase";
                        sendDataToESP32();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
