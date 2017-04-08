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
    private static double glVersion = 3.0;

    private static class ContextFactory implements GLSurfaceView.EGLContextFactory {

        private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

        public EGLContext createContext(
                EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {

            Log.w("debug", "creating OpenGL ES " + glVersion + " context");
            int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, (int) glVersion,
                    EGL10.EGL_NONE };
            // attempt to create a OpenGL ES 3.0 context
            EGLContext context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
            return context; // returns null if 3.0 is not supported;
        }

        @Override
        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {

        }
    }

}
