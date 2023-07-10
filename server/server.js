const express = require("express");
const app = express();
// This is a public sample test API key.
// Don’t submit any personally identifiable information in requests made with this key.
// Sign in to see your own test API key embedded in code samples.
const stripe = require("stripe")("sk_test_tR3PYbcVNZZ796tH88S4VQ2u");

app.use(express.static("public"));
app.use(express.json());

const calculateOrderAmount = (items) => {
  // Replace this constant with a calculation of the order's amount
  // Calculate the order total on the server to prevent
  // people from directly manipulating the amount on the client
  return 1400;
};

app.post("/create-payment-intent", async (req, res) => {
  try {
    const paymentIntent = await stripe.paymentIntents.create({
      currency: "USD",
      amount: 250000,
      confirm: false,
      capture_method: "automatic",
      authentication_type: "no_three_ds",
      customer_id: "StripeCustomer",
      business_label: "default",
      business_country: "US",
      description: "Hello this is description",
      shipping: {
        address: {
          line1: "1467",
          line2: "Harrison Street",
          line3: "Harrison Street",
          city: "San Fransico",
          state: "California",
          zip: "94122",
          country: "US",
          first_name: "joseph",
          last_name: "Doe",
        },
        phone: {
          number: "8056594427",
          country_code: "+91",
        },
      },
      billing: {
        address: {
          line1: "1467",
          line2: "Harrison Street",
          line3: "Harrison Street",
          city: "San Fransico",
          state: "California",
          zip: "94122",
          country: "US",
          first_name: "joseph",
          last_name: "Doe",
        },
        phone: {
          number: "8056594427",
          country_code: "+91",
        },
      },
      metadata: {
        order_details: {
          product_name: "Apple iphone 15",
          quantity: 1,
        },
      },
    });

    // Send PaymentIntent details to client
    res.send({
      clientSecret: paymentIntent.client_secret,
    });
  } catch (err) {
    try {
      const paymentIntent = await stripe.paymentIntents.create({
        currency: "USD",
        amount: 369999,
      });

      // Send publishable key and PaymentIntent details to client
      res.send({
        clientSecret: paymentIntent.client_secret,
      });
    } catch (err) {
      return res.status(400).send({
        error: {
          message: err.message,
        },
      });
    }
  }
});

app.listen(4242, () => console.log("Node server listening on port 4242!"));
