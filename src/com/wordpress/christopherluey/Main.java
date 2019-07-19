package com.wordpress.christopherluey;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        ArrayList<String> imageType = new ArrayList<>();
        NeuralNetwork ultimateBrain;
        float[] finalDecision;
        float learningRate = (float) 1;
        float reductionRate = (float)10;
        int successes = 0;
        int attempts = 0;
        float successRate = (float)0.0;



        System.out.println("Creating Neural Networks...");

        ultimateBrain = new NeuralNetwork(10000, 5000, 5);

        //loadBrains(ultimateBrain);

        ArrayList<File> files = null;
        try {
            System.out.println("Reading Training Data");
            File f = new File("/Volumes/Lexar/image");
            files = new ArrayList<>(Arrays.asList(Objects.requireNonNull(f.listFiles())));
            files.remove(0);
            //Collections.shuffle(files);
            for (File file : files) {
                BufferedImage buffer = ImageIO.read(file);
                images.add(buffer);
                imageType.add(file.getName());
            }

        } catch (Exception e) {
            System.out.println("Error");
        }

        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < images.size(); i++) {
                BufferedImage imagess = images.get(i);

                if (imagess != null) {
//                    BufferedImage resized = new BufferedImage(100, 100, imagess.getType());
//                    Graphics2D g = resized.createGraphics();
//                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//                    g.drawImage(imagess, 0, 0, 100, 100, 0, 0, imagess.getWidth(), imagess.getHeight(), null);
//                    g.dispose();

                    image img = new image(imagess);

                    attempts += 1;
                    System.out.print("Attempt: " + attempts + " ");

                    //img.parseImage();
//                    img.consolidate();
//                    img.produceFloatMatricies();
//                    img.produce1DFloatMatricies();


                    //System.out.println("Combined Decision: ");
                    //img.printGrFloatMatrix();
                    finalDecision = ultimateBrain.output(img.parseImage());

                    //System.out.println("Analyzing Floats");
                    float max = 0;
                    int maxIndex = 0;
                    for (int in = 0; in < finalDecision.length; in++) {
                        Random r = new Random();
                        float f = r.nextFloat()/6;
                        //System.out.println(f);

//                        if(finalDecision[in] > (float)0.5){
//                            finalDecision[in] = finalDecision[in] - f;
//                        } else{
//                            finalDecision[in] = finalDecision[in] + f;
//                        }

                        if (finalDecision[in] > max) {
                            max = finalDecision[in];
//                            System.out.println("max "+ max);
                            maxIndex = in;
//                            System.out.println("Max Index: " + maxIndex);
                        }
                    }
                    System.out.println(max);

                    float[] errorArray = new float[5];
                    float error;

                    String s1 = imageType.get(i);
                    System.out.println(s1);
                    String[] s2 = s1.split(" ");
                    float[] desired = new float[5];
                    if (s2[0].equals("Not_a_Water_Bottle")) {
                        desired[0] = 1;
                        desired[1] = 0;
                        desired[2] = 0;
                        desired[3] = 0;
                        desired[4] = 0;
                    } else if(s2[0].equals("8_oz")){
                        desired[0] = 0;
                        desired[1] = 1;
                        desired[2] = 0;
                        desired[3] = 0;
                        desired[4] = 0;
                    } else if(s2[0].equals("16_oz")){
                        desired[0] = 0;
                        desired[1] = 0;
                        desired[2] = 1;
                        desired[3] = 0;
                        desired[4] = 0;
                    } else if(s2[0].equals("20_oz")){
                        desired[0] = 0;
                        desired[1] = 0;
                        desired[2] = 0;
                        desired[3] = 1;
                        desired[4] = 0;
                    } else if (s2[0].equals("24_oz")){
                        desired[0] = 0;
                        desired[1] = 0;
                        desired[2] = 0;
                        desired[3] = 0;
                        desired[4] = 1;
                    }

                    if (maxIndex == 0) {
                        String s = "Not_a_Water_Bottle";
                        System.out.print(s);
                        System.out.print(" " + s2[0]);
                        if (s.equals(s2[0])) {
                            System.out.print(" " + true);
                            successes += 1;
//                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
//                            error = 1 - max;
//                            ultimateBrain.backPropagate(errorArray, learningRate/reductionRate, finalDecision, error);
                        } else {
                            System.out.print(" " + false);
                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
                            error = 1 - max;
                            ultimateBrain.backPropagate(errorArray, learningRate, finalDecision, error, desired);

                        }

                    } else if (maxIndex == 1) {
                        String s = "8_oz";
                        System.out.print(s);
                        System.out.print(" " + s2[0]);


                        if (s.equals(s2[0])) {
                            System.out.print(" " + true);
                            successes += 1;
//                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
//                            error = 1 - max;
//                            ultimateBrain.backPropagate(errorArray, learningRate/reductionRate, finalDecision, error);
                        } else {
                            System.out.print(" " + false);
                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
                            error = 1 - max;
                            ultimateBrain.backPropagate(errorArray, learningRate, finalDecision, error, desired);

                        }

                    } else if (maxIndex == 2) {
                        String s = "16_oz";
                        System.out.print(s);
                        System.out.print(" " + s2[0]);


                        if (s.equals(s2[0])) {
                            System.out.print(" " + true);
                            successes += 1;
//                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
//                            error = 1 - max;
//                            ultimateBrain.backPropagate(errorArray, learningRate/reductionRate, finalDecision, error);
                        } else {
                            System.out.print(" " + false);
                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
                            error = 1 - max;
                            ultimateBrain.backPropagate(errorArray, learningRate, finalDecision, error, desired);

                        }

                    } else if (maxIndex == 3) {
                        String s = "20_oz";
                        System.out.print(s);
                        System.out.print(" " + s2[0]);


                        if (s.equals(s2[0])) {
                            System.out.print(" " + true);
                            successes += 1;
//                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
//                            error = 1 - max;
//                            ultimateBrain.backPropagate(errorArray, learningRate/reductionRate, finalDecision, error);
                        } else {

                            System.out.print(" " + false);
                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
                            error = 1 - max;
                            ultimateBrain.backPropagate(errorArray, learningRate, finalDecision, error, desired);

                        }

                    } else if (maxIndex == 4) {
                        String s = "24_oz";
                        System.out.print(s);
                        System.out.print(" " + s2[0]);


                        if (s.equals(s2[0])) {
                            System.out.print(" " + true);
                            successes += 1;
//                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
//                            error = 1 - max;
//                            ultimateBrain.backPropagate(errorArray, learningRate/reductionRate, finalDecision, error);
                        } else {

                            System.out.print(" " + false);
                            errorArray = errorCalculation(s2[0], finalDecision, errorArray);
                            error = 1 - max;

                            ultimateBrain.backPropagate(errorArray, learningRate, finalDecision, error, desired);

                        }
                    }

                    System.out.println("");

                    //successRate = (((float) successes) / attempts) * 100;
                    //System.out.println("Success Rate: " + successRate + "%");

                    if (attempts%1000 == 0){
                        saveBrains(ultimateBrain);
                    }
                }
            }

