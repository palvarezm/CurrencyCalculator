package com.example.bcp_currency.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.bcp_currency.database.Converters
import java.io.Serializable

@Entity(tableName = "currency_pair")
data class CurrencyPair(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "exchange_source")
    @TypeConverters(Converters::class)
    var exchangeSource: Exchange,
    @ColumnInfo(name = "exchange_destinations")
    @TypeConverters(Converters::class)
    var exchangeDestinations: MutableList<Exchange>
): Serializable
