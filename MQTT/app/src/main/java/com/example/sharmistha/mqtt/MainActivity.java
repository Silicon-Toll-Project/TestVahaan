package com.example.sharmistha.mqtt;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    //Connection with broker server(sango)
    static String MQTTHOST = "tcp://lite.mqtt.shiguredo.jp:1883";
    static String USERNAME = "SharmisthaSahoo@github";
    static String PASSWORD = "50jsTAlZlqqvBO6V";
    String topicStr = "SharmisthaSahoo@github/#";

    MqttAndroidClient client;

    TextView subText;

    MqttConnectOptions options;

    Vibrator vibrator;

    Ringtone myRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subText = (TextView)findViewById(R.id.subtext);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);//Vibration when message arrives

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Ringtone when message arrives
        myRingtone = RingtoneManager.getRingtone(getApplicationContext(), uri);

        String clientId = MqttClient.generateClientId(); //Random user id created
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId); //an instance of an Android MQTT client created that will bind to the Paho Android Service

        options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());


        try
        {
            IMqttToken token = client.connect(options);//client will asynchronously try to connect to the MQTT broker and return a token.
            token.setActionCallback(new IMqttActionListener()
            {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "CONNECTED", Toast.LENGTH_LONG).show();
                    setSubscription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "CONNECTION FAILED", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(MqttException e)
        {
            e.printStackTrace();
        }

        //Callback method
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                subText.setText(new String(message.getPayload()));

                vibrator.vibrate(500);

                myRingtone.play();

            }


            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }


    //The MqttAndroidClient allows messages to be published via its publish(topic, MqttMessage) method.
    public void pub(View v)
    {
        String topic = topicStr;
        String message = "Hello MQTT";

        try
        {
          //  encodedPayload = payload.getBytes("UTF-8");
          //  MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message.getBytes(), 0, false );
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }

    //Subscription method
    private void setSubscription()
    {
        try
        {
            client.subscribe(topicStr, 0);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }

    //For connect button
    public void conn(View v){
        try
        {
            IMqttToken token = client.connect(options);//client will asynchronously try to connect to the MQTT broker and return a token.
            token.setActionCallback(new IMqttActionListener()
            {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "CONNECTED", Toast.LENGTH_LONG).show();
                    setSubscription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "CONNECTION FAILED", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(MqttException e)
        {
            e.printStackTrace();
        }
    }

    //For disconnect button
    public void disconn(View v){
        try
        {
            IMqttToken token = client.disconnect();//client will asynchronously try to connect to the MQTT broker and return a token.
            token.setActionCallback(new IMqttActionListener()
            {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "DISCONNECTED", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "COULD NOT DISCONNECT", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(MqttException e)
        {
            e.printStackTrace();
        }
    }



}