//            try {
//                Collections.shuffle(files);
//                images.clear();
//                imageType.clear();
//                for (File file : files) {
//                    BufferedImage buffer = ImageIO.read(file);
//                    images.add(buffer);
//                    imageType.add(file.getName());
//                }
//            } catch (Exception e){}

            successRate = (((float) successes) / attempts) * 100;

        }

        saveBrains(ultimateBrain);

        System.out.println("Success Rate: " + successRate + "%");
    }

    public static void saveBrains(NeuralNetwork ultimateBrain){
        System.out.println("Saving Brains");
//        gbrain.saveNeuralNetworktoTXT(1, "gbrain");
//        rbrain.saveNeuralNetworktoTXT(1, "rbrain");
//        bbrain.saveNeuralNetworktoTXT(1, "bbrain");
        ultimateBrain.saveNeuralNetworktoTXT(1, "ultimateBrain");
    }

    public static void loadBrains (NeuralNetwork ultimateBrain){
//        System.out.println("Reading Red Brain...");
//        rbrain.readNeuralNetworkfromTXT("rbrain", 1);
//        System.out.println("Reading Blue Brain...");
//        bbrain.readNeuralNetworkfromTXT("bbrain", 1);
//        System.out.println("Reading Green Brain...");
//        gbrain.readNeuralNetworkfromTXT("gbrain", 1);
//        System.out.println("Reading Combined Brain...");
        ultimateBrain.readNeuralNetworkfromTXT("ultimateBrain", 1);
    }

    public static float[] errorCalculation(String actual, float[] finalDecision, float[] errorArray){
        if (actual.equals("Not_a_Water_Bottle")){
            errorArray[0] = 1-finalDecision[0];
            errorArray[1] = 0-finalDecision[1];
            errorArray[2] = 0-finalDecision[2];
            errorArray[3] = 0-finalDecision[3];
            errorArray[4] = 0-finalDecision[4];

//            for(float s3: errorArray){
//                System.out.println(s3);
//            }
            return errorArray;

        } else if (actual.equals("8_oz")){
            errorArray[0] = 0-finalDecision[0];
            errorArray[1] = 1-finalDecision[1];
            errorArray[2] = 0-finalDecision[2];
            errorArray[3] = 0-finalDecision[3];
            errorArray[4] = 0-finalDecision[4];

//            for(float s3: errorArray){
//                System.out.println(s3);
//            }
            return errorArray;

        } else if (actual.equals("16_oz")){
            errorArray[0] = 0-finalDecision[0];
            errorArray[1] = 0-finalDecision[1];
            errorArray[2] = 1-finalDecision[2];
            errorArray[3] = 0-finalDecision[3];
            errorArray[4] = 0-finalDecision[4];

//            for(float s3: errorArray){
//                System.out.println(s3);
//            }
            return errorArray;

        } else if (actual.equals("20_oz")){
            errorArray[0] = 0-finalDecision[0];
            errorArray[1] = 0-finalDecision[1];
            errorArray[2] = 0-finalDecision[2];
            errorArray[3] = 1- finalDecision[3];
            errorArray[4] = 0-finalDecision[4];

//            for(float s3: errorArray){
//                System.out.println(s3);
//            }

            return errorArray;

        } else if (actual.equals("24_oz")){
            errorArray[0] = 0 - finalDecision[0];
            errorArray[1] = 0 - finalDecision[1];
            errorArray[2] = 0 - finalDecision[2];
            errorArray[3] = 0 - finalDecision[3];
            errorArray[4] = 1 - finalDecision[4];

//            for(float s3: errorArray){
//                System.out.println(s3);
//            }

            return errorArray;

        } else {
            return null;
        }
    }
}
