package hu.ubi.soft.vodafonetest.ui.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import hu.ubi.soft.vodafonetest.R

class TextField(context: Context, attributeSet: AttributeSet) :
    TextInputLayout(context, attributeSet) {

    private val textInputLayout: TextInputLayout
    private val textInputEditText: TextInputEditText
    private val pattern: String?
    var textFieldValue: String?
        get() : String? {
            return textInputEditText.text?.let {
                if (this.visibility != View.GONE && it.isNotBlank()) {
                    it.toString()
                } else {
                    null
                }
            }
        }
        set(value) = textInputEditText.setText(value)

    init {
        inflate(context, R.layout.layout_text_field, this)
        val attributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.TextField)
        textInputLayout = findViewById(R.id.textInputLayout)
        textInputEditText = findViewById(R.id.textInputEditText)
        val inputType =
            attributes.getInt(R.styleable.TextField_android_inputType, EditorInfo.TYPE_NULL)
        if (inputType != EditorInfo.TYPE_NULL) {
            textInputEditText.inputType = inputType
            textInputEditText.typeface = Typeface.DEFAULT
        }
        pattern = attributes.getString(R.styleable.TextField_pattern)
        attributes.recycle()
    }

    fun isValid(): Boolean {
        return if (isTextFieldBlank()) {
            false
        } else {
            isTextFieldPatternValid()
        }
    }

    private fun isTextFieldBlank(): Boolean {
        return textInputEditText.text?.toString()?.isBlank() == true
    }

    private fun isTextFieldPatternValid() : Boolean {
        pattern?.let {
            return it.toRegex().matches(textInputEditText.text?.toString() ?: "")
        } ?: run {
            return true
        }
    }

}