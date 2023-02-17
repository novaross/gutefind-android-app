package com.gutefind.mobile.ui.map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.gutefind.mobile.R;
import com.gutefind.mobile.databinding.FragmentMapViewBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FragmentMapView extends Fragment implements FragmentMapViewInt {

    Logger log = LoggerFactory.getLogger(FragmentMapView.class);

    private FragmentMapViewBinding binding;
    private MapPresenter mapPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapPresenter = new MapPresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentMapView.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //mapPresenter.setSomeText();
        log.debug("starting delayed action");
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                log.debug("running delayed action");
                mapPresenter.setSomeText();
            }
        }, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void setText(String text) {
        binding.testView.setText(text);
    }
}