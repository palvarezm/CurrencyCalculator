package com.example.bcp_currency.utils

internal inline fun Double.toMoney(): String {
    return "%.2f".format(this)
}