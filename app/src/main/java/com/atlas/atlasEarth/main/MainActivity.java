package com.atlas.atlasEarth.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Queue;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlasEarth.map.MapFragment;


public class MainActivity extends AppCompatActivity {


    LightControlInterface lightControlInterface;

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
        linkedList.insertLastLink(new Vector3D(0.2,4,2),index++);
        linkedList.insertLastLink(new Vector3D(0.,4,25),index++);
        linkedList.insertLastLink(new Vector3D(0.2,4,26),index++);
        linkedList.insertLastLink(new Vector3D(0.2,4,27),index++);
        linkedList.insertLastLink(new Vector3D(0.2,4,28),index++);
        linkedList.insertLastLink(new Vector3D(0.2,4,29),index++);
        linkedList.insertLastLink(new Vector3D(0.2,4,250),index++);

        linkedList.insertFirstLink(new Vector3D(92,14,14),999);

        linkedList.insertFirstLink(new Vector3D(92,14,14),999);

        Queue queue = new Queue();
        queue.enqueue(new TriangleIndicesInt(0,3,5));
        queue.enqueue(new TriangleIndicesInt(0,3,4));
        queue.enqueue(new TriangleIndicesInt(0,7,5));
        queue.enqueue(new TriangleIndicesInt(5,3,5));

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }

    OptionsInterface optionsControlListener;

    public void FullLight(View view) {
        optionsControlListener.fullLight();
    }

    public void setOnOptionsControlListener(OptionsInterface onOptionsControlListener) {
        this.optionsControlListener = onOptionsControlListener;
    }

    public interface OptionsInterface {
        void fullLight();
    }


    public void lightOut(View view) {
        lightControlInterface.out();
    }

    public void lightIn(View view) {
        lightControlInterface.in();
    }

    public void lightLeft(View view) {
        lightControlInterface.left();
    }

    public void lightUp(View view) {
        lightControlInterface.up();
    }

    public void lightDown(View view) {
        lightControlInterface.down();
    }

    public void lightRight(View view) {
        lightControlInterface.right();
    }

    public void setOnLightControlListener(@Nullable LightControlInterface controlListener) {
        this.lightControlInterface = controlListener;
    }

    public interface LightControlInterface {
        void left();

        void up();

        void down();

        void right();

        void in();

        void out();
    }


}

