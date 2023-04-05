// Hexagon.java

package com.example.lab8;

import static com.example.lab8.MainActivity.checkGlError;
import static com.example.lab8.MainActivity.loadShader;
import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Hexagon extends Figure{
    // проти часової стрілки
    private float coords[] = {
        0.5f, 0.866025f, 0.3f, // зверху зліва
        1.0f, 0.0f, 0.3f, // зліва
        0.5f, -0.866025f, 0.3f, // знизу зліва
        -0.5f, -0.866025f, 0.3f, // знизу праворуч
        -1.0f, 0.0f, 0.3f, // праворуч
        -0.5f, 0.866025f, 0.3f // зверху праворуч
    };
    private final short drawOrder[] = { 0, 1, 5, 5, 1, 2, 5, 2, 3, 3, 4, 5 };

    public Hexagon() {
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