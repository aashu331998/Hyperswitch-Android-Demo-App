# Hyperswitch-Android-Demo-App

A simple Android app to demo the new hyperswitch android library

## Running the sample App

1. Build the application

> Note: You can checkout the live demo app on google play store here. https://play.google.com/store/apps/details?id=io.hyperswitch.hyperecom

2. In `server` folder 
Add the `.env` with the following keys

```
HYPERSWITCH_PUBLISHABLE_KEY=""
HYPERSWITCH_SECRET_KEY=""
PROFILE_ID=""
```

You can create your keys using the Hyperswitch dashboard. https://app.hyperswitch.io/

3. Do ` yarn install` or `npm install`

4. then run `yarn server` or `npm run server`

5. Open the android app and click on the Reload Client Secret button

### Note: 
To test in real device. 
replace the serverUrl with your device IP address in `MainActivity` in android folder `app/src/main/java/com/hyperswitch/hyperswitchdemoapp/MainActivity.kt`