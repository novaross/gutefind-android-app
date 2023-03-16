package com.gutefind.mobile.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gutefind.mobile.databinding.FragmentSettingsBinding;
import com.tencent.mmkv.MMKV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FragmentSettings extends Fragment {

    private final Logger log = LoggerFactory.getLogger(FragmentSettings.class);

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAllFields();
            }
        });
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadValuesFromSettings();
    }

    private void loadValuesFromSettings() {
        MMKV kv = MMKV.defaultMMKV();

        binding.txtBeaconTopLeftLat.setText(Double.toString(kv.decodeDouble("BEACON_TOP_LEFT_LAT")));
        binding.txtBeaconTopLeftLng.setText(Double.toString(kv.decodeDouble("BEACON_TOP_LEFT_LNG")));

        binding.txtBeaconTopRightLat.setText(Double.toString(kv.decodeDouble("BEACON_TOP_RIGHT_LAT")));
        binding.txtBeaconTopRightLng.setText(Double.toString(kv.decodeDouble("BEACON_TOP_RIGHT_LNG")));

        binding.txtBeaconBottomRightLat.setText(Double.toString(kv.decodeDouble("BEACON_BOTTOM_RIGHT_LAT")));
        binding.txtBeaconBottomRightLng.setText(Double.toString(kv.decodeDouble("BEACON_BOTTOM_RIGHT_LNG")));

        binding.txtBeaconBottomLeftLat.setText(Double.toString(kv.decodeDouble("BEACON_BOTTOM_LEFT_LAT")));
        binding.txtBeaconBottomLeftLng.setText(Double.toString(kv.decodeDouble("BEACON_BOTTOM_LEFT_LNG")));

    }

    private void saveAllFields() {
        MMKV kv = MMKV.defaultMMKV();

        kv.encode("BEACON_TOP_LEFT_LAT", getDouble(binding.txtBeaconTopLeftLat.getText().toString()));
        kv.encode("BEACON_TOP_LEFT_LNG", getDouble(binding.txtBeaconTopLeftLng.getText().toString()));

        kv.encode("BEACON_TOP_RIGHT_LAT", getDouble(binding.txtBeaconTopRightLat.getText().toString()));
        kv.encode("BEACON_TOP_RIGHT_LNG", getDouble(binding.txtBeaconTopRightLng.getText().toString()));

        kv.encode("BEACON_BOTTOM_RIGHT_LAT", getDouble(binding.txtBeaconBottomRightLat.getText().toString()));
        kv.encode("BEACON_BOTTOM_RIGHT_LNG", getDouble(binding.txtBeaconBottomRightLng.getText().toString()));

        kv.encode("BEACON_BOTTOM_LEFT_LAT", getDouble(binding.txtBeaconBottomLeftLat.getText().toString()));
        kv.encode("BEACON_BOTTOM_LEFT_LNG", getDouble(binding.txtBeaconBottomLeftLng.getText().toString()));
    }

    private double getDouble(String text) {
        try {
            log.debug("trying to convert text {} to double", text);
            return Double.parseDouble(text);
        } catch (Exception e) {
            log.error("Error converting string {} to double", text, e);
            return 0d;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
