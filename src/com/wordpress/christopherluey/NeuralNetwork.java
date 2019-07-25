package com.wordpress.christopherluey;

import java.io.*;

class NeuralNetwork {
    int iNodes;
    int hNodes;
    int oNodes;
    int h2Nodes;
    WeighedMatrix whi;
    WeighedMatrix whh;
    WeighedMatrix woh;
    WeighedMatrix hi;
    WeighedMatrix ho;
    WeighedMatrix hi2;
    WeighedMatrix ho2;
    WeighedMatrix oi;
    WeighedMatrix oo;
    WeighedMatrix in;

    NeuralNetwork(int inputs, int hiddenNo, int hiddenNo2, int outputNo) {
        iNodes = inputs;
        oNodes = outputNo;
        hNodes = hiddenNo;
        h2Nodes = hiddenNo2;
        whi = new WeighedMatrix(h2Nodes, iNodes +1);
        whh = new WeighedMatrix(hNodes, h2Nodes +1);
        woh = new WeighedMatrix(oNodes, hNodes +1);
        whi.randomizeWeights();
        whh.randomizeWeights();
        woh.randomizeWeights();
    }

    float[] output(float[] inputsArr) {
        WeighedMatrix inputs = woh.singleColumnMatrixFromArray(inputsArr);
        in = inputs;
        WeighedMatrix inputsBias = inputs.addBias();
        WeighedMatrix hiddenInputs = whi.dot(inputsBias);
        hi = hiddenInputs;
        WeighedMatrix hiddenOutputs = hiddenInputs.activate();
        ho = hiddenOutputs;
        WeighedMatrix hiddenOutputsBias = hiddenOutputs.addBias();
        WeighedMatrix hiddenInputs2 = whh.dot(hiddenOutputsBias);
        hi2 = hiddenInputs2;
        WeighedMatrix hiddenOutputs2 = hiddenInputs2.activate();
        ho2 = hiddenOutputs2;
        WeighedMatrix hiddenOutputsBias2 = hiddenOutputs2.addBias();
        WeighedMatrix outputInputs = woh.dot(hiddenOutputsBias2);
        oi = outputInputs;
        WeighedMatrix outputs = outputInputs.activate();
        oo = outputs;
        return outputs.toArray();
    }

