package com.company;
import java.util.Random;
public class Main {

    public static void main(String[] args) {
	// write your code here
        Matrix matrix = new Matrix();
        int[][] arr1 = {{1,2,3,4,5,6}, {3,4,4,4,5,6}}; //2x6
        int[][] arr2 = {{1,2}, {3,4}, {3,6}, {6,8},{6,8},{6,8},{6,8},{6,8},{6,8},{6,8},{6,8}}; //6x2
        int row = arr1.length;
        int col = arr1[0].length;

        int[][] bF = matrix.bruteForceMM(arr1,arr2);
        matrix.printMatrix(bF);

        int[][]dC = matrix.divideAndConquerMM(arr1,arr2);
        matrix.printMatrix(dC);

        int[][]sM = matrix.StrassenMM(arr1,arr2);
        matrix.printMatrix(sM);

        int a = 4; int b=2; int c=3; int d=4;
        int max = a;

        if (b > max)
            max = b;
        if (c > max)
            max = c;
        if (d > max)
            max = d;
        System.out.println(max);
    }

    public static int getLargestDimension(int row1, int col1, int row2, int col2)
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
    public static boolean checkMatrixMultiplication(int[][]Matrix1, int[][]Matrix2)
    {
        if(Matrix1[0].length!=Matrix2.length)
            return false;
        else
            return true;
    }

    public static boolean checkMatrixIntegrity(int[][]Matrix){
        int row = Matrix.length;
        int col = Matrix[0].length;
        if(((row & (row - 1)) == 0) || ((col & (col - 1)) == 0))
            return true;
        else
            return false;
    }
    static long nextPowerOf2(long N)
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
    public static int[][] padMatrix(int [][]a, int dim)
    {
        int row = a.length;
        int col = a[0].length;
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

    public static int[][]GenerateMatrix(int n)
    {
        Random rand = new Random();
        int[][]returnArr = new int[n][n];
        for(int i=0; i<n;i++)
        {
            for (int j=0; j<n;j++)
            {
                returnArr[i][j] = rand.nextInt(n);
            }
        }

        return returnArr;
    }

    public static int[][]GetPartitionMatrix(int[][]Matrix, int m, int n)
    {
        int[][]returnArr = new int[m][n];
        for(int i=0;i<m;i++)
        {
            for (int j=0;j<n;j++)
            {
                returnArr[i][j] = Matrix[i][j];
            }
        }
        return returnArr;
    }

    public static int[][] StrassenMM(int[][]A, int[][]B)
    {
        if(A[0].length != B.length)
            throw new IllegalArgumentException("Cannot do Matrix Multiplication because Matrix1 col != Matrix2 row");

        int n = A.length;
        int m = (int) Math.ceil(n/2);
        int C[][] = new int[n][n];

        if(n==1)
        {
            C[0][0] = A[0][0] * B[0][0];
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
                a11[i][j] = A[i][j];
                a12[i][j] = A[i][j+m];
                a21[i][j] = A[i+m][j];
                a22[i][j] = A[i+m][j+m];

                b11[i][j] = B[i][j];
                b12[i][j] = B[i][j+m];
                b21[i][j] = B[i+m][j];
                b22[i][j] = B[i+m][j+m];
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


    public static int[][] divideAndConquerMM(int[][]A, int[][]B)
    {
        boolean padA = false;
        boolean padB = false;
        if(A[0].length != B.length)
            throw new IllegalArgumentException("Cannot do Matrix Multiplication because Matrix1 col != Matrix2 row");

//        if(a[0].length != a.length)
//             = padMatrix(a);
//
//        if(b[0].length != b.length)
//            padMatrix(b);

        int n = A.length;
        int m = n/2;
        int C[][] = new int[n][n];

        if(n==1){
            C[0][0] = A[0][0] * B[0][0];
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
                a11[i][j] = A[i][j];
                a12[i][j] = A[i][j+m];
                a21[i][j] = A[i+m][j];
                a22[i][j] = A[i+m][j+m];

                b11[i][j] = B[i][j];
                b12[i][j] = B[i][j+m];
                b21[i][j] = B[i+m][j];
                b22[i][j] = B[i+m][j+m];
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

    public static int[][] bruteForceMM(int [][]a, int[][]b) {
        int row1 = a.length;
        int col1 = a[0].length;
        int row2 = b.length;
        int col2 = b[0].length;

        if(col1 != row2){
            System.out.println("Please provide dimensions of mxn x nxm");
            throw new IllegalArgumentException("Wrong dimensions of array");
        }

        int[][] returnArr = new int[row1][col2];

        //Time Complexity: O(n^3)
        for (int i =0; i<row1; i++)
        {
            for (int j =0; j<col2;j++)
            {
                for(int k=0; k<row2; k++)
                    returnArr[i][j] += a[i][k] * b[k][j];
            }
        }
        return returnArr;
    }




}
