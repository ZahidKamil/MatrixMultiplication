package com.company;

import java.util.Random;

public class Matrix {
//    private boolean checkIntegrity = false;
    private boolean checkMatrixMultiplication(int[][]Matrix1, int[][]Matrix2)
    {
        if(Matrix1[0].length!=Matrix2.length)
            return false;
        else
            return true;
    }
    private boolean checkMatrixIntegrity(int[][]Matrix){
        int row = Matrix.length;
        int col = Matrix[0].length;
        if(((row & (row - 1)) == 0) && ((col & (col - 1)) == 0))
            return true;
        else
            return false;
    }


    public int[][]GenerateMatrix(int n)
    {
        Random rand = new Random();
        int[][]Matrix = new int[n][n];
        for(int i=0; i<n;i++)
        {
            for (int j=0; j<n;j++)
            {
                Matrix[i][j] = rand.nextInt(n);
            }
        }

        return Matrix;
    }

    public int[][]GetPartitionMatrix(int[][]InputMatrix, int m, int n)
    {
        int[][]Matrix = new int[m][n];
        for(int i=0;i<m;i++)
        {
            for (int j=0;j<n;j++)
            {
                Matrix[i][j] = InputMatrix[i][j];
            }
        }
        return Matrix;
    }

    public static void printMatrix(int [][]a)
    {
        System.out.println("Printing Matrix:");
        for (int i=0; i<a.length;i++)
        {
            for (int j=0; j<a[0].length; j++)
            {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static long nextPowerOf2(long N)
    {
        // if N is a power of two simply return it
        if ((N & (N - 1)) == 0)
            return N;
        // else set only the left bit of most significant
        // bit as in Java right shift is filled with most
        // significant bit we consider
        return 0x4000000000000000L
                >> (Long.numberOfLeadingZeros(N) - 2);
    }

    private int getLargestDimension(int row1, int col1, int row2, int col2)
    {
        int max = row1;

        if (col1 > max)
            max = col1;
        if (row2 > max)
            max = row2;
        if (row2 > max)
            max = row2;

        return max;
    }

    private int[][] padMatrix(int [][]a, int dim)
    {
        int row = a.length;
        int col = a[0].length;
        dim = (int)nextPowerOf2((long)dim);
        int [][]pad = new int[dim][dim];

        //Padding matrix requires a new matrix to be instantiated
        for (int i=0; i<pad.length; i++)
        {
            for (int j=0; j<pad[0].length; j++)
            {
                //if current idx >= row or col in the input matrix then we put in 0s.
                if((i>=row) || (j>=col)) {
                    pad[i][j] = 0;
                    continue;
                }
                pad[i][j] = a[i][j];
            }
        }

        return pad;
    }

    public int[][] bruteForceMM(int [][]A, int[][]B) {

        int dim = getLargestDimension(A.length, A[0].length, B.length, B[0].length);
        int [][]ANew = padMatrix(A, dim);
        int [][]BNew = padMatrix(B, dim);


        if(!checkMatrixMultiplication(ANew,BNew))
        {
            throw new IllegalArgumentException("Cannot do Matrix Multiplication because ANew col != BNew row");
        }

        int row1 = ANew.length;
        int col1 = ANew[0].length;
        int row2 = BNew.length;
        int col2 = BNew[0].length;

        int[][] returnArr = new int[row1][col2];

        //Time Complexity: O(n^3)
        for (int i =0; i<row1; i++)
        {
            for (int j =0; j<col2;j++)
            {
                for(int k=0; k<row2; k++)
                    returnArr[i][j] += ANew[i][k] * BNew[k][j];
            }
        }
        return returnArr;
    }
    public int[][] divideAndConquerMM(int[][]A, int[][]B)
    {
        int dim = getLargestDimension(A.length, A[0].length, B.length, B[0].length);
        int [][]ANew = padMatrix(A, dim);
        int [][]BNew = padMatrix(B, dim);

        if(!checkMatrixMultiplication(ANew,BNew))
        {
            throw new IllegalArgumentException("Cannot do Matrix Multiplication because A col != B row");
        }

        int n = ANew.length;
        int m = n/2;
        int C[][] = new int[n][n];

        if(n==1){
            C[0][0] = ANew[0][0] * BNew[0][0];
            return  C;
        }

        int[][] a11 = new int[m][m];
        int[][] a12 = new int[m][m];
        int[][] a21 = new int[m][m];
        int[][] a22 = new int[m][m];

        int[][] b11 = new int[m][m];
        int[][] b12 = new int[m][m];
        int[][] b21 = new int[m][m];
        int[][] b22 = new int[m][m];

        for(int i=0; i<m; i++)
        {
            for(int j=0; j<m; j++)
            {
                a11[i][j] = ANew[i][j];
                a12[i][j] = ANew[i][j+m];
                a21[i][j] = ANew[i+m][j];
                a22[i][j] = ANew[i+m][j+m];

                b11[i][j] = BNew[i][j];
                b12[i][j] = BNew[i][j+m];
                b21[i][j] = BNew[i+m][j];
                b22[i][j] = BNew[i+m][j+m];
            }
        }

        int [][]c11 = addMatrix(divideAndConquerMM(a11, b11), divideAndConquerMM(a12, b21),true);
        int [][]c12 = addMatrix(divideAndConquerMM(a11, b12), divideAndConquerMM(a12, b22),true);
        int [][]c21 = addMatrix(divideAndConquerMM(a21, b11), divideAndConquerMM(a22, b21),true);
        int [][]c22 = addMatrix(divideAndConquerMM(a21, b12), divideAndConquerMM(a22, b22),true);

        for (int i =0; i<m;i++)
        {
            for (int j =0; j<m; j++)
            {
                C[i][j] = c11[i][j];
                C[i][j+m] = c12[i][j];
                C[i+m][j] = c21[i][j];
                C[i+m][j+m] = c22[i][j];
            }
        }

        return C;
    }

    public static int[][] addMatrix(int [][]A, int [][]B, boolean add)
    {
        int operation;
        if(add)
        {
            operation = 1;
        }
        else{
            operation = -1;
        }
        int C[][] = new int[A.length][A.length];
        for(int i=0; i<A.length; i++)
        {
            for(int j=0; j<A.length;j++)
            {
                C[i][j] = A[i][j] + (operation*B[i][j]);
            }
        }

        return C;
    }

    public int[][] StrassenMM(int[][]A, int[][]B)
    {
        int dim = getLargestDimension(A.length, A[0].length, B.length, B[0].length);
        int [][]ANew = padMatrix(A, dim);
        int [][]BNew = padMatrix(B, dim);

        if(!checkMatrixMultiplication(ANew,BNew))
        {
            throw new IllegalArgumentException("Cannot do Matrix Multiplication because A col != B row");
        }

        int n = ANew.length;
        int m = n/2;
        int C[][] = new int[n][n];

        if(n==1)
        {
            C[0][0] = ANew[0][0] * BNew[0][0];
            return  C;
        }

        int[][] a11 = new int[m][m];
        int[][] a12 = new int[m][m];
        int[][] a21 = new int[m][m];
        int[][] a22 = new int[m][m];

        int[][] b11 = new int[m][m];
        int[][] b12 = new int[m][m];
        int[][] b21 = new int[m][m];
        int[][] b22 = new int[m][m];

        for(int i=0; i<m; i++)
        {
            for(int j=0; j<m; j++)
            {
                a11[i][j] = ANew[i][j];
                a12[i][j] = ANew[i][j+m];
                a21[i][j] = ANew[i+m][j];
                a22[i][j] = ANew[i+m][j+m];

                b11[i][j] = BNew[i][j];
                b12[i][j] = BNew[i][j+m];
                b21[i][j] = BNew[i+m][j];
                b22[i][j] = BNew[i+m][j+m];
            }
        }

        int[][]P1 = StrassenMM(a11, addMatrix(b12, b22, false));
        int[][]P2 = StrassenMM(addMatrix(a11,a12,true),b22);
        int[][]P3 = StrassenMM(addMatrix(a21,a22,true),b11);
        int[][]P4 = StrassenMM(a22, addMatrix(b21,b11,false));
        int[][]P5 = StrassenMM(addMatrix(a11,a22,true),addMatrix(b11,b22,true));
        int[][]P6 = StrassenMM(addMatrix(a12,a22,false),addMatrix(b21,b22,true));
        int[][]P7 = StrassenMM(addMatrix(a11,a21,false),addMatrix(b11,b12,true));

        //-p2+p4+p5+p6
        int[][]c11 = addMatrix(addMatrix(addMatrix(P6,P5,true),P4,true),P2,false);
        //p1+p2
        int[][]c12 = addMatrix(P1,P2,true);
        int[][]c21 = addMatrix(P3,P4, true);
        //p1-p3+p5-p7
        int[][]c22 = addMatrix(addMatrix(P1,P3,false),addMatrix(P5,P7,false),true);

        for (int i =0; i<m;i++)
        {
            for (int j =0; j<m; j++)
            {
                C[i][j] = c11[i][j];
                C[i][j+m] = c12[i][j];
                C[i+m][j] = c21[i][j];
                C[i+m][j+m] = c22[i][j];
            }
        }

        return C;
    }
}
