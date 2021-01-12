package com.example.bcp_currency.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bcp_currency.R
import com.example.bcp_currency.extensions.afterTextChanged
import com.example.bcp_currency.extensions.setMoneyFormat
import com.example.bcp_currency.models.CurrencyPair
import com.example.bcp_currency.models.Exchange
import com.example.bcp_currency.utils.JsonUtils
import com.example.bcp_currency.utils.toMoney
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_currency_calculator.*
import java.io.Serializable

class CurrencyCalculatorActivity : AppCompatActivity() {
    lateinit var currencyPairs: CurrencyPair
    lateinit var source: Exchange
    lateinit var destination: Exchange
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_calculator)
        initValues()
        updateViewValues()
        tiet_sent_amount.setMoneyFormat()
        initListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val position = data?.getIntExtra("position", 0)
        swapCurrenciesFromList(requestCode, position)
        updateRetrievedAmount(tiet_sent_amount.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        val prefsEditor = sharedPreferences.edit()
        val json = Gson().toJson(currencyPairs)
        prefsEditor.putString("currencyPairJSON", json)
        prefsEditor.apply()
    }

    private fun initValues(){
        sharedPreferences = getPreferences(MODE_PRIVATE)
        var json: String? = sharedPreferences.getString("currencyPairJSON", "")
        if (json == null || json == ""){
            json = JsonUtils.getJsonDataFromAsset(applicationContext, "response.json")
        }
        currencyPairs = Gson().fromJson(json, CurrencyPair::class.java)
        source = currencyPairs.exchangeSource
        destination = currencyPairs.exchangeDestinations.first()
    }

    private fun initListeners(){
        tv_source_currency.setOnLongClickListener {
            val intent = Intent(this@CurrencyCalculatorActivity, CurrencyRatesActivity::class.java)
            intent.putExtra("currencyPairs", currencyPairs as Serializable)
            startActivityForResult(intent, 0)
            return@setOnLongClickListener true
        }
        tv_destination_currency.setOnLongClickListener {
            val intent = Intent(this@CurrencyCalculatorActivity, CurrencyRatesActivity::class.java)
            intent.putExtra("currencyPairs", currencyPairs as Serializable)
            startActivityForResult(intent, 1)
            return@setOnLongClickListener true
        }
        btn_swap_currency.setOnClickListener {
            swapSourceAndDestination()
            updateViewValues()
            updateRetrievedAmount(tiet_sent_amount.text.toString())
        }
        tiet_sent_amount.afterTextChanged {
            var sentAmount = tiet_sent_amount.text.toString()
            sentAmount.let {
                val dotOcurrences = sentAmount.count { ".".contains(it) }
                if (sentAmount.isNotEmpty() && dotOcurrences <= 1){
                    if (sentAmount.first() == '.'){
                        sentAmount = sentAmount.replace(".", "0.")
                        tiet_sent_amount.setText(sentAmount)
                        tiet_sent_amount.setSelection(sentAmount.count())
                    }
                    updateRetrievedAmount(sentAmount)
                }
                else{
                    tiet_retrieved_amount.setText("")
                }
            }
        }
    }

    private fun updateRetrievedAmount(sentAmount: String){
        if (sentAmount.isNotEmpty()){
            val retrievedAmount = sentAmount.toDouble() * destination.buyRate
            tiet_retrieved_amount.setText(retrievedAmount.toMoney())
        }
    }

    private fun updateViewValues(){
        tv_source_currency.text = source.currency.description
        tv_destination_currency.text = destination.currency.description
        tv_exchange_rate_buy.text = destination.buyRate.toMoney()
        tv_exchange_rate_sale.text = destination.saleRate.toMoney()
    }

    private fun swapCurrenciesFromList(requestCode: Int, position: Int?){
        position?.let {
            val destinationPosition = position - 1
            when(requestCode){
                0 -> {
                    if (position != 0) {
                        if (position == 1) {
                            swapSourceAndDestination()
                        } else {
                            val newExchangeSource =
                                currencyPairs.exchangeDestinations[destinationPosition]
                            val previousExchangeSource = currencyPairs.exchangeSource
                            source = newExchangeSource
                            currencyPairs.exchangeSource = source
                            currencyPairs.exchangeDestinations.removeAt(destinationPosition)
                            currencyPairs.exchangeDestinations.add(
                                destinationPosition,
                                previousExchangeSource
                            )
                            destination = currencyPairs.exchangeDestinations.first()
                            updateCurrencyPairsExchangeRates()
                        }
                    }
                }
                1 -> {
                    if (position != 0) {
                        val newExchangeDestination =
                            currencyPairs.exchangeDestinations[destinationPosition]
                        currencyPairs.exchangeDestinations.removeAt(destinationPosition)
                        currencyPairs.exchangeDestinations.add(0, newExchangeDestination)
                        source = currencyPairs.exchangeSource
                        destination = currencyPairs.exchangeDestinations.first()
                    } else {
                        swapSourceAndDestination()
                    }
                }
            }
            updateViewValues()
        }
    }

    private fun swapSourceAndDestination(){
        val newSource = destination
        val newDestination = source
        currencyPairs.exchangeSource = newSource
        currencyPairs.exchangeDestinations.removeAt(0)
        currencyPairs.exchangeDestinations.add(0, newDestination)
        source = newSource
        destination = newDestination
        updateCurrencyPairsExchangeRates()
    }

    private fun updateCurrencyPairsExchangeRates(){
        val newBuyRate = source.buyRate
        val newSaleRate = source.saleRate

        currencyPairs.exchangeDestinations.forEach {
            it.buyRate = it.buyRate / newBuyRate
            it.saleRate = it.saleRate / newSaleRate
        }
        currencyPairs.exchangeSource.buyRate = 1.00
        currencyPairs.exchangeSource.saleRate = 1.00
    }
}