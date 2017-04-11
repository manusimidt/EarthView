package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Multithreading;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;

import java.util.List;

public class Loader extends Thread {
    private List<Renderable> requestQueue;

    public void post(Renderable object) {
        requestQueue.add(object);
    }

    @Override
    public void run() {
        for (Renderable rendable : requestQueue) {

        }
    }
}
