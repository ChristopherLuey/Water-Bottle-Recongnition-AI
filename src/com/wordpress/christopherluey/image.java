package com.wordpress.christopherluey;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class image {

    private BufferedImage img;
    private int[][] rMatrix;
    private int[][] gMatrix;
    private int[][] bMatrix;
    private float[][] grMatrix;
    private int[][] grConMatrix;
    private int[][] rConMatrix;
    private int[][] gConMatrix;
    private int[][] bConMatrix;
    private float[][] rConMatrixFloat;
    private float[][] gConMatrixFloat;
    private float[][] bConMatrixFloat;
    private float[][] grConMatrixFloat;
    private float[] idkGrSomething;

    private float[] rFloatMatrix;
    private float[] gFloatMatrix;
    private float[] bFloatMatrix;
    private float[] grFloatMatrix;

    public image(BufferedImage i){
        img = i;

    }

    public float[] parseImage(){
        //System.out.println("Parsing Image");
        int rows = img.getWidth();
        int cols = img.getHeight();
        gMatrix = new int[rows][cols];
        bMatrix = new int[rows][cols];
        rMatrix = new int[rows][cols];
        grMatrix = new float[rows][cols];
        idkGrSomething = new float[10000];

        for(int i = img.getMinY(); i<rows; i++){
            for(int j = img.getMinX(); j<cols; j++){
                Color color = new Color(img.getRGB(j, i));
                rMatrix[i][j] = color.getRed();
                bMatrix[i][j] = color.getBlue();
                gMatrix[i][j] = color.getGreen();
                grMatrix[i][j] = 1- ((float)(rMatrix[i][j] + bMatrix[i][j] + gMatrix[i][j])) / (float)765;
                float idk = ((float)(rMatrix[i][j] + bMatrix[i][j] + gMatrix[i][j])) / (float)3;
                idkGrSomething[i*100+j] = grMatrix[i][j];
                //System.out.println(idkGrSomething[i*j]);
            }
        }
//
//        int y = 0;
//        for (int j = 0; j<idkGrSomething.length; j++){
//            if(0.2>idkGrSomething[j] && idkGrSomething[j]>0.1){
//                System.out.print("0 ");
//            } else if(idkGrSomething[j]>0.2 && idkGrSomething[j]<0.3){
//                System.out.print("1 ");
//            } else if(idkGrSomething[j]>0.3 && idkGrSomething[j]<0.4){
//                System.out.print("2 ");
//            } else if(idkGrSomething[j]>0.4 && idkGrSomething[j]<0.5){
//                System.out.print("3 ");
//            }else if(idkGrSomething[j]>0.5 && idkGrSomething[j]<0.6){
//                System.out.print("4 ");
//            }else if(idkGrSomething[j]>0.6 && idkGrSomething[j]<0.7){
//                System.out.print("5 ");
//            }else if(idkGrSomething[j]>0.7 && idkGrSomething[j]<0.8){
//                System.out.print("6 ");
//            }else if(idkGrSomething[j]>0.8 && idkGrSomething[j]<0.9){
//                System.out.print("7 ");
//            }else if(idkGrSomething[j]>0.9 && idkGrSomething[j]<=1){
//                System.out.print("8 ");
//            }
//            else{
//                System.out.print("- ");
//            }
//            y++;
//            if(y==100){
//                System.out.println();
//                y=0;
//            }
//        }

//        for(int i = 0; i< grMatrix.length; i++){
//            for(int j = 0; j<grMatrix[0].length;j++){
//                if(grMatrix[i][j]>0.3){
//                    System.out.print("0 ");
//                } else{
//                    System.out.print("- ");
//                }
//            }
//            System.out.println();
//        }
        return idkGrSomething;
    }

    public void consolidate(){
        //System.out.println("Consolidating Image");
        int x = img.getWidth() / 100;
        int y = img.getHeight() / 100;
        int a = img.getHeight() - (img.getHeight()%100);
        int b = img.getWidth() - (img.getWidth()%100);
        rConMatrix = new int[100][100];
        bConMatrix = new int[100][100];
        gConMatrix = new int[100][100];
        grConMatrix = new int[100][100];
        int rAvg, bAvg, gAvg, grAvg, rVal, gVal, bVal, grVal;
        rAvg = bAvg = gAvg = grAvg = 0;
        for (int i = 0; i < 100; i+=1) {
            for (int j = 0; j < 100; j++){
                for (int t = img.getMinX(); t < x; t++){
                    for (int o = img.getMinY(); o < y; o++){
                        rVal = rMatrix[j*x+t][i*y+o];
                        //grVal = grMatrix[j*x+t][i*y+o];
                        gVal = gMatrix[j*x+t][i*y+o];
                        bVal = bMatrix[j*x+t][i*y+o];
                        rAvg+=rVal;
                        //grAvg+=grVal;
                        gAvg+=gVal;
                        bAvg+=bVal;
                        int idk = x*y;
                        rConMatrix[i][j] = rAvg/idk;
                        gConMatrix[i][x] = gAvg/idk;
                        bConMatrix[i][x] = bAvg/idk;
                        //grConMatrix[i][x] = grAvg/idk;

                    }
                }
                rAvg = gAvg = bAvg = grAvg = 0;
            }
        }
    }

    public void produceFloatMatricies(){
        //System.out.println("Matrix Converting to Float");
        rConMatrixFloat = new float[rConMatrix.length][rConMatrix[0].length];
        gConMatrixFloat = new float[gConMatrix.length][gConMatrix[0].length];
        bConMatrixFloat = new float[bConMatrix.length][bConMatrix[0].length];
        grConMatrixFloat = new float[grConMatrix.length][grConMatrix[0].length];


        for (int i = 0; i < rConMatrix.length; i++){
            for (int j = 0; j <rConMatrix[i].length; j++){
                rConMatrixFloat[i][j] = ((float)rConMatrix[i][j]) / (float)255;
                gConMatrixFloat[i][j] = ((float)gConMatrix[i][j]) / (float)255;
                bConMatrixFloat[i][j] = ((float)bConMatrix[i][j]) / (float)255;
                float val = (float)grConMatrix[i][j];
                grConMatrixFloat[i][j] = val / 255;
            }
        }
    }

    public void produce1DFloatMatricies(){
        //System.out.println("Matrix Producing 1D Array");
        rFloatMatrix = new float[10000];
        gFloatMatrix = new float[10000];
        bFloatMatrix = new float[10000];
        grFloatMatrix = new float[10000];

        int count = 0;

        for (int i = 0; i<100; i++){
            for (int j = 0; j<100; j++){
                rFloatMatrix[count] = rConMatrixFloat[i][j];
                count++;
            }
        }

        count = 0;

        for (int i = 0; i<100; i++){
            for (int j = 0; j<100; j++){
                bFloatMatrix[count] = bConMatrixFloat[i][j];
                count++;
            }
        }

        count = 0;

        for (int i = 0; i<100; i++){
            for (int j = 0; j<100; j++){
                gFloatMatrix[count] = gConMatrixFloat[i][j];
                count++;
            }
        }

        count = 0;

        for (int i = 0; i<100; i++){
            for (int j = 0; j<100; j++){
                grFloatMatrix[count] = grConMatrixFloat[i][j];
                count++;
            }
        }
    }

    public float[] getrFloatMatrix() {
        return rFloatMatrix;
    }

    public float[] getgFloatMatrix() {
        return gFloatMatrix;
    }

    public float[] getbFloatMatrix() {
        return bFloatMatrix;
    }

    public float[] getGrFloatMatrix() {
        return grFloatMatrix;
    }

    public void printGrFloatMatrix() {
        for(int i = 0; i < grFloatMatrix.length; i++){
            System.out.print(grFloatMatrix[i] + " ");
        }
    }

    public float[] getIdkGrSomething() {
//        int x = 0;
//        for (int i = 0; i<idkGrSomething.length; i++){
//            System.out.printf("%.2f", idkGrSomething[i]);
//            System.out.print(" ");
//            x++;
//            if(x==100){
//                System.out.println();
//                x=0;
//            }
//        }

        int y = 0;
        for (int j = 0; j<idkGrSomething.length; j++){
            if(idkGrSomething[j]>0.4){
                System.out.print("0 ");
            } else{
                System.out.print("- ");
            }
            y++;
            if(y==100){
                System.out.println();
                y=0;
            }
        }
        return idkGrSomething;
    }

}
