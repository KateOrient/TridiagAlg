package TridiagAlg;

import java.io.*;
import java.util.*;

public class TridiagAlg{
    private double[] a;
    private double[] c;
    private double[] b;
    private double[] f;
    private int n;

    public TridiagAlg (String fileName) throws IOException{
        loadEquation(fileName);
    }

    public void loadEquation (String fileName) throws IOException{
        //считывание расширенной матрицы системы из файла
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        n = Integer.parseInt(reader.readLine());
        a = new double[n];
        b = new double[n];
        c = new double[n];
        f = new double[n];
        String s;
        s = reader.readLine();
        StringTokenizer st = new StringTokenizer(s);
        a[0] = 0;
        c[0] = Double.parseDouble(st.nextToken());
        b[0] = -Double.parseDouble(st.nextToken());
        for (int i = 0; i < n - 2; i++){
            st.nextToken();
        }
        f[0] = Double.parseDouble(st.nextToken());
        for (int i = 1; i < n - 1; i++){
            s = reader.readLine();
            st = new StringTokenizer(s);
            for (int j = 0; j < i - 1; j++){
                st.nextToken();
            }
            a[i] = -Double.parseDouble(st.nextToken());
            c[i] = Double.parseDouble(st.nextToken());
            b[i] = -Double.parseDouble(st.nextToken());
            for (int j = 0; j < n - i - 2; j++){
                st.nextToken();
            }
            f[i] = Double.parseDouble(st.nextToken());
        }
        s = reader.readLine();
        st = new StringTokenizer(s);
        for (int j = 0; j < n - 2; j++){
            st.nextToken();
        }
        a[n - 1] = -Double.parseDouble(st.nextToken());
        c[n - 1] = Double.parseDouble(st.nextToken());
        b[n - 1] = 0;
        f[n - 1] = Double.parseDouble(st.nextToken());
        reader.close();
    }

    public void print (){
        for (int i = 0; i < n; i++){
            System.out.print(a[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++){
            System.out.print(c[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++){
            System.out.print(b[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++){
            System.out.print(f[i] + " ");
        }
        System.out.println();
    }

    public double[] rightMethod (){
        double[] res = new double[n];
        double[] alpha = new double[n + 1];
        double[] beta = new double[n + 1];

        for (int i = 0; i <= n - 1; i++){
            alpha[i + 1] = b[i] / (c[i] - alpha[i] * a[i]);
        }
        for (int i = 0; i < n; i++){
            beta[i + 1] = (f[i] + beta[i] * a[i]) / (c[i] - alpha[i] * a[i]);
        }

        res[n - 1] = beta[n];
        for (int i = n - 2; i >= 0; i--){
            res[i] = alpha[i + 1] * res[i + 1] + beta[i + 1];
        }

        printVector("Вектор альфа", alpha);
        printVector("Вектор бета", beta);

        return res;
    }

    public double[] leftMethod (){
        double[] res = new double[n];
        double[] xi = new double[n + 1];
        double[] eta = new double[n + 1];

        for (int i = n - 1; i >= 0; i--){
            xi[i] = a[i] / (c[i] - xi[i + 1] * b[i]);
        }
        for (int i = n - 1; i >= 0; i--){
            eta[i] = (f[i] + eta[i + 1] * b[i]) / (c[i] - xi[i + 1] * b[i]);
        }

        res[0] = eta[0];
        for (int i = 0; i < n - 1; i++){
            res[i + 1] = xi[i + 1] * res[i] + eta[i + 1];
        }

        printVector("Вектор кси", xi);
        printVector("Вектор эта", eta);

        return res;
    }

    public double counterMethod (int indx){
        indx--;
        double res;
        double[] alpha = new double[n + 1];
        double[] beta = new double[n + 1];
        double[] xi = new double[n + 1];
        double[] eta = new double[n + 1];

        for (int i = 0; i <= indx; i++){
            alpha[i + 1] = b[i] / (c[i] - alpha[i] * a[i]);
        }
        for (int i = 0; i <= indx; i++){
            beta[i + 1] = (f[i] + beta[i] * a[i]) / (c[i] - alpha[i] * a[i]);
        }
        for (int i = n - 1; i >= indx + 1; i--){
            xi[i] = a[i] / (c[i] - xi[i + 1] * b[i]);
        }
        for (int i = n - 1; i >= indx + 1; i--){
            eta[i] = (f[i] + eta[i + 1] * b[i]) / (c[i] - xi[i + 1] * b[i]);
        }

        res = (beta[indx + 1] + alpha[indx + 1] * eta[indx + 1]) / (1 - alpha[indx + 1] * xi[indx + 1]);

        return res;
    }

    public double[] checkSol (double[] sol){
        double[][] matrix = new double[n][n];
        matrix[0][0] = c[0];
        matrix[0][1] = -b[0];
        for (int i = 1; i < n - 1; i++){
            matrix[i][i] = c[i];
            matrix[i][i - 1] = -a[i];
            matrix[i][i + 1] = -b[i];
        }
        matrix[n - 1][n - 1] = c[n - 1];
        matrix[n - 1][n - 2] = -a[n - 1];

        double[] discrepancy = new double[n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                discrepancy[i] += matrix[i][j] * sol[j];
            }
            discrepancy[i] -= f[i];
        }
        return discrepancy;
    }

    public int getN (){
        return n;
    }

    public static void printVector(String vectorName, double[] vector){
        System.out.print(vectorName+": (");
        for (int i = 0; i < vector.length; i++){
            System.out.format("%10.05f",vector[i]);
            if(i!=vector.length-1){
                System.out.print(", ");
            }
        }
        System.out.println(")");
    }
}
