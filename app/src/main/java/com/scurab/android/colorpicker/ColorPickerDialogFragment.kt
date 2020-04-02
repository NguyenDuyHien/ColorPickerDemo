package com.scurab.android.colorpicker

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_color_picker_dialog.*

private const val PRE_COLOR = "preColor"

class ColorPickerDialogFragment : DialogFragment() {
    private var preColor: Int = 0
    private var selectedColor: Int? = null
    private var listener: OnColorSelectListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preColor = arguments!!.getInt(PRE_COLOR)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_color_picker_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preColorBG = context!!.getDrawable(R.drawable.pre_color) as LayerDrawable
        val selectedColorBG = context!!.getDrawable(R.drawable.selected_color) as LayerDrawable

        gradientPicker.setBrightnessGradientView(brightnessPicker)
        brightnessPicker.setOnColorChangedListener { _, color ->
            selectedColor = color
            selectedColorBG.getDrawable(0).setTint(selectedColor!!)
            selectedColorView.background = selectedColorBG
        }

        preColorBG.getDrawable(0).setTint(preColor)
        preColorView.background = preColorBG
        gradientPicker.setColor(preColor)

        btnDone.setOnClickListener {
            onClickDone()
        }

        btnCancel.setOnClickListener {
            onClickCancel()
        }
    }

    private fun onClickCancel() {
        this.dismiss()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.apply {
            if (resources.getBoolean(R.bool.isTablet)) {
                setLayout(dpToPx(450), ViewGroup.LayoutParams.WRAP_CONTENT)
                setGravity(Gravity.BOTTOM)
            } else {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setGravity(Gravity.CENTER)
            }
            setDimAmount(0.5f)
            attributes.windowAnimations = R.style.DialogAnimation
            setBackgroundDrawableResource(R.drawable.dialog_rounded_bg)
        }
    }

    fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun onClickDone() {
        selectedColor?.let {
            listener?.onColorSelect(it)
            this.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnColorSelectListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnColorSelectListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnColorSelectListener {
        fun onColorSelect(color: Int)
    }

    companion object {
        /**
         * @param preColor = Previous Color of text.
         */
        @JvmStatic
        fun newInstance(preColor: Int) =
                ColorPickerDialogFragment().apply {
                    arguments = Bundle().apply {
                        putInt(PRE_COLOR, preColor)
                    }
                }
    }
}
