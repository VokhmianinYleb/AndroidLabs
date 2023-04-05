// Figure.java

package com.example.lab8;

import android.graphics.Color;
import android.opengl.GLES20;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Figure {
    // текст вершинного шейдеру
    protected final String vertexShaderCode =
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                " gl_Position = uMVPMatrix * vPosition;" +
                "}";

    // текст піксельного шейдеру
    protected final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    " gl_FragColor = vColor;" +
                    "}";

    protected FloatBuffer vertexBuffer;
    protected ShortBuffer drawListBuffer;
    protected int mProgram = GLES20.glCreateProgram();
    protected int mPositionHandle;
    protected int mColorHandle;
    protected int mMVPMatrixHandle;

    // число координат для кожної вершини
    protected static final int COORDS_PER_VERTEX = 3;
    protected final int vertexStride = COORDS_PER_VERTEX * 4; // 4 байта для вершини

    // колір
    protected float color[] = {1.0f, 0.0f, 0.0f, 1.0f};

    /**
     * Встановлює новий колір для фігури
     * @param color - колір фігури
     */
    public void setColor(int color) {
        this.color[0] = Color.red(color) / 255f;
        this.color[1] = Color.green(color) / 255f;
        this.color[2] = Color.blue(color) / 255f;
        this.color[3] = Color.alpha(color) / 255f;
    }
}
