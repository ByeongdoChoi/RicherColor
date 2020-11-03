package com.example.richercolor;


/**
// Usage ColorMatrix result = new ColorMatrix( ColorMatrices.INVERT );
 [paint].setColorFilter( new ColorMatrixColorFilter(result) );
 or
 [imageview].setColorFilter( new ColorMatrixColorFilter(result) );
 */

public class ColorMatrices {
    public static final float[] IDENTITY = {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] INVERT = {
            -1, 0, 0, 0, 255,
            0, -1, 0, 0, 255,
            0, 0, -1, 0, 255,
            0, 0, 0, 1, 0
    };

    public static final float[] REDONLY = {
            1, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] GREENONLY = {
            0, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] BLUEONLY = {
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] SWAP_R_G = {
            0, 1, 0, 0, 0,
            1, 0, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] SWAP_R_B = {
            0, 0, 1, 0, 0,
            0, 1, 0, 0, 0,
            1, 0, 0, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] SWAP_G_B = {
            1, 0, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] GRAYSCALE = {
            0.2989f, 0.5870f, 0.1140f, 0, 0,
            0.2989f, 0.5870f, 0.1140f, 0, 0,
            0.2989f, 0.5870f, 0.1140f, 0, 0,
            0.0000F, 0.0000F, 0.0000F, 1, 0
    };

    public static final float[] SEPIA = {
            0.393F, 0.769F, 0.189F, 0, 0,
            0.349F, 0.686F, 0.168F, 0, 0,
            0.272F, 0.534F, 0.131F, 0, 0,
            0.000F, 0.000F, 0.000F, 1, 0
    };

    // warm 5000K 1.0000 0.7992 0.6045
    public static final float[] WARM = {
            1.000F, 0.000F, 0.000F, 0, 0,
            0.000F, 0.780F, 0.000F, 0, 0,
            0.000F, 0.000F, 0.605F, 0, 0,
            0.000F, 0.000F, 0.000F, 1, 0
    };

    // cool 8000K 0.7644 0.8139 1.0000
    public static final float[] COOL = {
            0.765F, 0.000F, 0.000F, 0, 0,
            0.000F, 0.814F, 0.000F, 0, 0,
            0.000F, 0.000F, 1.000f, 0, 0,
            0.000F, 0.000F, 0.000F, 1, 0
    };
}
