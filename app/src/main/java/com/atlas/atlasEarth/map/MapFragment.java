package com.atlas.atlasEarth.map;


import android.os.Bundle;
import android.os.Handler;
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
                    earthView.getLight().increaseAngle((float)Math.PI);
                }

                @Override
                public void up() {

                }

                @Override
                public void down() {

                }

                @Override
                public void right() {
                    earthView.getLight().increaseAngle(-(float)Math.PI);
                }

                @Override
                public void in() {

                }

                @Override
                public void out() {

                }
            });
        ((MainActivity) getActivity()).setOnOptionsControlListener(new MainActivity.OptionsInterface() {
            @Override
            public void fullLight() {
                if(EarthViewOptions.isFullLightning()) {
                    EarthViewOptions.disableFullLightning();
                }else{
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
