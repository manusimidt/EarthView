package com.atlas.atlasEarth._VirtualGlobe;

import android.content.Context;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.CSConverter;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geodetic3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.EarthModel;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.RayCastedGlobe;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Post;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.CameraLookAtUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing.PointInWorldSpace;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.TouchHandeling.TouchHandler;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.RendererGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Scene.SceneState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;
import com.atlas.atlasEarth.general.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class EarthView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private static float height;
    private static float width;
    private List<Renderable> doneQueue;
    private List<Renderable> renderables;
    private Light light;
    private SceneState sceneState;
    private RendererGL3x renderer;
    private TouchHandler touchHandler;
    private ScaleGestureDetector scaleGestureDetector;
    private Ellipsoid globeShape;
    private LinkedList<Renderable> posts;

    PointInWorldSpace pointInWorldspace;


    public EarthView(Context context) {
        super(context);
        init();
    }

    public void init() {

        super.setEGLContextClientVersion(3);
        super.setEGLConfigChooser(true);
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        super.setRenderer(this);
        //Render the view only when there is a change in the drawing data
        //super.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        globeShape = Ellipsoid.ScaledWgs84;
        renderables = new ArrayList<>();
        doneQueue = new ArrayList<>();
        posts = new LinkedList<>();


    }

    @SuppressWarnings("Unchecked")
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        Ellipsoid ellipsoid = Ellipsoid.UnitSphere;
        RayCastedGlobe rayCastedGlobe = new RayCastedGlobe(getContext(), ellipsoid);
        rayCastedGlobe.onCreate();
        rayCastedGlobe.activateVAO();
        renderables.add(rayCastedGlobe);

        EarthModel earthModel = new EarthModel(getContext());
        earthModel.onCreate();
        earthModel.activateVAO();
        renderables.add(earthModel);


        light = new Light(new Vector3F(0.82f, 0.72f, 0.95f));
        Camera camera = new Camera(earthModel);
        sceneState = new SceneState(camera, getContext());
        RenderStatesHolder renderStates = new RenderStatesHolder();
        renderStates.loadGlobalDefaults();

        renderer = new RendererGL3x(getContext(), renderStates, sceneState);

        // CameraLookAtUtility.lookAtGeodeticCoordinate(camera, ellipsoid, new Geodetic2D(0, 10), 5);
        CameraLookAtUtility.lookFromTargetVectorToOrigin(camera, new Vector3F(2, 3, 1), 3);


        touchHandler = new TouchHandler(MatricesUtility.createProjectionMatrix(getContext()), camera, getContext());
        touchHandler = new TouchHandler(renderer.getProjectionMatrix(), camera, getContext());

        pointInWorldspace = new PointInWorldSpace(camera, getContext(),
                globeShape.ToVector3D(CSConverter.toRadians(new Geodetic3D(49.08147, 12.072807, 0.1))).toVector3F()
        );


    }

    public PointInWorldSpace getPointInWorldSpace() {
        return pointInWorldspace;
    }


    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES31.glViewport(0, 0, width, height);
        EarthView.width = width;
        EarthView.height = height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {


        if (doneQueue.size() != 0) {
            for (Renderable renderable : doneQueue) {
                renderable.activateVAO();
                if (renderable.hasTexture()) {
                    renderable.loadTextures(getContext());

                    if (renderable instanceof Post) {
                        posts.insertLastLink(renderable, ((Post) renderable).getId());
                    }
                }
                renderables.add(renderable);
            }
            doneQueue.clear();
        }

        renderer.postRendables(renderables);
        renderer.postPosts(posts);
        renderer.render(light, sceneState);


        pointInWorldspace.render();
    }


    @Override
    public void onPause() {
        super.onPause();
        for (Renderable renderable : doneQueue) {
            renderable.dispose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public Light getLight() {
        return light;
    }

    public List<Post> getPost() {
        List<Post> cache = new ArrayList<>();
        for (Renderable renderable : renderables) {
            if (renderable instanceof Post) {
                cache.add((Post) renderable);
            }
        }
        return cache;
    }


    public Renderable getEarth() {
        return renderables.get(0);
    }

    public void addPosts(Post... posts) {
        new WorkerThread().execute(posts);
    }

    public void removePost(int... ids) throws ArrayIndexOutOfBoundsException {
        for (int id : ids) {
            posts.removeNode(id);
        }
    }

    public Ellipsoid getEllipsoid() {
        return globeShape;
    }

    public Camera getCamera() {
        return sceneState.getCamera();
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
        super.requestRender();
        float x = event.getX();
        float y = event.getY();

        switch (event.getPointerCount()) {
            case 1:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchHandler.setUp(x, y, sceneState.getCamera());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchHandler.update(x, y, sceneState.getCamera());
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
                sceneState.getCamera().decreaseZoom(eventTime - previousEventTime);
                Log.d("EarthControl", "Zooming in");
            } else {
                sceneState.getCamera().increaseZoom(eventTime - previousEventTime);
                Log.d("EarthControl", "Zooming out");
            }

            previousEventTime = eventTime;
            return true;
        }
    }


    private class WorkerThread extends AsyncTask<Renderable, Void, Renderable[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressWarnings("varargs")
        @Override
        protected Renderable[] doInBackground(Renderable... params) {
            for (Renderable renderable : params) {
                renderable.onCreate();
                renderable.setReady();
            }
            return params;
        }


        @Override
        protected void onPostExecute(Renderable[] renderables) {
            doneQueue.addAll(Arrays.asList(renderables));
        }
    }
}