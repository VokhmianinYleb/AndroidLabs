// Square.java

package com.example.lab8;

import static com.example.lab8.MainActivity.checkGlError;
import static com.example.lab8.MainActivity.loadShader;
import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Square extends Figure {
    private float coords[] = {
        // проти часової стрілки
        -0.5f, 0.5f, 0.0f, // зверху зліва
        -0.5f, -0.5f, 0.0f, // знизу зліва
        0.5f, -0.5f, 0.0f, // знизу праворуч
        0.5f, 0.5f, 0.0f // зверху праворуч
    };
    private final short drawOrder[] = {0, 1, 2, 0, 2, 3};

    public Square() {
        color[0] = 0.2f; color[1] = 0.709803922f; color[2] = 0.898039216f; color[3] = 1.0f;
        // масив байтів для координат вершин
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4); // 4 байта для float
        bb.order(ByteOrder.nativeOrder()); // порядок байтів пристрою
        vertexBuffer = bb.asFloatBuffer(); // вершинний масив
        vertexBuffer.put(coords); // додавання координат
        vertexBuffer.position(0); // на початок масиву

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // компіляція шейдерів
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // підключення шейдерів
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    /**
     * Містить інструкції OpenGL ES для малювання фігури
     * @param mvpMatrix - матриця для малювання
     */
    public void draw(float[] mvpMatrix) {
        // використовуємо програму з шейдерами
        GLES20.glUseProgram(mProgram);
        // вказівник до змінної vPosition у вершинному шейдері
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle); // включення
        // підготовка координат фігури
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        // одержати вказівник до змінної vColor у піксельному шейдері
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0); // колір фігури
        // вказівник на матрицю перетворення
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        checkGlError("glGetUniformLocation");
        // Застосування трансформації проектування і виду
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        checkGlError("glUniformMatrix4fv");
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        checkGlError("glDrawElements");
        // Відключення вершинного масиву
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        checkGlError("glDisableVertexAttribArray");
    }
}