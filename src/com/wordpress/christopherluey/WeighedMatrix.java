package com.wordpress.christopherluey;

import java.util.Random;
import static java.lang.Math.pow;

public class WeighedMatrix {
    int rows;
    int cols;
    float[][] matrix;

    public WeighedMatrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;

        matrix = new float[rows][cols];
    }

    public float getFloat(int row, int cols){
        return matrix[row][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setMatrix(int rows, int cols, float value) {
        matrix[rows][cols] = value;
    }

    public WeighedMatrix(float[][] m) {
        matrix = m;
        cols = m.length;
        rows = m[0].length;
    }

    public void printMatrix() {
        for (int i =0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println(" ");
        }
        System.out.println();
    }

    public void randomizeWeights() {
        for (int i =0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                Random r = new Random();
                matrix[i][j] = 2*r.nextFloat()-1;
            }
        }
    }

    public void setAllZero() {
        for (int i =0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public WeighedMatrix activate() {
        WeighedMatrix n = new WeighedMatrix(rows, cols);
        for (int i =0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                n.matrix[i][j] = sigmoid(matrix[i][j]);
            }
        }
        return n;
    }

    public float sigmoid(float x) {
        float y = (float) ((float) 1.0 / (1 + pow((float)Math.E, -x)));
        return y;
    }

    public WeighedMatrix sigmoidDerived() {
        WeighedMatrix n = new WeighedMatrix(rows, cols);
        for (int i =0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                n.matrix[i][j] = (matrix[i][j] * (1- matrix[i][j]));
            }
        }
        return n;
    }

    public WeighedMatrix singleColumnMatrixFromArray(float[] arr) {
        WeighedMatrix n = new WeighedMatrix(arr.length, 1);
        for (int i = 0; i< arr.length; i++) {
            n.matrix[i][0] = arr[i];
        }
        return n;
    }

    public WeighedMatrix addBias() {
        WeighedMatrix n = new WeighedMatrix(rows+1, 1);
        for (int i =0; i<rows; i++) {
            n.matrix[i][0] = matrix[i][0];
        }
        n.matrix[rows][0] = (float)0.5;
        return n;
    }

    WeighedMatrix dot(WeighedMatrix n) {
        WeighedMatrix result = new WeighedMatrix(rows, n.cols);

        if (cols == n.rows) {
            //for each spot in the new matrix
            for (int i =0; i<rows; i++) {
                for (int j = 0; j<n.cols; j++) {
                    float sum = 0;
                    for (int k = 0; k<cols; k++) {
                        sum+= matrix[i][k]*n.matrix[k][j];
                    }
                    result.matrix[i][j] = sum;
                }
            }
        }

        return result;
    }

    public float[] toArray() {
        float[] arr = new float[rows*cols];
        for (int i = 0; i< rows; i++) {
            for (int j = 0; j< cols; j++) {
                arr[j+i*cols] = matrix[i][j];
            }
        }
        return arr;
    }

    public void fromArray(float[] arr) {
        for (int i = 0; i< rows; i++) {
            for (int j = 0; j< cols; j++) {
                matrix[i][j] =  arr[j+i*cols];
            }
        }
    }
}
