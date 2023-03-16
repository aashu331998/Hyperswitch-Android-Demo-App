const express = require("express");
const app = express();

// replace the test api key with your hyperswitch api key
const hyper = require("@juspay-tech/hyperswitch-node")(
  "snd_9d552d20e1f0411e8c9c45193cdc0677"
);

app.use(express.static("public"));
app.use(express.json());

const calculateAmount = (items) => {
  // calculate the order amount using request
  return 1300;
};

app.post("/create-payment-intent", async (request, response) => {
  const { items } = request.body;

  //create a payment using amount, currency, metadata etc.
  const paymentIntent = await hyper.paymentIntents.create({
    amount: calculateAmount(items),
    currency: "USD",
  });

  //return clientSecret to initiate payment flow at the client
  response.send({
    clientSecret: paymentIntent.client_secret,
  });
});

app.listen(4444, () => console.log("Node server listening on port 4444!"));
