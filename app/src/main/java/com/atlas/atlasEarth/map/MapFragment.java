package com.atlas.atlasEarth.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atlas.atlasEarth._VirtualGlobe.EarthView;
import com.atlas.atlasEarth._VirtualGlobe.EarthViewOptions;
import com.atlas.atlasEarth.main.MainActivity;


public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor

    }

    EarthView earthView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        earthView = new EarthView(getContext());


        ((MainActivity) getActivity()).setOnLightControlListener(new MainActivity.LightControlInterface() {
            @Override
            public void left() {
                earthView.getShapefileRenderables().get(0).setRotZ(earthView.getShapefileRenderables().get(0).getRotZ() + 5);
            }

            @Override
            public void up() {

            }

            @Override
            public void down() {

            }

            @Override
            public void right() {

            }

            @Override
            public void in() {
                earthView.getShapefileRenderables().get(0).setRotY(earthView.getShapefileRenderables().get(0).getRotY() + 5);
            }

            @Override
            public void out() {
                earthView.getShapefileRenderables().get(0).setRotX(earthView.getShapefileRenderables().get(0).getRotX() + 5);
            }
        });
        ((MainActivity) getActivity()).setOnOptionsControlListener(new MainActivity.OptionsInterface() {
            @Override
            public void fullLight() {
                if (EarthViewOptions.isFullLightning()) {
                    EarthViewOptions.disableFullLightning();
                } else {
                    EarthViewOptions.enableFullLightning();
                }
            }
        });


        return earthView;
    }


    @Override
    public void onPause() {
        super.onPause();
        earthView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        earthView.onResume();
    }


}
