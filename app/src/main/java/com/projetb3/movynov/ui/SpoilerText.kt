package com.projetb3.movynov.ui

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView

class SpoilerText {

    public fun setSpoilerTextToTextView(text: String, textView : TextView) {
        val spoilerTag = "||"
        var text = text

        // Save the original text without the tags
        val original = text.replace(spoilerTag, "")

        // Get the character ranges
        val ranges = mutableListOf<Pair<Int, Int>>()
        while (text.contains(spoilerTag)) {
            // Get start and end of spoiler tags
            val start = text.indexOf(spoilerTag)
            val end = text.indexOf(spoilerTag, start + spoilerTag.length) - spoilerTag.length

            // Add to the list
            ranges.add(Pair(start, end))

            // Remove starting spoiler tags.
            // This is specifically done before checking if an end tag exists
            text = text.replaceRange(start, start + spoilerTag.length, "")

            // If there is no end tag, the text was badly formatted. It is therefore ignored
            if (end <= start) continue

            // Remove starting spoiler tags
            text = text.replaceRange(end, end + spoilerTag.length, "")
        }

        // Required in order for clickable spans to work
        textView.movementMethod = LinkMovementMethod.getInstance()

        updateTextView(original, ranges, textView)
    }

    private fun updateTextView(plainText: String, ranges: MutableList<Pair<Int, Int>>, textView: android.widget.TextView){
        val spannableString = SpannableString(plainText)

        for (range in ranges) {
            spannableString.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: android.view.View) {
                        // Remove clicked range from ranges
                        ranges.remove(Pair(range.first, range.second))

                        updateTextView(plainText, ranges, textView)
                    }
                },
                range.first,
                range.second,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            // Set the background to black to give it the hidden effect
            spannableString.setSpan(
                BackgroundColorSpan(Color.BLACK),
                range.first,
                range.second,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // The foreground color (text color) should match the background
            spannableString.setSpan(
                ForegroundColorSpan(Color.BLACK),
                range.first,
                range.second,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textView.text = spannableString
    }
}