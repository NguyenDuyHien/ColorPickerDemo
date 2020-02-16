package com.scurab.android.colorpicker

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_color_picker_dialog.*


private const val PRE_COLOR = "preColor"

/**
 * A simple [DialogFragment] subclass.
 * Activities that contain this fragment must implement the
 * [ColorPickerDialogFragment.OnColorSelectListener] interface
 * to handle interaction events.
 * Use the [ColorPickerDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

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

        val preColorBG = resources.getDrawable(R.drawable.pre_color) as LayerDrawable
        val selectedColorBG = resources.getDrawable(R.drawable.selected_color) as LayerDrawable

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

    override fun onStart() {
        super.onStart()

        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnColorSelectListener {
        fun onColorSelect(color: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param preColor = Previous Color of text.
         * @return A new instance of fragment ColorPickerDialogFragment.
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
