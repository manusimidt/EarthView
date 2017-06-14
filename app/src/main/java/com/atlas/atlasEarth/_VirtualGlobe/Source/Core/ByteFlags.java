package com.atlas.atlasEarth._VirtualGlobe.Source.Core;


public class ByteFlags {

    public static final byte NULL = 0;

    //------Render States------//
    //DepthTest
    public static final byte GL_NEVER = 1; //The depth test never passes.
    public static final byte GL_LESS = 2; //Passes if the fragment's depth value is less than the stored depth value.
    public static final byte GL_EQUAL = 3; //Passes if the fragment's depth value is equal to the stored depth value.
    public static final byte GL_LESSTHANOREQUAL = 4; //Passes if the fragment's depth value is less than or equal to the stored depth value.
    public static final byte GL_GREATER = 5; //Passes if the fragment's depth value is greater than the stored depth value.
    public static final byte GL_NOTEQUAL = 6; //Passes if the fragment's depth value is not equal to the stored depth value.
    public static final byte GL_GREATERTHANOREQUAL = 7; //Passes if the fragment's depth value is greater than or equal to the stored depth value.
    public static final byte GL_ALWAYS = 8; //The depth test always passes.
    //~DepthTest

    //FaceCulling
    public static final byte GL_FRONT = 9;
    public static final byte GL_BACK = 10;
    public static final byte GL_FRONTANDBACK = 11;
    //~FaceCulling

    //DrawStates
    public static final byte GL_POINTS = 12;
    public static final byte GL_LINES = 13;
    public static final byte GL_LINE_LOOP = 14;
    public static final byte GL_LINE_STRIP = 15;
    public static final byte GL_TRIANGLES = 16;
    public static final byte GL_TRIANGLE_STRIP = 17;
    public static final byte GL_TRIANGLE_FAN = 18;
    //~DrawStates

    //~-----Render States------//

    //------Buffer------//
    //Buffer types
    public static final byte GL_ARRAY_BUFFER = 50;
    public static final byte GL_ELEMENTARRAY_BUFFER = 51;
    public static final byte GL_PIXELPACK_BUFFER = 52;
    public static final byte GL_PIXELUNPACK_BUFFER = 53;
    public static final byte GL_UNIFORM_BUFFER = 54;
    public static final byte GL_TEXTURE_BUFFER = 55;
    public static final byte GL_TRANSFORMFEEDBACK_BUFFER = 56;
    public static final byte GL_COPYREAD_BUFFER = 57;
    public static final byte GL_COPYWRITE_BUFFER = 58;
    //~Buffer types
    //Buffer usage hints
    public static final byte GL_STREAM_DRAW = 59;
    public static final byte GL_STREAM_READ = 60;
    public static final byte GL_STREAM_COPY = 61;
    public static final byte GL_STATIC_DRAW = 62;
    public static final byte GL_STATIC_READ = 63;
    public static final byte GL_STATIC_COPY = 64;
    public static final byte GL_DYNAMIC_DRAW = 65;
    public static final byte GL_DYNAMIC_READ = 66;
    public static final byte GL_DYNAMIC_COPY = 67;
    //~Buffer usage hints
    //~-----Buffer------//

    //------Mesh------//
    //Winding Order
    public static final byte CLOCKWISE = 98;
    public static final byte COUNTERCLOCKWISE = 99;
    //~-----Mesh------//

    //------Data Types------//
    //Vertex Attribute Data Type
    public static final byte GL_SHORT = 100;
    public static final byte GL_FLOAT = 101;
    public static final byte GL_FLOAT_VEC2 = 102;
    public static final byte GL_FLOAT_VEC3 = 103;
    public static final byte GL_FLOAT_VEC4 = 104;
    public static final byte GL_INT = 105;
    public static final byte GL_MEDIUM_FLOAT = 106;
    //~Vertex Attribute Data Type


    //Indices Type
    public static final byte GL_UNSIGNED_SHORT = 108;
    public static final byte GL_UNSIGNED_INT = 109;
    //~Indices Type

    //~-----Mesh------//


    /**
     * All negative values are not implemented in the GL3x Converter!!!
     */
    public static final byte UNKNOWN_ERROR = -1;
    //-----ShapeFiles------//
    //ShapeType
    public static final byte NULL_SHAPE = -2;
    public static final byte POINT = -3;
    public static final byte POLYLINE = -4;
    public static final byte POLYGON = -5;
    public static final byte MULTITOUCH = -6;
    public static final byte POINT_Z = -7;
    public static final byte POLYLINE_Z = -8;
    public static final byte POLYGON_Z = -9;
    public static final byte MULTI_POINT_Z = -10;
    public static final byte POINT_M = -11;
    public static final byte POLYLINE_M = -12;
    public static final byte POLYGON_M = -13;
    public static final byte MULTI_POINT_M = -14;
    public static final byte MULTIPATCH = -15;
    //~ShapeType
    //~-----ShapeFiles------//

    //-----MeshDataTypes-----//

    //~----MeshDataTypes-----//

    //-----GeocodingAPIFailureCodes----//
    public static final byte EMPTY_RESULT = -50;
    public static final byte ZERO_RESULTS = -51;
    public static final byte OVER_QUERY_LIMIT = -52;
    public static final byte REQUEST_DENIED = -53;
    public static final byte INVALID_REQUEST = -54;

    //-----GeocodingApiLocationTypes------//
    public static final byte ROOFTOP = -55;
    public static final byte RANGE_INTERPOLATED = -56;
    public static final byte GEOMETRIC_CENTER = -57;
    public static final byte APPROXIMATE = -58;


}
