# Hyperswitch-Android-Demo-App

A simple Android app to demo the new hyperswitch android library

## Running the sample App

1. Build the application

2. Provide valid API key in server/server.js and Publishable key in CheckoutActivity.kt. You can create your keys using the Hyperswitch dashboard. https://app.hyperswitch.io/

> Note: You can checkout the live demo app on google play store here. https://play.google.com/store/apps/details?id=io.hyperswitch.hyperecom

```
//in server/server.js
const hyper = require("@juspay-tech/hyperswitch-node")("api_key");
```

```
//in CheckoutActivity.kt
// replace the test publishable key with your hyperswitch publishable key
    PaymentConfiguration.init(
        applicationContext,
        "publishable_key"
    )
```

3. Run the Server

```
//in server folder
npm install
```

```
//then run the server through command
npm start
```

4. Run the application
