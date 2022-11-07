package test;

import com.company.Matrix;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixTest {
    @Test
    void GetPartitionMatrix()
    {
        Matrix matrix = new Matrix();
        int[][]LargeMatrix = {{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}};
        int[][]partitionTrue1 = {{1,2},{1,2}};
        int[][]partition1 = matrix.GetPartitionMatrix(LargeMatrix, 2,2);
        int[][]partitionTrue2 = {{1,2,3},{1,2,3}};
        int[][]partition2 = matrix.GetPartitionMatrix(LargeMatrix, 2,3);
        Arrays.equals(partitionTrue1,partition1);
        Arrays.equals(partitionTrue2,partition2);
        int[][]partitionTrue3 = {{1,2,3,4,5,6},{1,2,3,4,5,6},{1,2,3,4,5,6},{1,2,3,4,5,6}};
        int[][]partition3 = matrix.GetPartitionMatrix(LargeMatrix, 4,6);
        Arrays.equals(partitionTrue3,partition3);
    }

    @Test
    void addS()
    {
        Matrix matrix = new Matrix();
        int[][]arr1 = {{1,2},{3,4}};
        int[][]arr2 = {{1,2},{3,4}};
        int [][]c= matrix.addS(arr1,arr2);
        int [][]cTrue = {{2,4},{6,8}};
        Arrays.equals(c,cTrue);

    }

    @Test
    void add()
    {
        Matrix matrix = new Matrix();
        int[][]arr1 = {{1,2},{3,4}};
        int[][]arr2 = {{1,2},{3,4}};
        int[][]cTrue = {{2,4},{6,8}};
        int [][]c = new int[2][2];
        matrix.add(c,arr2,arr1,0,0);
        Arrays.equals(c,cTrue);
    }

    @Test
    void split()
    {
        Matrix matrix = new Matrix();
        int[][] Large = {{1,2,3,4},{1,2,3,4},{1,2,3,4},{1,2,3,4}};
        int [][]C = new int[2][2];
        int [][]CTrue = {{1,2},{1,2}};
        matrix.split(Large,C, 0,0);
        Arrays.equals(C,CTrue);
    }

    @Test
    void subtract()
    {
        Matrix matrix = new Matrix();
        int[][]arr1 = {{1,2},{3,4}};
        int[][]arr2 = {{2,4},{6,8}};
        int[][]cTrue = {{1,2},{3,4}};
        int C[][] = matrix.subtract(arr2,arr1);
        Arrays.equals(cTrue,C);
    }

    @Test
    void join()
    {
        Matrix matrix = new Matrix();
        int[][]arr1 = {{1,2},{3,4}};
        int[][]arr2 = {{1,2},{3,4}};
        int C[][] = new int[4][4];
        int[][]CTrue = {{1,2,0,0},{3,4,0,0},{0,0,0,0},{0,0,0,0}};
        matrix.join(arr1,C,0,0);
        Arrays.equals(C,CTrue);
        int[][]CTrue1 = {{1,2,1,2},{3,4,3,4},{0,0,0,0},{0,0,0,0}};
        matrix.join(arr1,C,0,2);
        Arrays.equals(C,CTrue1);
    }

    @Test
    void classicMultiply(){
        Matrix matrix = new Matrix();
        int[][]arr1 = {{1,2},{3,4}};
        int[][]arr2 = {{1,2},{3,4}};
        int C[][] = matrix.classicMultiply(2,arr1,arr2);
        int [][]CTrue = {{7,10},{15,22}};
        Arrays.equals(C,CTrue);
    }

    @Test
    void DCMultiply()
    {
        Matrix matrix = new Matrix();
        int[][]arr1 = {{1,2},{3,4}};
        int[][]arr2 = {{1,2},{3,4}};
        int C[][] = matrix.DCmultiply(arr1,arr2,0,0,0,0,2);
        int [][]CTrue = {{7,10},{15,22}};
        Arrays.equals(C,CTrue);
    }

    @Test
    void strassen()
    {
        Matrix matrix = new Matrix();
        int[][]arr1 = {{1,2},{3,4}};
        int[][]arr2 = {{1,2},{3,4}};
        int C[][] = matrix.strassen(2,arr1,arr2);
        int [][]CTrue = {{7,10},{15,22}};
        Arrays.equals(C,CTrue);
    }
}
