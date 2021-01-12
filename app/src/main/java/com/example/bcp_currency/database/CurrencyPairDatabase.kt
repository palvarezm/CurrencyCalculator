package com.example.bcp_currency.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bcp_currency.dao.CurrencyPairDAO
import com.example.bcp_currency.models.Currency
import com.example.bcp_currency.models.CurrencyPair
import com.example.bcp_currency.models.Exchange

@Database(entities = [Currency::class, Exchange::class, CurrencyPair::class], version = 1)
@TypeConverters(Converters::class)
abstract class CurrencyPairDatabase: RoomDatabase() {
    abstract val currencyPairDAO : CurrencyPairDAO

    companion object{
        @Volatile
        private var INSTANCE: CurrencyPairDatabase? = null

        fun getInstance(context: Context): CurrencyPairDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CurrencyPairDatabase::class.java,
                        "currency_pair_database.db"
                    ).build()
                }

                return instance
            }
        }
    }
}