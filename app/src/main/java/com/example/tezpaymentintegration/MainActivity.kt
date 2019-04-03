package com.example.tezpaymentintegration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.content.Intent
import android.R.attr.scheme
import android.net.Uri
import android.util.Log


class MainActivity : AppCompatActivity() {

    val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    val GOOGLE_PAY_REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pay.setOnClickListener {
            payUsingGooglePay()
        }

    }

    private fun payUsingGooglePay() {

        val uri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", "test@axisbank")                    //Payee VPA
            .appendQueryParameter("pn", "Test Merchant")                    //Payee name
            .appendQueryParameter("mc", "1234")                             //Payee merchant code
            .appendQueryParameter("tr", "123456789")                        /*Transaction reference ID. This could be order number,subscription number, Bill ID, booking ID, insurance renewal reference, etc. */
            .appendQueryParameter("tn", "test transaction note")            /*Transaction note providing a short description of the transaction. */
            .appendQueryParameter("am", "1")                                //Transaction amount in decimal format.
            .appendQueryParameter("cu", "INR")                              //Currency
            .appendQueryParameter("url", "https://test.merchant.website")   /*This should be a URL when clicked provides customer with further transaction details like complete bill details, bill copy, order copy,
                                                                            ticket details, etc. This can also be used to deliver digital goods such as mp3 files etc. after payment. This URL, when used, MUST BE
                                                                            related to the particular transaction and MUST NOT be used to send unsolicited information that are not relevant to the transaction. */
            .build()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
        this@MainActivity.startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_PAY_REQUEST_CODE) {
            // Process based on the data in response.
            Log.d("result", data!!.getStringExtra("Status"))
        }
    }

}
