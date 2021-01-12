package com.example.bcp_currency.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.bcp_currency.database.Converters
import java.io.Serializable
import java.util.*

@Entity(tableName = "exchange")
data class Exchange(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @TypeConverters(Converters::class)
    val currency: Currency,
    @ColumnInfo(name = "country_name")
    val countryName: String,
    @ColumnInfo(name = "country_flag")
    val base64CountryFlag: String,
    @ColumnInfo(name = "exchange_rate_buy")
    var buyRate: Double,
    @ColumnInfo(name = "exchange_rate_sale")
    var saleRate: Double
): Serializable
