package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing;



public class ControlInterfaces {


    public interface LightControlInterface {

        void fullLight();
        void iXLight();
        void dXLight();
        void iYLight();
        void dYLight();
        void iZLight();
        void dZLight();
    }


    public interface PIWSControlInterface {

        void ePM();
        void eVM();
        void eTM();
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


}
