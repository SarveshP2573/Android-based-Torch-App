package com.example.torch;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.torch.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.button3.getText().toString().equals("Turn On")) {
                    binding.button3.setText(R.string.TurnOff);
                    binding.imageView2.setImageResource(R.drawable.on_to);
                    changeLightState(true);
                }else {
                    binding.button3.setText(R.string.TurnOn);
                    binding.imageView2.setImageResource(R.drawable.off_to);
                    changeLightState(false);
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void changeLightState(boolean b){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N)
        {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String camId=null;

            try {
                camId=cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(camId,b);
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.button3.setText(R.string.TurnOn);
    }
}