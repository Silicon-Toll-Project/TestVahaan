#!usr/bin/env python3

import paho.mqtt.client as mqtt

# This is the Publisher

client = mqtt.Client()
client.connect("localhost",1883,60)
client.publish("topic/SharmisthaSahoo@github", "Hello MQTT");
client.disconnect();

def on_connect(client, userdata, flags, rc):
	print("Connected with result code" + str(rc))
def on_message(client, userdata, msg):
	print(msg.topic + " " + str(msg.payload))
count = 0

client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message
##client.username_pw_set(USERNAME, PASSWORD)
##client.connect(MQTTHOST, 1883)

while client.loop() == 0:
	msg = time.ctime()
	client.publish(topicStr, msg, 0, False)
	print("message published : " + str(msg))
	time.sleep(3)
	count = count + 1
	pass