package com.atlas.atlasEarth.map;


import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atlas.atlasEarth._VirtualGlobe.EarthView;
import com.atlas.atlasEarth._VirtualGlobe.EarthViewOptions;
import com.atlas.atlasEarth.main.MainActivity;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;


public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor

    }

    EarthView earthView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        earthView = new EarthView(getContext());

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
                earthView.getPost().increasePosition(0.5f, 0, 0);
            }
            @Override
            public void dX() {
                earthView.getPost().increasePosition(-0.5f, 0, 0);
            }
            @Override
            public void iY() {
                earthView.getPost().increasePosition(0, 0.5f, 0);
            }
            @Override
            public void dY() {
                earthView.getPost().increasePosition(0, -0.5f, 0);
            }
            @Override
            public void iZ() {
                earthView.getPost().increasePosition(0, 0, 0.5f);
            }
            @Override
            public void dZ() {
                earthView.getPost().increasePosition(0, 0, -0.5f);
            }
            @Override
            public void iXR() {
                earthView.getPost().increaseRotation(30, 0, 0);
            }
            @Override
            public void dXR() {
                earthView.getPost().increaseRotation(-30, 0, 0);
            }
            @Override
            public void iYR() {
                earthView.getPost().increaseRotation(0, 30, 0);
            }
            @Override
            public void dYR() {
                earthView.getPost().increaseRotation(0, -30, 0);
            }
            @Override
            public void iZR() {
                earthView.getPost().increaseRotation(0, 0, 30);
            }
            @Override
            public void dZR() {
                earthView.getPost().increaseRotation(0, 0, -30);
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
                //      earthView.getCamera().increaseDistanceToEarth(0.5f);
            }
            @Override
            public void dDist() {
                //      earthView.getCamera().increaseDistanceToEarth(-0.5f);
            }
            @Override
            public void iVA() {
                //      earthView.getCamera().increaseViewAngle(30);
            }
            @Override
            public void dVA() {
                //      earthView.getCamera().increaseViewAngle(-30);
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
    private static double glVersion = 3.0;

    private static class ContextFactory implements GLSurfaceView.EGLContextFactory {

        private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

        public EGLContext createContext(
                EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {

            Log.w("debug", "creating OpenGL ES " + glVersion + " context");
            int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, (int) glVersion,
                    EGL10.EGL_NONE};
            // attempt to create a OpenGL ES 3.0 context
            EGLContext context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
            return context; // returns null if 3.0 is not supported;
        }

        @Override
        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {

        }
    }

}
