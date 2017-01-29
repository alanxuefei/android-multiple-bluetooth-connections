android-multiple-bluetooth-connections
======================================
Android phone CAN communicate with multiply bluetooth devices at same time using Bluetooth Low Engery or Bluetooth Classic.
**Bluetooth Classic**
This code is designed to communicate with three bluetooth Classic devices using three services. MyService1.java and MyService2.java are related to the three bluetooth devices. The MAC address in the three files should be modified accordingly. MyService0.java is a dummy service, which make the demo working. The origrinal code is designed to show the reading from multiple airflow sensors. In my experiment, the three Bluetooth Classic connections can work but not stable. As Bluetooth Classic evolves, this code may not be the best solution. However,  

**Bluetooth Low Engery** 
Please refer to BluetoothLeGatt from Offical Android. After reading the tutorial, the multiple BLE connections can be achieved by storing all the BLE connections. 
I achieved the multiply BLE connections using Andorid phone and Bluefruit LE Micro Arduino Boards (Adafruit website). The codes are not inclued in this repository. I will upload it once I have time. :) so busy right now.
