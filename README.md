# GuteFind Android Application

GuteFind Proof of Concept Android application  
The application is based on the "BLE Indoor Positioning" library.  
<https://github.com/neXenio/BLE-Indoor-Positioning>

## Getting Started

1. Install the Application on your device
2. Grant permissions for the application
3. Configure Settings

## Grant Permissions

In order for the application to detect BLE signals from the surrounding beacons, you need to follow the steps below:

- Install the application on your mobile device.
- Press and hold the application icon until a popup menu appears.
- Select "App info" from the menu to access the application settings.
- In the "App info" screen, tap on the "Permissions" menu.
- Under the "Not Allowed" permissions, select:
  - "Location" and choose "All the time".
  - "Nearby devices" and choose "Allow".

Following these steps will ensure that the application can receive BLE signals from the surrounding beacons, which is necessary for its proper functioning.

## Configuring Beacon Positions

It is recommended to place the four beacons in the corners of the room to ensure optimal functionality of the application. Providing the latitude and longitude positions of the beacons in the settings screen will provide the necessary context for the application to function effectively.

To use the Android application and set up the beacons, please follow these steps:

- Open the GuteFind mobile application.
- Access the settings screen by clicking on the three dots in the tab bar.
- Enter the exact absolute latitude and longitude positions for each of the four beacons, with 6 digits precision.
- Click on the "Save" button at the bottom of the screen.
