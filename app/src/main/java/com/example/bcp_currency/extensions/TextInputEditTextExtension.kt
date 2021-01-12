package com.example.bcp_currency.extensions

import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Matcher
import java.util.regex.Pattern

internal fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

internal fun TextInputEditText.setMoneyFormat(){
    this.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter())
}

class DecimalDigitsInputFilter : InputFilter {
    var decimalPattern: Pattern = Pattern.compile("[0-9]*+((\\.[0-9]?)?)||(\\.)?")

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        val matcher: Matcher = decimalPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }
}