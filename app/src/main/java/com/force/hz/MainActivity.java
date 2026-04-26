package com.force.hz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etHz = findViewById(R.id.et_hz);
        Button btnRun = findViewById(R.id.btn_run);

        btnRun.setOnClickListener(v -> {
            String input = etHz.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, "Isi angka Hz!", Toast.LENGTH_SHORT).show();
                return;
            }

            int targetHz = Integer.parseInt(input);
            try {
                Window w = getWindow();
                WindowManager.LayoutParams params = w.getAttributes();
                Display display = getDisplay();
                Display.Mode[] modes = display.getSupportedModes();
                
                Display.Mode targetMode = null;
                StringBuilder supportedList = new StringBuilder();

                for (Display.Mode mode : modes) {
                    int currentModeHz = (int)mode.getRefreshRate();
                    supportedList.append(currentModeHz).append(" ");
                    
                    if (currentModeHz == targetHz) {
                        targetMode = mode;
                    }
                }

                if (targetMode != null) {
                    params.preferredDisplayModeId = targetMode.getModeId();
                    w.setAttributes(params);
                    Toast.makeText(this, "Berhasil! Sekarang jalan di " + targetHz + "Hz", Toast.LENGTH_SHORT).show();
                } else {
                    // Jika Hz yang diminta tidak ada di list sistem
                    Toast.makeText(this, "HP lu nggak dukung " + targetHz + "Hz. Mode tersedia: " + supportedList.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
