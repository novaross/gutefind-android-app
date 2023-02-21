package com.gutefind.mobile.ui.map;

import android.graphics.PointF;
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

    private Logger log = LoggerFactory.getLogger(FragmentMapView.class);
    private CanvasView canvasView;


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
        canvasView = binding.canvasView;
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        //mapPresenter.setSomeText();
        log.debug("starting delayed action");
        mapPresenter.setSomeText();
//        canvasView.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void setText(String text) {
        // binding.testView.setText(text);
    }

    @Override
    public void drawDevice(PointF deviceCenter) {

    }
}