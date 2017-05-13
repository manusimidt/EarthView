package com.atlas.atlasEarth.search;


import android.opengl.EGLSurface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atlas.atlasEarth.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class EffectFragment extends Fragment implements GLSurfaceView.Renderer{



    public EffectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }
    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
