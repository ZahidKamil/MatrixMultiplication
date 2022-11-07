package com.company;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Main {

    public static void main(String[] args) {
	// write your code here
        Matrix matrix = new Matrix();
        int LargeN = 512;
        int[][] LargeMatrix = matrix.GenerateMatrix(LargeN);
        // first create file object for file placed at location
        // specified by filepath
        String filePath = "Z:\\Cal Poly Pomona\\courses\\Fall 2022\\CS3310-DesignAnalysisAlgorithms\\Projects\\Project1\\Java\\strassen.csv";
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            int i = 2;
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[]{"Size", "Time (ms)"});
            while (i <= LargeN) {
                if ((i & (i - 1)) == 0) {
                    int[][] A = matrix.GetPartitionMatrix(LargeMatrix, i, i);
                    int[][] B = matrix.GetPartitionMatrix(LargeMatrix, i, i);
                    long time = 0;
                    for(int j=0; j<5;j++)
                    {
                        long start = System.currentTimeMillis();
                        int[][] C = matrix.strassen(i, A,B);
                        long end = System.currentTimeMillis();
                        time += (end-start);
                    }
                    time = time / 5;
                    System.out.println(i + ": " + time + "ms");
                    data.add(new String[]{Integer.toString(i), Integer.toString((int) time)});
                    i++;
                }
                else{
                    i++;
                }
            }
            writer.writeAll(data);
            // closing writer connection
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
