package com.example.arm_remote_controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    private MqttAndroidClient mqttAndroidClient;

    private Button btn11, btn21, btn31, btn41;
    private Button btn12, btn22, btn32, btn42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn11 = (Button)findViewById(R.id.btn11);
        btn21 = (Button)findViewById(R.id.btn21);
        btn31 = (Button)findViewById(R.id.btn31);
        btn41 = (Button)findViewById(R.id.btn41);

        btn12 = (Button)findViewById(R.id.btn12);
        btn22 = (Button)findViewById(R.id.btn22);
        btn32 = (Button)findViewById(R.id.btn32);
        btn42 = (Button)findViewById(R.id.btn42);


        mqttAndroidClient = new MqttAndroidClient(this, "tcp://" + "192.168.12.100" + ":1883", MqttClient.generateClientId());
        //"tcp://" + "113.198.48.4" + ":80", MqttClient.generateClientId());

        // 2번째 파라메터 : 브로커의 ip 주소 , 3번째 파라메터 : client 의 id를 지정함 여기서는 paho 의 자동으로 id를 만들어주는것

        try {
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
                    Log.e("Connect_success", "Success");
                    Toast.makeText(getApplicationContext(), "CONNECT SUCCESS", Toast.LENGTH_LONG).show();


                    try {

                        mqttAndroidClient.subscribe("android", 0);   //연결에 성공하면 kitae 라는 토픽으로 subscribe함
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
                    Log.e("connect_fail", "Failure " + exception.toString());
                    Toast.makeText(getApplicationContext(), "CONNECT FAIL", Toast.LENGTH_LONG).show();

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }


        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn11");
                    mqttAndroidClient.publish("arm", "btn11".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });
        btn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn21");
                    mqttAndroidClient.publish("arm", "btn21".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });
        btn31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn31");
                    mqttAndroidClient.publish("arm", "btn31".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });
        btn41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn41");
                    mqttAndroidClient.publish("arm", "btn41".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });

        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn12");
                    mqttAndroidClient.publish("arm", "btn12".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });
        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn22");
                    mqttAndroidClient.publish("arm", "btn22".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });
        btn32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn32");
                    mqttAndroidClient.publish("arm", "btn32".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });
        btn42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("button_press", "btn42");
                    mqttAndroidClient.publish("arm", "btn42".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }}
        });


        mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {   //모든 메시지가 올때 Callback method
                if (topic.equals("drive")) {     //topic 별로 분기처리하여 작업을 수행할수도있음
                    String msg = new String(message.getPayload());
                    Log.e("arrive message : ", msg);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private DisconnectedBufferOptions getDisconnectedBufferOptions() {

        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(100);
        disconnectedBufferOptions.setPersistBuffer(true);
        disconnectedBufferOptions.setDeleteOldestMessages(false);
        return disconnectedBufferOptions;
    }

    private MqttConnectOptions getMqttConnectionOption() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setWill("aaa", "I am going offline".getBytes(), 1, true);
        return mqttConnectOptions;
    }
}