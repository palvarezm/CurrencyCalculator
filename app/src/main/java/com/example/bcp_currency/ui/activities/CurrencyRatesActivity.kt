package com.example.bcp_currency.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcp_currency.R
import com.example.bcp_currency.models.CurrencyPair
import com.example.bcp_currency.ui.adapters.CurrencyPairAdapter
import kotlinx.android.synthetic.main.activity_currency_rates.*

class CurrencyRatesActivity : AppCompatActivity() {
    private lateinit var adapter: CurrencyPairAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var currencyPairs: CurrencyPair

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_rates)
        currencyPairs = intent.extras?.get("currencyPairs") as CurrencyPair
        setupRecyclerView()
    }

    fun setupRecyclerView(){
        var source = currencyPairs.exchangeSource
        var destinationList = currencyPairs.exchangeDestinations
        adapter = CurrencyPairAdapter(source, destinationList)
        adapter.onItemClick = { position ->
            setBack(position)
        }
        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_exchange_rates.layoutManager = layoutManager
        rv_exchange_rates.adapter = adapter
    }

    fun setBack(position: Int){
        var intent = Intent()
        intent.putExtra("position", position)
        setResult(RESULT_OK, intent)
        finish()
    }
}