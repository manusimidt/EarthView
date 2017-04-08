package com.atlas.atlasEarth._VirtualGlobe;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ExternalMeshData.ObjLoader;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Post.Post;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.EarthModel;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.SpaceBackground;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.ShapefileAppearance;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefiles.ShapefileRenderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.TouchHandeling.TouchHandler;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.RendererGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.EarthShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderState;
import com.atlas.atlasEarth.general.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;


public class EarthView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private Renderable earthRenderable;
    private Renderable background;
    private Renderable post;
    private Light light;
    private Camera camera;
    private RendererGL3x renderer;
    private TouchHandler touchHandler;
    private static float height;
    private static float width;
    private ScaleGestureDetector scaleGestureDetector;
    private List<Renderable> shapefileRenderables;
    private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;
    private static double glVersion = 3.0;





    public EarthView(Context context) {
        super(context);
        init();
    }

    public void init() {

        //Set the OpenGlVersion
        super.setEGLContextClientVersion(2);
        //configure the output of OpenGL
        super.setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        //Set the RendererGL3x
        super.setRenderer(this);
        // Render the view only when there is a change in the drawing data
        //super.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @Override
    public void queueEvent(Runnable r) {
        super.queueEvent(r);
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        //light = new Light(new Vector3F(0.82f, 0.72f, 0.55f));
        light = new Light(new Vector3F(0.82f, 0.72f, 0.95f));
        Ellipsoid globeShape = Ellipsoid.ScaledWgs84;
        earthRenderable = EarthModel.getInstance();
        earthRenderable.loadTextures(getContext());

        camera = new Camera(earthRenderable);
        touchHandler = new TouchHandler(MatricesUtility.createProjectionMatrix(getContext()), camera, getContext());

        background = new SpaceBackground();
        background.loadTextures(getContext());


        RenderState renderState = new RenderState();
        renderState.getDepthTest().setEnabled(true);
        renderState.getDepthTest().setDepthTestFunction(ByteFlags.ALWAYS);
        renderState.getFacetCulling().setEnabled(true);
        renderState.getFacetCulling().setCullFace(ByteFlags.BACK);
        renderState.getFacetCulling().setWindingOrder(ByteFlags.COUNTERCLOCKWISE);


        shapefileRenderables = new ArrayList<>(1);
        try {
            ShapefileRenderable shapefileRenderable = new ShapefileRenderable(R.raw.countries_110m, getContext(), globeShape,
                    new ShapefileAppearance());
            shapefileRenderables.add(shapefileRenderable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        renderer = new RendererGL3x(getContext(), renderState);
        touchHandler = new TouchHandler(renderer.getProjectionMatrix(), camera, getContext());

        post = new Post(new Vector3F(0,1,0), BitmapFactory.decodeResource(getResources(), R.drawable.fern), "test", "14.07.1999", getContext());
        post.loadTextures(getContext());



        System.gc();
    }

    public List<Renderable> getShapefileRenderables() {
        return shapefileRenderables;
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //Called when the screen rotates
        GLES31.glViewport(0, 0, width, height);
        EarthView.width = width;
        EarthView.height = height;

    }


    @Override
    public void onDrawFrame(GL10 gl10) {
        renderer.progressEarthModel(earthRenderable);
        renderer.progressBackground(background);
        renderer.progressShapeFile(shapefileRenderables);
        renderer.progressPost(post);
        renderer.render(light, camera);
        light.calculateAngleByTime();

    }


    @Override
    public void onPause() {
        super.onPause();
        earthRenderable.clear();
        background.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public static float getEarthViewHeight(Context context) {
        if (height == 0) {
            height = Utils.getDisplayDimensions(context, false) - Utils.dpToPixel(55, context) - Utils.getStatusBarHeight(context);
        }
        return height;
    }

    public static float getEarthViewWidth(Context context) {
        if (width == 0) {
            width = Utils.getDisplayDimensions(context, true);
        }

        return width;
    }

    public Light getLight() {
        return light;
    }


    /**
     * Touch Handeling
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //Always handle Action Cancel !!!
    /*
    +Multitouch:
        -MotionEvent.getPointerCount(); determines how many pointers are currently on screen
        -ACTION_POINTER_DOWN/_UP to detect secondary pointers, if there occur two or more touch
             Events at the same time, onTouchDown wont be fired, rather ACTION_POINTER_UP
        -PointerID is stable, PointerIndex is related to the amount of touch events occur at the same time

    +ScaleGestureDetector!
     */
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getPointerCount()) {
            case 1:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchHandler.setUp(x, y, camera);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchHandler.update(x, y, camera);
                        break;
                    case MotionEvent.ACTION_UP:
                        touchHandler.reset();
                        break;
                }
                break;
            case 2:
                scaleGestureDetector.onTouchEvent(event);
                break;
            default:

                break;

        }


        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private long previousEventTime = 0;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //Log.d("debug", "onScale");
            //Log.d("debug", "detector.getCurrentSpan(); " + detector.getCurrentSpan());
            //Log.d("debug", "detector.getCurrentSpanX(); " + detector.getCurrentSpanX());
            //Log.d("debug", "detector.getCurrentSpanY(); " + detector.getCurrentSpanY());
            //Log.d("debug", "detector.getScaleFactor(); " + detector.getScaleFactor());
            long eventTime = System.currentTimeMillis();

            if (previousEventTime == 0 || (eventTime - previousEventTime) > 50) {
                previousEventTime = eventTime - 16;
            }

            if (detector.getScaleFactor() > 1) {
                camera.decreaseZoom(eventTime - previousEventTime);
                Log.d("EarthControl", "Zooming in");
            } else {
                camera.increaseZoom(eventTime - previousEventTime);
                Log.d("EarthControl", "Zooming out");
            }

            previousEventTime = eventTime;
            return true;
        }
    }

}