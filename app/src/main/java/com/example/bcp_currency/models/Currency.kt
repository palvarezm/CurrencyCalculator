package com.example.bcp_currency.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val code: String,
    val description: String,
    val abbreviation: String
): Serializable
