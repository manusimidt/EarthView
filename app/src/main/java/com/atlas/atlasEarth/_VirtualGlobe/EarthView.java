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
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.EarthModel;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Post;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
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

    public static float lat = 0;
    public static float lng = 0;


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


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        EarthModel earthModel = new EarthModel(getContext());
        earthModel.onCreate();
        earthModel.activateVAO();
        renderables.add(earthModel);

        sun = new Sun();
        Camera camera = new Camera();
        sceneState = new SceneState(camera, getContext());
        RenderStatesHolder renderStates = new RenderStatesHolder();
        renderStates.loadGlobalDefaults();

        renderer = new RendererGL3x(getContext(), renderStates, sceneState);


        touchHandler = new TouchHandler(MatricesUtility.createProjectionMatrix(getContext()), camera, getContext());
        touchHandler = new TouchHandler(renderer.getProjectionMatrix(), camera, getContext());

        Vector3F middle = globeShape.convertGeographicToCartesian(new Geographic2D(0, 0)).toVector3F();
        Vector3F north = globeShape.convertGeographicToCartesian(new Geographic2D(10, 0)).toVector3F();
        Vector3F south = globeShape.convertGeographicToCartesian(new Geographic2D(-10, 0)).toVector3F();
        Vector3F west = globeShape.convertGeographicToCartesian(new Geographic2D(0, -10)).toVector3F();
        Vector3F east = globeShape.convertGeographicToCartesian(new Geographic2D(0, 10)).toVector3F();


        Vector3F first = globeShape.convertGeographicToCartesian(new Geographic2D(0, 10)).toVector3F();
        Vector3F second = globeShape.convertGeographicToCartesian(new Geographic2D(0, 20)).toVector3F();
        Vector3F third = globeShape.convertGeographicToCartesian(new Geographic2D(0, 30)).toVector3F();
        Vector3F fourth = globeShape.convertGeographicToCartesian(new Geographic2D(0, 40)).toVector3F();
        Vector3F fifth = globeShape.convertGeographicToCartesian(new Geographic2D(0, 50)).toVector3F();
        Vector3F sixth = globeShape.convertGeographicToCartesian(new Geographic2D(0, 60)).toVector3F();
        Vector3F seventh = globeShape.convertGeographicToCartesian(new Geographic2D(0, 70)).toVector3F();
        Vector3F eighth = globeShape.convertGeographicToCartesian(new Geographic2D(0, 80)).toVector3F();
        Vector3F ninth = globeShape.convertGeographicToCartesian(new Geographic2D(0, 90)).toVector3F();
        Vector3F tenth = globeShape.convertGeographicToCartesian(new Geographic2D(0, 100)).toVector3F();
        Vector3F eleventh = globeShape.convertGeographicToCartesian(new Geographic2D(0, 110)).toVector3F();
        Vector3F twelve = globeShape.convertGeographicToCartesian(new Geographic2D(0, 120)).toVector3F();
        Vector3F thirteen = globeShape.convertGeographicToCartesian(new Geographic2D(0, 130)).toVector3F();
        Vector3F fourteen = globeShape.convertGeographicToCartesian(new Geographic2D(0, 140)).toVector3F();
        Vector3F fifteen = globeShape.convertGeographicToCartesian(new Geographic2D(0, 150)).toVector3F();
        Vector3F sixteen = globeShape.convertGeographicToCartesian(new Geographic2D(0, 160)).toVector3F();
        Vector3F seventeen = globeShape.convertGeographicToCartesian(new Geographic2D(0, 170)).toVector3F();
        Vector3F eighteen = globeShape.convertGeographicToCartesian(new Geographic2D(0, 180)).toVector3F();

        Vector3F eins = globeShape.convertGeographicToCartesian(new Geographic2D(0, 0)).toVector3F();
        Vector3F zwei = globeShape.convertGeographicToCartesian(new Geographic2D(0, 90)).toVector3F();
        Vector3F drei = globeShape.convertGeographicToCartesian(new Geographic2D(0, -90)).toVector3F();
        Vector3F vier = globeShape.convertGeographicToCartesian(new Geographic2D(0, 180)).toVector3F();
        Vector3F fünf = globeShape.convertGeographicToCartesian(new Geographic2D(90, 0)).toVector3F();
        Vector3F sechs = globeShape.convertGeographicToCartesian(new Geographic2D(-90, 0)).toVector3F();


        Vector3F home = globeShape.convertGeographicToCartesian(new Geographic2D(49.1372548, 12.1245394)).toVector3F();
        Vector3F ellipsoidIntersectsZ = globeShape.convertGeographicToCartesian(new Geographic2D(0, 90)).toVector3F();
        Vector3F ellipsoidIntersectsX = globeShape.convertGeographicToCartesian(new Geographic2D(0, 0)).toVector3F();
        Vector3F ellipsoidIntersectsY = globeShape.convertGeographicToCartesian(new Geographic2D(90, 0)).toVector3F();
        Vector3F ellipsoidIntersectsNegativeY = globeShape.convertGeographicToCartesian(new Geographic2D(-90, 0)).toVector3F();
        pointInWorldSpace = new PointInWorldSpace(getContext(), camera, middle, ellipsoidIntersectsNegativeY);
        //pointInWorldSpace = new PointInWorldSpace(camera, getContext(),
        //        middle, first, second, third,
        //        fourth, fifth, sixth, seventh,
        //        eighth, ninth, tenth, eleventh,
        //        twelve, thirteen, fourteen, fifteen,
        //        sixteen, thirteen, seventeen, eighteen, home);
        //pointInWorldSpace = new PointInWorldSpace(getContext(), camera, eins, zwei, drei, vier, fünf, sechs);
        //pointInWorldSpace = new PointInWorldSpace(getContext(), camera, middle, vier, globeShape.convertGeographicToCartesian(new Geographic2D(54.19,30.35)).toVector3F());

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

        sceneState.getCamera().lookAt(new Geographic2D(lat, lng));

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
        renderer.render(sun, sceneState);


        pointInWorldSpace.render();
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