    public void saveNeuralNetworktoTXT(int j, String type) {
        float[] whiArr = whi.toArray();
        float[] whhArr = whh.toArray();
        float[] wohArr = woh.toArray();

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < whiArr.length; i++) {
            builder.append(whiArr[i]);
            if(i < whiArr.length - 1)
                builder.append(",");
        }
        builder.append("\n");
        for(int i = 0; i < whhArr.length; i++) {
            builder.append(whhArr[i]);
            if(i < whhArr.length - 1)
                builder.append(",");
        }
        builder.append("\n");
        for(int i = 0; i < wohArr.length; i++) {
            builder.append(wohArr[i]+"");
            if(i < wohArr.length - 1)
                builder.append(",");
        }
        try {
            File file = new File("/Volumes/Lexar/neuralnets/" + type + j + ".txt");
            FileWriter writer = new FileWriter(file.getAbsoluteFile());
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void readNeuralNetworkfromTXT(String type, int j){
        String savedFile = "/Volumes/Lexar/neuralnets/" + type + j + ".txt";

        float[] whiArr = new float[whi.rows * whi.cols];
        float[] whhArr = new float[whh.rows * whh.cols];
        float[] wohArr = new float[woh.rows * woh.cols];

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(savedFile));
            String line = "";
            int row = 0;
            while((line = reader.readLine()) != null)
            {
                String[] cols = line.split(",");
                int col = 0;
                if (row == 0) {
                    for (String c : cols) {
                        whiArr[col] = Float.parseFloat(c);
                        col++;
                    }
                    row++;
                } else if (row == 1) {
                    for (String c : cols) {
                        whhArr[col] = Float.parseFloat(c);
                    }
                    row++;
                } else if (row == 2){
                    for (String c : cols) {
                        wohArr[col] = Float.parseFloat(c);
                    }
                    row++;
                }
            }

            whi.fromArray(whiArr);
            whh.fromArray(whhArr);
            woh.fromArray(wohArr);

        } catch (Exception e) { System.out.println("Error reading file"); }

    }

    public void backPropagate(float[] error, float lr, float[] finalDecision, float error2, float[] desired){
        System.out.print(" Backpropagating");
        for (int i = 0; i < woh.rows; i++){
            for (int j = 0; j < woh.cols-1; j++){
                float weight = woh.getFloat(i, j);
                float outputOutput = oo.getFloat(i, 0);
                float hiddenOutput = ho2.getFloat(j, 0);
                float outputNew = (float)1.0-outputOutput;
                float sigmoidDerived = outputOutput*outputNew;
                float errorCalc = (desired[i] - outputOutput) * -1;
                float newValue = weight - (lr*errorCalc*hiddenOutput*sigmoidDerived);
                woh.setMatrix(i, j, newValue);
            }
        }

        for (int i = 0; i < whh.rows; i++){
            for (int j = 0; j < whh.cols-1; j++) {
                float count = 0;
                for (int f = 0; f < woh.rows; f++) {
                    float outputOutput = finalDecision[f];
                    float total = error[f] * outputOutput * (1 - outputOutput) * woh.getFloat(f, i);
                    count += total;
                }
                float weight = whh.getFloat(i, j);
                float hiddenOuputfar = ho2.getFloat(i, 0);
                float hiddenOuputclose = ho.getFloat(j, 0);
                float hiddenOuputfarnew = 1 - hiddenOuputfar;
                float newWeight = weight - lr*hiddenOuputfarnew*hiddenOuputfar*hiddenOuputclose*count;
                whh.setMatrix(i, j, newWeight);
            }
        }



        for (int g = 0; g < whh.rows; g++) {
            float count = 0;
            for (int f = 0; f < woh.rows; f++) {
                float outputOutput = finalDecision[f];
                float total = error[f] * outputOutput * (1 - outputOutput) * woh.getFloat(f, g);
                count += total;
            }
            float hiddenOuputfar = ho2.getFloat(g, 0);
            float hiddenOuputfarnew = 1 - hiddenOuputfar;
            float idk = count*hiddenOuputfar*hiddenOuputfarnew;
            hi2.setMatrix(g, 0, idk);
        }

        float sup = 0;
        for(int i = 0; i < whh.cols-1; i++) {
            for (int u = 0; u < whh.rows; u++) {
                float total2 = hi2.getFloat(u, 0) * whh.getFloat(u, i);
                sup += total2;
            }
            ho.setMatrix(i, 0, sup);
        }

        for (int i = 0; i < whi.rows; i++){
            for (int j = 0; j < whi.cols-1; j++) {
                float weight = whi.getFloat(i, j);
                float hiddenOuputfar = ho.getFloat(i, 0);
                float inputs = in.getFloat(j, 0);
                float hiddenOuputfarnew = 1 - hiddenOuputfar;
                float newWeight = weight - lr*hiddenOuputfarnew*hiddenOuputfar*inputs;
                whi.setMatrix(i, j, newWeight);
            }
        }

    }

    public void colorBackPropagate(float error, float lr, float[] finalDecision){
        for (int i = 0; i < woh.rows; i++){
            for (int j = 0; j < woh.cols-1; j++){
                float weight = woh.getFloat(i, j);
                float outputOutput = oo.getFloat(i, 0);
                float hiddenOutput = ho2.getFloat(j, 0);
                float outputNew = 1-outputOutput;
                float newValue = weight + lr*error*hiddenOutput*outputOutput*outputNew;
                woh.setMatrix(i, j, newValue);
            }
        }

        for (int i = 0; i < whh.rows; i++){
            for (int j = 0; j < whh.cols-1; j++){
                float weight = whh.getFloat(i, j);
                float hiddenOuputfar = ho2.getFloat(i, 0);
                float hiddenOuputclose = ho.getFloat(j, 0);
                float hiddenOuputfarnew = 1 - hiddenOuputfar;
                float newValue = weight + lr*error*hiddenOuputclose*hiddenOuputfar*hiddenOuputfarnew;
                whh.setMatrix(i, j, newValue);
            }
        }

        for (int i = 0; i < whi.rows; i++){
            for (int j = 0; j < whi.cols-1; j++){
                float weight = whi.getFloat(i, j);
                float hiddenOutputclose = ho.getFloat(i, 0);
                float inputOuput = in.getFloat(j, 0);
                float hiddenOuputclosenew = 1 - hiddenOutputclose;
                float newValue = weight + lr*error*inputOuput*hiddenOutputclose*hiddenOuputclosenew;
                whi.setMatrix(i, j, newValue);
            }
        }
    }
}
