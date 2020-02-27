package com.japalearn.mobile.utils

import android.text.Editable
import android.text.TextWatcher

abstract class TextChangedListener(): TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


}