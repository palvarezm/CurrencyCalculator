package com.example.bcp_currency.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.bcp_currency.models.CurrencyPair

@Dao
interface CurrencyPairDAO {

    @Query("SELECT * from currency_pair")
    fun getAllCurrencyPair(): LiveData<CurrencyPair>
}