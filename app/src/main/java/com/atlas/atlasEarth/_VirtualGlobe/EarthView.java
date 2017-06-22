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
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geographic2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.EarthModel;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Post;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.SkyBox;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing.PointInWorldSpace;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.TouchHandeling.TouchHandler;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.RendererGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Scene.SceneState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Sun;
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
    private Sun sun;
    private SceneState sceneState;
    private RendererGL3x renderer;
    private TouchHandler touchHandler;
    private ScaleGestureDetector scaleGestureDetector;
    private Ellipsoid globeShape;
    private LinkedList<Renderable> posts;

    PointInWorldSpace pointInWorldSpace;


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

        globeShape = Ellipsoid.SCALED_WGS84;
        renderables = new ArrayList<>();
        doneQueue = new ArrayList<>();
        posts = new LinkedList<>();

    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        sun = new Sun();
        Camera camera = new Camera();

        EarthModel earthModel = new EarthModel();
        earthModel.onCreate();
        earthModel.activateVAO();
        earthModel.loadTextures(getContext());
        renderables.add(earthModel);

        SkyBox skyBox = new SkyBox(camera);
        skyBox.onCreate();
        skyBox.activateVAO();
        skyBox.loadTextures(getContext());
        renderables.add(skyBox);

        sceneState = new SceneState(camera, getContext());
        RenderStatesHolder renderStates = new RenderStatesHolder();
        renderStates.loadGlobalDefaults();

        renderer = new RendererGL3x(getContext(), renderStates, sceneState);


        touchHandler = new TouchHandler(renderer.getProjectionMatrix(), camera, getContext());

        Vector3F middle = globeShape.convertGeographicToCartesian(new Geographic2D(0, 0)).toVector3F();
        Vector3F home = globeShape.convertGeographicToCartesian(new Geographic2D(48.7904472, 11.4978895)).toVector3F();

        pointInWorldSpace = new PointInWorldSpace(getContext(), camera, middle, home);


    }

    public PointInWorldSpace getPointInWorldSpace() {
        return pointInWorldSpace;
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

        renderer.postRenderables(renderables);
        renderer.postPosts(posts);
        renderer.render(sun, sceneState);


        pointInWorldSpace.render();
    }


    @Override
    public void onPause() {
        super.onPause();
        renderer.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public Sun getSun() {
        return sun;
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

        if (touchHandler == null) {
            return false;
        }

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

                if(pan){

                        float averageCurrentY = (event.getY(0) + event.getY(1)) / 2;
                        float averageHistoricalY = event.getHistoricalY(0, 1) + event.getHistoricalY(1, 1);
                        getCamera().increasePan((averageCurrentY - averageHistoricalY) / 50);

                }else{
                scaleGestureDetector.onTouchEvent(event);
                }
                break;
            default:

                break;

        }


        return true;
    }

    // TODO: 6/22/2017 implement real panning and remove this !
    private boolean pan = false;
    public void togglePan() {
        pan = !pan;
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
            }
            return params;
        }


        @Override
        protected void onPostExecute(Renderable[] renderables) {
            doneQueue.addAll(Arrays.asList(renderables));
        }
    }
}