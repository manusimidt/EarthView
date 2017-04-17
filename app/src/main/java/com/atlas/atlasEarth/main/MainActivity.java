package com.atlas.atlasEarth.main;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Queue;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlasEarth.map.MapFragment;


public class MainActivity extends AppCompatActivity {


    LightControlInterface lightControlInterface;
    EarthControlInterface earthControlInterface;
    PostControlInterface postControlInterface;
    CameraControlInterface cameraControlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new MapFragment();
                   /* case 1:
                        return new MapFragment();
                    case 2:
                        return new BlankFragment();
                        */
                }
                return null;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //TESTING AREA **********************************************************************************************
        LinkedList linkedList = new LinkedList();
        int index = 0;
        linkedList.insertLastLink(new Vector3D(0.2, 4, 2), index++);
        linkedList.insertLastLink(new Vector3D(0., 4, 25), index++);
        linkedList.insertLastLink(new Vector3D(0.2, 4, 26), index++);
        linkedList.insertLastLink(new Vector3D(0.2, 4, 27), index++);
        linkedList.insertLastLink(new Vector3D(0.2, 4, 28), index++);
        linkedList.insertLastLink(new Vector3D(0.2, 4, 29), index++);
        linkedList.insertLastLink(new Vector3D(0.2, 4, 250), index++);

        linkedList.insertFirstLink(new Vector3D(92, 14, 14), 999);

        linkedList.insertFirstLink(new Vector3D(92, 14, 14), 999);

        Queue queue = new Queue();
        queue.enqueue(new TriangleIndicesInt(0, 3, 5));
        queue.enqueue(new TriangleIndicesInt(0, 3, 4));
        queue.enqueue(new TriangleIndicesInt(0, 7, 5));
        queue.enqueue(new TriangleIndicesInt(5, 3, 5));

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }


    public void fullLight(View view) {
        lightControlInterface.fullLight();
    }
    public void iXLight(View view) {
        lightControlInterface.iXLight();
    }
    public void dXLight(View view) {
        lightControlInterface.dXLight();
    }
    public void iYLight(View view) {
        lightControlInterface.iYLight();
    }
    public void dYLight(View view) {
        lightControlInterface.dYLight();
    }
    public void iZLight(View view) {
        lightControlInterface.iZLight();
    }
    public void dZLight(View view) {
        lightControlInterface.dZLight();
    }

    public void iXEarth(View view) {
        earthControlInterface.iX();
    }
    public void dXEarth(View view) {
        earthControlInterface.dX();
    }
    public void iYEarth(View view) {
        earthControlInterface.iY();
    }
    public void dYEarth(View view) {
        earthControlInterface.dY();
    }
    public void iZEarth(View view) {
        earthControlInterface.iZ();
    }
    public void dZEarth(View view) {
        earthControlInterface.dZ();
    }
    public void iXREarth(View view) {
        earthControlInterface.iXR();
    }
    public void dXREarth(View view) {
        earthControlInterface.dXR();
    }
    public void iYREarth(View view) {
        earthControlInterface.iYR();
    }
    public void dYREarth(View view) {
        earthControlInterface.dYR();
    }
    public void iZREarth(View view) {
        earthControlInterface.iZR();
    }
    public void dZREarth(View view) {
        earthControlInterface.dZR();
    }

    public void iXPost(View view) {
        postControlInterface.iX();
    }
    public void dXPost(View view) {
        postControlInterface.dX();
    }
    public void iYPost(View view) {
        postControlInterface.iY();
    }
    public void dYPost(View view) {
        postControlInterface.dY();
    }
    public void iZPost(View view) {
        postControlInterface.iZ();
    }
    public void dZPost(View view) {
        postControlInterface.dZ();
    }
    public void iXRPost(View view) {
        postControlInterface.iXR();
    }
    public void dXRPost(View view) {
        postControlInterface.dXR();
    }
    public void iYRPost(View view) {
        postControlInterface.iYR();
    }
    public void dYRPost(View view) {
        postControlInterface.dYR();
    }
    public void iZRPost(View view) {
        postControlInterface.iZR();
    }
    public void dZRPost(View view) {
        postControlInterface.dZR();
    }

    public void iPitch(View view) {
        cameraControlInterface.iPitch();
    }
    public void dPitch(View view) {
        cameraControlInterface.dPitch();
    }
    public void iDist(View view) {
        cameraControlInterface.iDist();
    }
    public void dDist(View view) {
        cameraControlInterface.dDist();
    }
    public void iVA(View view) {
        cameraControlInterface.iVA();
    }
    public void dVA(View view) {
        cameraControlInterface.dVA();
    }


    public interface LightControlInterface {

        void fullLight();
        void iXLight();
        void dXLight();
        void iYLight();
        void dYLight();
        void iZLight();
        void dZLight();
    }

    public interface EarthControlInterface {

        void iX();
        void dX();
        void iY();
        void dY();
        void iZ();
        void dZ();
        void iXR();
        void dXR();
        void iYR();
        void dYR();
        void iZR();
        void dZR();
    }

    public interface PostControlInterface {

        void iX();
        void dX();
        void iY();
        void dY();
        void iZ();
        void dZ();
       void iXR();
       void dXR();
       void iYR();
       void dYR();
       void iZR();
       void dZR();
    }

    public interface CameraControlInterface {

        void iPitch();
        void dPitch();
        void iDist();
        void dDist();
        void iVA();
        void dVA();
    }

    public void setOnLightControlListener(LightControlInterface controlListener) {
        this.lightControlInterface = controlListener;
    }
    public void setOnEarthControlListener(EarthControlInterface controlListener) {
        this.earthControlInterface = controlListener;
    }
    public void setOnPostControlListener(PostControlInterface postControlListener){
        this.postControlInterface = postControlListener;
    }
    public void setOnCameraControlListener(CameraControlInterface cameraControlListener) {
        this.cameraControlInterface = cameraControlListener;
    }
}

