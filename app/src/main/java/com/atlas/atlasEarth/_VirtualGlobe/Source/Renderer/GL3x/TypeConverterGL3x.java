package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;


public class TypeConverterGL3x {

    /**
     * Adapter Between {@link com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags} and the GLES31 flags
     */

    public static final byte Category_RENDER_MODE = 1;
    public static final byte Category_DEPTH_TESTING = 2;
    public static final byte Category_FACET_CULLING = 3;

    public static final byte Category_WINDING_ORDER = 8;
    public static final byte Category_DATA_TYPES = 9;
    public static final byte Category_BUFFER_TYPES = 10;
    public static final byte Category_BUFFER_USAGE_HINT = 11;

    /**
     * @param category Category of the flag, for validation
     * @param flag {@link com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags} flag
     * @return the {@link GLES31} FLAG
     */

    public static int convert(byte category, byte flag) {

        switch (category) {

            case Category_RENDER_MODE:
                switch (flag) {

                    case ByteFlags.GL_POINTS:
                        return GLES31.GL_POINTS;

                    case ByteFlags.GL_LINES:
                        return GLES31.GL_LINES;

                    case ByteFlags.GL_LINE_LOOP:
                        return GLES31.GL_LINE_LOOP;

                    case ByteFlags.GL_LINE_STRIP:
                        return GLES31.GL_LINE_STRIP;

                    case ByteFlags.GL_TRIANGLES:
                        return GLES31.GL_TRIANGLES;

                    case ByteFlags.GL_TRIANGLE_STRIP:
                        return GLES31.GL_TRIANGLE_STRIP;

                    case ByteFlags.GL_TRIANGLE_FAN:
                        return GLES31.GL_TRIANGLE_FAN;

                    default:
                        throw new IllegalArgumentException("Flag with ID: " + flag + " is not in the Category RENDER_MODE");
                }


            case Category_DEPTH_TESTING:
                switch (flag) {

                    case ByteFlags.GL_NEVER:
                        return GLES31.GL_NEVER;

                    case ByteFlags.GL_LESS:
                        return GLES31.GL_LESS;

                    case ByteFlags.GL_EQUAL:
                        return GLES31.GL_EQUAL;

                    case ByteFlags.GL_LESSTHANOREQUAL:
                        return GLES31.GL_LEQUAL;

                    case ByteFlags.GL_GREATER:
                        return GLES31.GL_GREATER;

                    case ByteFlags.GL_NOTEQUAL:
                        return GLES31.GL_NOTEQUAL;

                    case ByteFlags.GL_GREATERTHANOREQUAL:
                        return GLES31.GL_GEQUAL;

                    case ByteFlags.GL_ALWAYS:
                        return GLES31.GL_ALWAYS;

                    default:
                        throw new IllegalArgumentException("Flag with ID: " + flag + " is not in the Category DEPTH_TESTING");
                }


            case Category_FACET_CULLING:
                switch (flag) {
                    case ByteFlags.GL_FRONT:
                        return GLES31.GL_FRONT;

                    case ByteFlags.GL_BACK:
                        return GLES31.GL_BACK;

                    case ByteFlags.GL_FRONTANDBACK:
                        return GLES31.GL_FRONT_AND_BACK;

                    default:
                        throw new IllegalArgumentException("Flag with ID: " + flag + " is not in the Category FACET_CULLING");
                }


            case Category_WINDING_ORDER:
                switch (flag) {
                    case ByteFlags.CLOCKWISE:
                        return GLES31.GL_CW;

                    case ByteFlags.COUNTERCLOCKWISE:
                        return GLES31.GL_CCW;

                    default:
                        throw new IllegalArgumentException("Flag with ID: " + flag + " is not in the Category WINDING_ORDER");
                }


            case Category_DATA_TYPES:
                switch (flag) {
                    case ByteFlags.GL_SHORT:
                        return GLES31.GL_SHORT;

                    case ByteFlags.GL_FLOAT:
                        return GLES31.GL_FLOAT;

                    case ByteFlags.GL_FLOAT_VEC2:
                        return GLES31.GL_FLOAT_VEC2;

                    case ByteFlags.GL_FLOAT_VEC3:
                        return GLES31.GL_FLOAT_VEC3;

                    case ByteFlags.GL_FLOAT_VEC4:
                        return GLES31.GL_FLOAT_VEC4;

                    case ByteFlags.GL_INT:
                        return GLES31.GL_INT;

                    case ByteFlags.GL_UNSIGNED_SHORT:
                        return GLES31.GL_UNSIGNED_SHORT;

                    case ByteFlags.GL_UNSIGNED_INT:
                        return GLES31.GL_UNSIGNED_INT;

                    case ByteFlags.GL_MEDIUM_FLOAT:
                        return GLES31.GL_MEDIUM_FLOAT;

                    default:
                        throw new IllegalArgumentException("Flag with ID: " + flag + " is not in the Category DATA_TYPES");
                }


            case Category_BUFFER_TYPES:
                switch (flag) {
                    case ByteFlags.GL_ARRAY_BUFFER:
                        return GLES31.GL_ARRAY_BUFFER;

                    case ByteFlags.GL_ELEMENTARRAY_BUFFER:
                        return GLES31.GL_ELEMENT_ARRAY_BUFFER;

                    case ByteFlags.GL_PIXELPACK_BUFFER:
                        return GLES31.GL_PIXEL_PACK_BUFFER;

                    case ByteFlags.GL_PIXELUNPACK_BUFFER:
                        return GLES31.GL_PIXEL_UNPACK_BUFFER;

                    case ByteFlags.GL_UNIFORM_BUFFER:
                        return GLES31.GL_UNIFORM_BUFFER;

                    //case  ByteFlags.GL_TEXTURE_BUFFER: return GLES31.GL_TEXTURE_BUFFER;

                    case ByteFlags.GL_TRANSFORMFEEDBACK_BUFFER:
                        return GLES31.GL_TRANSFORM_FEEDBACK_BUFFER;

                    case ByteFlags.GL_COPYREAD_BUFFER:
                        return GLES31.GL_COPY_READ_BUFFER;

                    case ByteFlags.GL_COPYWRITE_BUFFER:
                        return GLES31.GL_COPY_WRITE_BUFFER;

                    default:
                        throw new IllegalArgumentException("Flag with ID: " + flag + " is not in the Category BUFFER_TYPES");
                }


            case Category_BUFFER_USAGE_HINT:
                switch (flag) {
                    case ByteFlags.GL_STREAM_DRAW:
                        return GLES31.GL_STREAM_DRAW;

                    case ByteFlags.GL_STREAM_READ:
                        return GLES31.GL_STREAM_READ;

                    case ByteFlags.GL_STREAM_COPY:
                        return GLES31.GL_STREAM_COPY;

                    case ByteFlags.GL_STATIC_DRAW:
                        return GLES31.GL_STATIC_DRAW;

                    case ByteFlags.GL_STATIC_READ:
                        return GLES31.GL_STATIC_READ;

                    case ByteFlags.GL_STATIC_COPY:
                        return GLES31.GL_STATIC_COPY;

                    case ByteFlags.GL_DYNAMIC_DRAW:
                        return GLES31.GL_DYNAMIC_DRAW;

                    case ByteFlags.GL_DYNAMIC_READ:
                        return GLES31.GL_DYNAMIC_READ;

                    case ByteFlags.GL_DYNAMIC_COPY:
                        return GLES31.GL_DYNAMIC_COPY;

                    default:
                        throw new IllegalArgumentException("Flag with ID: " + flag + " is not in the Category BUFFER_USAGE_HINT");
                }

            default:
                throw new IllegalArgumentException("Wrong Category");
        }

    }


    public static void testForBiggerZero(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value cant be zero");
        }
    }

}
