package com.force.hz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn144);
        btn.setOnClickListener(v -> {
            try {
                Window w = getWindow();
                WindowManager.LayoutParams params = w.getAttributes();
                Display display = getDisplay();
                Display.Mode[] modes = display.getSupportedModes();
                
                Display.Mode targetMode = null;
                for (Display.Mode mode : modes) {
                    if ((int)mode.getRefreshRate() == 144) {
                        targetMode = mode;
                        break;
                    }
                }

                if (targetMode != null) {
                    params.preferredDisplayModeId = targetMode.getModeId();
                    w.setAttributes(params);
                    Toast.makeText(this, "Set to 144Hz!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "144Hz not supported on this screen", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
