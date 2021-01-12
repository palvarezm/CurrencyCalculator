package com.example.bcp_currency.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bcp_currency.R
import com.example.bcp_currency.models.Exchange
import com.example.bcp_currency.utils.ImageUtils
import com.example.bcp_currency.utils.toMoney
import kotlinx.android.synthetic.main.item_currency_pair.view.*

class CurrencyPairAdapter(private var currencySource: Exchange, private var destinationExchangeList: MutableList<Exchange>): RecyclerView.Adapter<CurrencyPairAdapter.CurrencyPairViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    var currencyExchangeList: MutableList<Exchange> = mutableListOf<Exchange>()

    init{
        currencyExchangeList.add(currencySource)
        currencyExchangeList.addAll(destinationExchangeList)
    }

    fun setupList(currencyExchangeList: MutableList<Exchange>){
        this.currencyExchangeList = currencyExchangeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrencyPairViewHolder(LayoutInflater.from(parent?.context)
        .inflate(R.layout.item_currency_pair, parent, false))

    override fun onBindViewHolder(holder: CurrencyPairViewHolder, position: Int) = holder.let {
        it.clear()
        it.onBind(position)
    }

    override fun getItemCount() = currencyExchangeList.size

    inner class CurrencyPairViewHolder(view: View): RecyclerView.ViewHolder(view) {

        init{
            itemView.card_view.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
        fun clear(){
            itemView.iv_country_flag.setImageDrawable(null)
            itemView.tv_country_title.text = ""
            itemView.tv_exchange_rate.text = ""
        }

        fun onBind(position: Int){
            val(countryName, countryFlagURL, exchangeRate) = currencyExchangeList[position]
            fillData(currencyExchangeList[position])
        }

        private fun fillData(currencyExchange: Exchange){
            val decodedImage = ImageUtils.fromBase64(currencyExchange.base64CountryFlag)
            itemView.iv_country_flag.setImageBitmap(decodedImage)
            itemView.tv_country_title.text = currencyExchange.countryName
            itemView.tv_exchange_rate.text = "1 " + currencySource.currency.abbreviation + " = " +
                    currencyExchange.buyRate.toMoney() + " " + currencyExchange.currency.abbreviation
        }
    }
}