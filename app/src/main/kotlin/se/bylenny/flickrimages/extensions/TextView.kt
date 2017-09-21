package se.bylenny.flickrimages.extensions

import android.view.View
import android.widget.TextView

fun TextView.textToggle(charSequence: CharSequence?) {
    if (charSequence.isNullOrBlank()) {
        visibility = View.GONE
    } else {
        text = charSequence
        visibility = View.VISIBLE
    }
}