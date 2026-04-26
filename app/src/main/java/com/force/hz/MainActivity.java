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
                Toast.makeText(this, "Isi dulu Hz-nya!", Toast.LENGTH_SHORT).show();
                return;
            }

            int targetHz = Integer.parseInt(input);
            try {
                Window w = getWindow();
                WindowManager.LayoutParams params = w.getAttributes();
                Display display = getDisplay();
                Display.Mode[] modes = display.getSupportedModes();
                
                Display.Mode targetMode = null;
                float maxSupportedHz = 60;

                // Cari mode yang pas dan cari tahu Hz tertinggi yang didukung hardware
                for (Display.Mode mode : modes) {
                    if (mode.getRefreshRate() > maxSupportedHz) {
                        maxSupportedHz = mode.getRefreshRate();
                    }
                    if ((int)mode.getRefreshRate() == targetHz) {
                        targetMode = mode;
                    }
                }

                // Cek apakah targetHz masuk akal
                if (targetHz > (int)maxSupportedHz) {
                    Toast.makeText(this, "Error: HP lu cuma support sampe " + (int)maxSupportedHz + "Hz!", Toast.LENGTH_LONG).show();
                } else if (targetMode != null) {
                    params.preferredDisplayModeId = targetMode.getModeId();
                    w.setAttributes(params);
                    Toast.makeText(this, "Berhasil Force ke " + targetHz + "Hz", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Mode " + targetHz + "Hz tidak tersedia di sistem ini", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
