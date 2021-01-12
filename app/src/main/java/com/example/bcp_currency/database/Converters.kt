package com.example.bcp_currency.database

import androidx.room.TypeConverter
import com.example.bcp_currency.models.Currency
import com.example.bcp_currency.models.Exchange
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromExchange(value: Exchange): String{
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toExchange(stringifiedExchange: String): Exchange{
            return Gson().fromJson(stringifiedExchange, Exchange::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromCurrency(value: Currency): String {
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toCurrency(stringifiedCurrency: String): Currency {
            return Gson().fromJson(stringifiedCurrency, Currency::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromExchangeList(value: List<Exchange>): String {
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toExchangeList(stringifiedExchangeList: String): List<Exchange> {
            val type = object : TypeToken<List<Exchange>>() {}.type
            return Gson().fromJson(stringifiedExchangeList, type)
        }
    }
}