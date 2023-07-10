const fs = require("fs");

//modify server.js import
fs.readFile("./server.js", "utf8", (err, data) => {
  if (err) {
    console.error(err);
    return;
  }
  let replacedNodePckge = data.replace(
    '"stripe"',
    '"@juspay-tech/hyperswitch-node"'
  );

  fs.writeFile("./server.js", replacedNodePckge, function (err) {
    if (err) return console.log(err);
    console.log("Replaced Stripe node to HyswitchNode");
  });
});

// modify setting.gradle
fs.readFile("../settings.gradle", "utf8", (err, data) => {
  if (err) {
    console.error(err);
    return;
  }
  let replacedNodePckge = data.replace(
    "mavenCentral() // Add import here",
    "mavenCentral()\n\t\tmaven {url \"https://maven.hyperswitch.io/release/production/android/maven/1.0.0-sandbox01\"}\n\t\tmaven {url 'https://x.klarnacdn.net/mobile-sdk/'}\n\t\tmaven {url \"https://cardinalcommerceprod.jfrog.io/artifactory/android\"\ncredentials {username 'braintree_team_sdk'\npassword 'AKCp8jQcoDy2hxSWhDAUQKXLDPDx6NYRkqrgFLRc3qDrayg6rrCbJpsKKyMwaykVL8FWusJpp'}}"
  );

  fs.writeFile("../settings.gradle", replacedNodePckge, function (err) {
    if (err) return console.log(err);
    console.log("Replaced setting.gradle imports");
  });
});

//modify build.gradle
fs.readFile("../app/build.gradle", "utf8", (err, data) => {
  if (err) {
    console.error(err);
    return;
  }

  let replacedNodePckge = data.replace(
    "com.stripe:stripe-android:20.26.0",
    "io.hyperswitch:hyperswitch-android:+"
  );

  fs.writeFile("../app/build.gradle", replacedNodePckge, function (err) {
    if (err) return console.log(err);
    console.log("Build.gradle import changes");
  });
});

//modify CheckoutForm.js import
fs.readFile(
  "../app/src/main/java/com/nitishkr/hyperswitchdemoapp/CheckoutActivity.kt",
  "utf8",
  (err, data) => {
    if (err) {
      console.error(err);
      return;
    }
    let replacedNodePckge = data
      .replace(
        "import com.stripe.android.paymentsheet",
        "\nimport io.hyperswitch.HyperInterface\nimport io.hyperswitch.paymentsheet"
      )
      .replace(
        "com.stripe.android.paymentsheet.PaymentSheetResult",
        "io.hyperswitch.paymentsheet.PaymentSheetResult"
      )
      .replace(
        ": AppCompatActivity()",
        ": AppCompatActivity(), HyperInterface"
      );

    fs.writeFile(
      "../app/src/main/java/com/nitishkr/hyperswitchdemoapp/CheckoutActivity.kt",
      replacedNodePckge,
      function (err) {
        if (err) return console.log(err);
        console.log(
          "Replaced Stripe imports in checkoutActitvity to hyperswitch"
        );
      }
    );
  }
);

// implement HyperInterface in activity where you wanted to open paymentsheet
fs.readFile(
  "../app/src/main/java/com/nitishkr/hyperswitchdemoapp/MainActivity.kt",
  "utf8",
  (err, data) => {
    if (err) {
      console.error(err);
      return;
    }
    let replacedNodePckge = data.replace(
      "com.stripe.android.PaymentConfiguration",
      "io.hyperswitch.PaymentConfiguration"
    );

    fs.writeFile(
      "../app/src/main/java/com/nitishkr/hyperswitchdemoapp/MainActivity.kt",
      replacedNodePckge,
      function (err) {
        if (err) return console.log(err);
        console.log("Change Imports for PaymentConfiguration");
      }
    );
  }
);
