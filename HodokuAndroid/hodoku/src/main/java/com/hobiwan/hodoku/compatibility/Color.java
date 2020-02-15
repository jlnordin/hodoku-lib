package com.hobiwan.hodoku.compatibility;

public class Color {

    public static final Color BLACK = new Color(0xFF000000);
    public static final Color WHITE = new Color(0xFFFFFFFF);
    public static final Color LIGHT_GRAY = new Color(0xFFCCCCCC);
    public static final Color RED = new Color(0xFFFF0000);
    public static final Color BLUE = new Color(0xFF0000FF);

    private int mColor = 0;

    public Color(int color) {
        mColor = color;
    }

    public Color(int red, int green, int blue) {
        mColor = 0xFF000000 |
                (red & 0xFF) << 16 |
                (green & 0xFF) << 8 |
                (blue & 0xFF);
    }

    public int getRGB() {
        return mColor;
    }
}
