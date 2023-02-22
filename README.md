# Hyperswitch-Android-Demo-App

A simple Android app to demo the new hyperswitch android library

## Running the sample

1. Build the application

2. Provide valid Api key in server/server.js and Publishable key in CheckoutActivity.kt. You can create your keys using the Hyperswitch dashboard. https://app.hyperswitch.io/

```
//in server.js
const hyper = require("@juspay-tech/hyper-node")("api_key");
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

//in server folder

```
npm install
```

//then run the server through command

```
npm start
```

4. Run the application
