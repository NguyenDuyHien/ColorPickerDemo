package com.scurab.android.colorpicker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements ColorPickerDialogFragment.OnColorSelectListener {

    private GradientView mTop;
    private GradientView mBottom;
    private TextView mainTextColor;
    private Drawable mIcon;
    private ColorPickerDialogFragment colorPickerDialogFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_main, null);
        setContentView(view);

        mainTextColor = findViewById(R.id.mainTexColor);
    }

    public void onClickShowDialog(View view) {
        showDialog();
    }

    private void showDialog() {
        colorPickerDialogFragment = ColorPickerDialogFragment.newInstance(mainTextColor.getCurrentTextColor());
        fm = getSupportFragmentManager();
        colorPickerDialogFragment.show(fm, " ");
    }

    @Override
    public void onColorSelect(int color) {
        String colorText = "#" + Integer.toHexString(color);
        mainTextColor.setText(colorText);
        mainTextColor.setTextColor(color);
    }
}
