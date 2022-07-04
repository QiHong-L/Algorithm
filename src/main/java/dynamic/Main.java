package dynamic;

import java.util.Scanner;

/**
 * @Author: liuqihong
 * @Description: 华为机试题：购物单
 * @Date Created in  2022-07-04 22:11
 * @Modified By:
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int money = input.nextInt();
        int n = input.nextInt();
        if (n <=0 || money <= 0) {
            System.out.println(0);
            return;
        }
        Good[] gs = new Good[n+1];
        for (int i = 1; i <= n; i++) {
            int v = input.nextInt();
            int p = input.nextInt();
            int q = input.nextInt();
            gs[i] = new Good(v,p,q);
        }
        for (int i = 1; i <= n; i++) {
            if (gs[i].q > 0) {
                if (gs[gs[i].q].a1 == 0) {
                    gs[gs[i].q].setA1(i);
                }else {
                    gs[gs[i].q].setA2(i);
                }
            }
        }
        int[][] dp = new int[n+1][money+1];
        for (int i = 1; i <= n; i++) {
            int v=0,v1=0,v2=0,v3=0,tempdp=0,tempdp1=0,tempdp2=0,tempdp3=0;

            v = gs[i].v;
            tempdp = gs[i].p*v;

            if (gs[i].a1!=0) {
                v1 = gs[gs[i].a1].v + v;
                tempdp1 = tempdp + gs[gs[i].a1].v * gs[gs[i].a1].p;
            }

            if (gs[i].a2 != 0) {
                v2 = gs[gs[i].a2].v + v;
                tempdp2 = tempdp + gs[gs[i].a2].v * gs[gs[i].a2].p;
            }

            if (gs[i].a1!=0 && gs[i].a2!=0) {
                v3 = gs[gs[i].a1].v + gs[gs[i].a2].v + v;
                tempdp3 =  tempdp + gs[gs[i].a1].v * gs[gs[i].a1].p + gs[gs[i].a2].v * gs[gs[i].a2].p;
            }

            for (int j = 1; j <= money; j++) {
                if (gs[i].q > 0) {
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                    if (j >= v && v!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v]+tempdp);
                    if (j >= v1 && v1!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v1]+tempdp1);
                    if (j >= v2 && v2!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v2]+tempdp2);
                    if (j >= v3 && v3!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v3]+tempdp3);
                }
            }
        }
        System.out.println(dp[n][money]);

    }

    static class Good {
        public int v;
        public int p;
        public int q;

        public int a1 = 0;
        public int a2 = 0;

        public Good(int v,int p,int q) {
            this.v = v;
            this.p = p;
            this.q = q;
        }

        public void setA1(int a1) {
            this.a1 = a1;
        }

        public void setA2(int a2) {
            this.a2 = a2;
        }
    }
}


