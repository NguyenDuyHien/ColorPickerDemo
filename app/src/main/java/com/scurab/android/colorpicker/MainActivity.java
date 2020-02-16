package com.scurab.android.colorpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements ColorPickerDialogFragment.OnColorSelectListener {

    private TextView mainTextColor;
    private ColorPickerDialogFragment colorPickerDialogFragment;

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
        colorPickerDialogFragment.show(getSupportFragmentManager(), " ");
    }

    @Override
    public void onColorSelect(int color) {
        String colorText = "#" + Integer.toHexString(color);
        mainTextColor.setText(colorText);
        mainTextColor.setTextColor(color);
    }
}
