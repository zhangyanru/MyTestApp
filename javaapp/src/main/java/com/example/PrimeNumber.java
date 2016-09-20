package com.example;

/**
 * Created by yanru.zhang on 16/9/20.
 * Email:yanru.zhang@renren-inc.com
 *
 * 计算N内的所有素数
 */
public class PrimeNumber {

    //循环N内的每一个数字i,比较i能否被2-根号i直接的数整除，如果能，就不是素数，不能，就是素数
    public void prime1(int n){
        System.out.println("\n" + n + "内的所有素数:");
        if(n <= 1) {
            System.out.println("必须输入一个大于1的数");
            return;
        }

        int i,j;
        for(i=2; i<=n;i++){
            for(j=2;j<=Math.sqrt(i);j++){
                if(i%j == 0){
                    break;
                }
            }
            if(j > Math.sqrt(i)){
                System.out.print(i + " ");
            }
        }
    }

    //只要2-sqrt(i)间的质数不能整除i即i是素数
    public void prime2(int n){
        System.out.println("\n" + n + "内的所有素数:");
        if(n <= 1) {
            System.out.println("必须输入一个大于1的数");
            return;
        }

        int count = 0;
        int[] primes = new int[n];
        primes[count++] = 2;
        System.out.print("2 ");
        int i,j;
        for(i=3;i<=n;i++){
            int icount = 0;
            int sqrtI = (int)Math.sqrt(i);
            while (icount < count && primes[icount] <= sqrtI){
                icount++;
            }
            for(j=0;j<icount;j++){
                if(i % primes[j] ==0){
                    break;
                }
            }
            //i不能被2-sqrt(i)间的素数整除，自然不能被其他整数整除，所以为素数
            if(j >= icount){
                System.out.print(i + " ");
                primes[count++] = i;
            }
        }
    }

    public void prime3(int n){
        System.out.println("\n" + n + "内的所有素数:");
        if(n <= 1) {
            System.out.println("必须输入一个大于1的数");
            return;
        }
        int i,j         ;
        int[] primes = new int[n+1];

        for(i=2;i<=    n;i++){
            primes[i] = i;
        }

        for(i=2;i<=n-1;i++){
            for(j=2;j*i<=n;j++){
                primes[i*j] = 0;
            }
        }

        for(i=2;i<=n;i++){
            if (primes[i] != 0){
                System.out.print(i + " ");
            }
        }
    }
}
