package com.atlas.atlasEarth.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atlas.atlasEarth._VirtualGlobe.EarthView;
import com.atlas.atlasEarth._VirtualGlobe.EarthViewOptions;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Post;
import com.atlas.atlasEarth.main.MainActivity;


public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor

    }

    EarthView earthView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        earthView = new EarthView(getContext());

        ((MainActivity) getActivity()).setOnPIWSControlListener(new MainActivity.PIWSControlInterface() {
            @Override
            public void ePM() {
                earthView.getPointInWorldSpace().useProjectionMatrix();
            }
            @Override
            public void eVM() {
                earthView.getPointInWorldSpace().useViewMatrix();
            }
            @Override
            public void eTM() {
                earthView.getPointInWorldSpace().useTransformationMatrix();
            }
            @Override
            public void iX() {
                earthView.getPointInWorldSpace().increasePositionX(0.1f);
            }
            @Override
            public void dX() {
                earthView.getPointInWorldSpace().increasePositionX(-0.1f);
            }
            @Override
            public void iY() {
                earthView.getPointInWorldSpace().increasePositionY(0.1f);
            }
            @Override
            public void dY() {
                earthView.getPointInWorldSpace().increasePositionY(-0.1f);
            }
            @Override
            public void iZ() {
                earthView.getPointInWorldSpace().increasePositionZ(0.1f);
            }
            @Override
            public void dZ() {
                earthView.getPointInWorldSpace().increasePositionZ(-0.1f);
            }
            @Override
            public void iXR() {
                earthView.getPointInWorldSpace().increaseRotX(30);
            }
            @Override
            public void dXR() {
                earthView.getPointInWorldSpace().increaseRotX(-30);
            }
            @Override
            public void iYR() {
                earthView.getPointInWorldSpace().increaseRotY(30);
            }
            @Override
            public void dYR() {
                earthView.getPointInWorldSpace().increaseRotY(-30);
            }
            @Override
            public void iZR() {
                earthView.getPointInWorldSpace().increaseRotZ(30);
            }
            @Override
            public void dZR() {
                earthView.getPointInWorldSpace().increaseRotZ(-30);
            }
        });

        ((MainActivity) getActivity()).setOnEarthControlListener(new MainActivity.EarthControlInterface() {
            @Override
            public void iX() {
                earthView.getEarth().increasePosition(0.1f, 0, 0);
            }
            @Override
            public void dX() {
                earthView.getEarth().increasePosition(-0.1f, 0, 0);
            }
            @Override
            public void iY() {
                earthView.getEarth().increasePosition(0, 0.1f, 0);
            }
            @Override
            public void dY() {
                earthView.getEarth().increasePosition(0, -0.1f, 0);
            }
            @Override
            public void iZ() {
                earthView.getEarth().increasePosition(0, 0, 0.1f);
            }
            @Override
            public void dZ() {
                earthView.getEarth().increasePosition(0, 0, -0.1f);
            }
            @Override
            public void iXR() {
                earthView.getEarth().increaseRotation(30, 0, 0);
            }
            @Override
            public void dXR() {
                earthView.getEarth().increaseRotation(-30, 0, 0);
            }
            @Override
            public void iYR() {
                earthView.getEarth().increaseRotation(0, 30, 0);
            }
            @Override
            public void dYR() {
                earthView.getEarth().increaseRotation(0, -30, 0);
            }
            @Override
            public void iZR() {
                earthView.getEarth().increaseRotation(0, 0, 30);
            }
            @Override
            public void dZR() {
                earthView.getEarth().increaseRotation(0, 0, -30);
            }
        });

        ((MainActivity) getActivity()).setOnPostControlListener(new MainActivity.PostControlInterface() {
            @Override
            public void iX() {
                for (Post post : earthView.getPost()) {
                    post.increasePosition(0.5f, 0, 0);
                }
            }
            @Override
            public void dX() {
                for (Post post : earthView.getPost()) {
                    post.increasePosition(-0.5f, 0, 0);
                }
            }
            @Override
            public void iY() {
                for (Post post : earthView.getPost()) {
                    post.increasePosition(0, 0.5f, 0);
                }
            }
            @Override
            public void dY() {
                for (Post post : earthView.getPost()) {
                    post.increasePosition(0, -0.5f, 0);
                }
            }
            @Override
            public void iZ() {
                for (Post post : earthView.getPost()) {
                    post.increasePosition(0, 0, 0.5f);
                }
            }
            @Override
            public void dZ() {
                for (Post post : earthView.getPost()) {
                    post.increasePosition(0, 0, -0.5f);
                }
            }
            @Override
            public void iXR() {
                for (Post post : earthView.getPost()) {
                    post.increaseRotation(30, 0, 0);
                }
            }
            @Override
            public void dXR() {
                for (Post post : earthView.getPost()) {
                    post.increaseRotation(-30, 0, 0);
                }
            }
            @Override
            public void iYR() {
                for (Post post : earthView.getPost()) {
                    post.increaseRotation(0, 30, 0);
                }
            }

            @Override
            public void dYR() {
                for (Post post : earthView.getPost()) {
                    post.increaseRotation(0, -30, 0);
                }
            }
            @Override
            public void iZR() {
                for (Post post : earthView.getPost()) {
                    post.increaseRotation(0, 0, 30);
                }
            }
            @Override
            public void dZR() {
                for (Post post : earthView.getPost()) {
                    post.increaseRotation(0, 0, -30);
                }
            }
        });

        ((MainActivity) getActivity()).setOnCameraControlListener(new MainActivity.CameraControlInterface() {
            @Override
            public void iPitch() {
                earthView.getCamera().increasePitch(10);
            }
            @Override
            public void dPitch() {
                earthView.getCamera().increasePitch(-10);
            }
            @Override
            public void iDist() {
                earthView.getCamera().increaseDistanceToEarth(0.5f);
            }
            @Override
            public void dDist() {
                earthView.getCamera().increaseDistanceToEarth(-0.5f);
            }
            @Override
            public void iVA() {
                earthView.getCamera().increaseViewAngle(30);
            }
            @Override
            public void dVA() {
                earthView.getCamera().increaseViewAngle(-30);
            }
        });

        ((MainActivity) getActivity()).setOnLightControlListener(new MainActivity.LightControlInterface() {
            @Override
            public void fullLight() {
                if (EarthViewOptions.isFullLightning()) {
                    EarthViewOptions.disableFullLightning();
                } else {
                    EarthViewOptions.enableFullLightning();
                }
            }
            @Override
            public void iXLight() {
                earthView.getLight().increasePosition(5, 0, 0);
            }
            @Override
            public void dXLight() {
                earthView.getLight().increasePosition(-5, 0, 0);
            }
            @Override
            public void iYLight() {
                earthView.getLight().increasePosition(0, 5, 0);
            }
            @Override
            public void dYLight() {
                earthView.getLight().increasePosition(0, -5, 0);
            }
            @Override
            public void iZLight() {
                earthView.getLight().increasePosition(0, 0, 5);
            }
            @Override
            public void dZLight() {
                earthView.getLight().increasePosition(0, 0, -5);
            }
        });

        //Handler handler = new Handler();
        //handler.postDelayed(new Runnable() {
        //    @Override
        //    public void run() {
        //        earthView.addPosts(
        //                new Post(1, earthView, new Geodetic2D(0.0, 0.0), BitmapFactory.decodeResource(getResources(), R.drawable.bild), "test", "14.07.1999", getContext()),
        //                new Post(2, earthView, new Geodetic2D(20.0, 0.0), BitmapFactory.decodeResource(getResources(), R.drawable.bild), "test", "14.07.1999", getContext()),
        //                new Post(3, earthView, new Geodetic2D(40.0, 0.0), BitmapFactory.decodeResource(getResources(), R.drawable.bild), "test", "14.07.1999", getContext()),
        //                new Post(4, earthView, new Geodetic2D(60.0, 0.0), BitmapFactory.decodeResource(getResources(), R.drawable.bild), "test", "14.07.1999", getContext()),
        //                new Post(5, earthView, new Geodetic2D(80.0, 0.0), BitmapFactory.decodeResource(getResources(), R.drawable.bild), "test", "14.07.1999", getContext())
        //        );
        //    }
        //}, 2000);

        //   handler.postDelayed(new Runnable() {
        //       @Override
        //       public void run() {
        //           earthView.removePost(2,3,4,5);
        //       }
        //   }, 15000);


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
