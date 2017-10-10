import TridiagAlg.*;

import java.io.IOException;

public class Main{
    public static void main (String[] args) throws IOException{
        TridiagAlg ta = new TridiagAlg("1.txt");
        double[] x1 = ta.rightMethod();
        double[] x2 = ta.leftMethod();
        TridiagAlg.printVector("Решение методом правой прогонки", x1);
        TridiagAlg.printVector("Решение методом левой прогонки", x2);
        int n = ta.getN();
        double[] x = ta.checkSol(x1);
        System.out.format("3-тья неизвестная х3 = %6.05f\n", ta.counterMethod(3));
        System.out.print("Проверка решения: (");
        for (int i = 0; i < n; i++){
            System.out.print(x[i]);
            if(i!=n-1){
                System.out.print(", ");
            }
        }
        System.out.println(")");
    }
}
