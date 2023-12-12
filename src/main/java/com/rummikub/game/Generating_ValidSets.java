package com.rummikub.game;
/**
 * Generates all the possible sets based on colors and numbers ofr playing Rummikub.
 */
class Generating_ValidSets{
    public static void main(String[] args) {
        String[] color = {"Red", "Black", "Blue", "Orange", "None"};
        int[] number = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};

        sameNumber3(color, number); //52
        sameNumber31J(color, number);//78

        sameNumber4(color, number);//13
        sameNumber41J(color, number);//52
        sameNumber42J(color, number);//78

        set3(color,number);//44
        set31J(color, number);//92
        set32J(color,number);//52

        set4(color, number);//40
        set41J(color, number);//124
        set42J(color, number);//132
        set5(color, number);//36
        set51J(color, number);//144
        set52J(color, number);//233

    }
    public static void sameNumber4(String[] color, int[] number){
        int count = 0;
        for (int i=1;i<=13;i++){
            System.out.print("{(" + i + "," + color[0] + ")."
                    + "(" + (i) + "," + color[1] + ")."
                    + "(" + (i) + "," + color[2] + ")."
                    + "(" + (i) + "," + color[3] + ")}");
            count++;
            //NB
            System.out.println(";{}");
        }
        //System.out.println(count);
    }

    public static void sameNumber41J(String[] color, int[] number){
        int count = 0;
        for (int i=1;i<=13;i++){
            System.out.print("{(" + i + "," + color[0] + ")."
                    + "(" + (i) + "," + color[1] + ")."
                    + "(" + (i) + "," + color[2] + ")."
                    + "(" + "0" + "," + color[4] + ")}");
            count++;
            //NB
            System.out.println(";{"+"(" + (i) + "," + color[3] + ")}");
        }
        for (int i=1;i<=13;i++){
            System.out.print("{(" + i + "," + color[0] + ")."
                    + "(" + (i) + "," + color[1] + ")."
                    + "(" + (i) + "," + color[3] + ")."
                    + "(" + "0" + "," + color[4] + ")}");
            count++;
            //NB
            System.out.println(";{"+"(" + (i) + "," + color[2] + ")}");
        }
        for (int i=1;i<=13;i++){
            System.out.print("{(" + i + "," + color[3] + ")."
                    + "(" + (i) + "," + color[1] + ")."
                    + "(" + (i) + "," + color[2] + ")."
                    + "(" + "0" + "," + color[4] + ")}");
            count++;
            //NB
            System.out.println(";{"+"(" + (i) + "," + color[0] + ")}");
        }
        for (int i=1;i<=13;i++){
            System.out.print("{(" + i + "," + color[0] + ")."
                    + "(" + (i) + "," + color[3] + ")."
                    + "(" + (i) + "," + color[2] + ")."
                    + "(" + "0" + "," + color[4] + ")}");
            count++;
            //NB
            System.out.println(";{"+"(" + (i) + "," + color[1] + ")}");
        }
        //System.out.println(count);
    }

    public static void sameNumber42J(String[] color, int[] number){
        int count=0;
        for (int i=1;i<=13;i++){
            for (int k = 0; k < 4; k++) {
                for (int j = k + 1; j < 4; j++) {
                    System.out.print("{(" + (i) + "," + color[k] + ")."
                            + "(" + (i) + "," + color[j] + ")."
                            + "(" + "0" + "," + color[4] + ")."
                            + "(" + "0" + "," + color[4] + ")}");
                    count++;
                    //NB
                    System.out.print(";{");
                    int n =1;
                    for (int nb = 0; nb < 4; nb++) {
                        if (nb != k && nb != j) {
                            System.out.print("(" + (i) + "," + color[nb] + ")");
                            if (n==1){
                                System.out.print(".");
                            }
                            n++;
                        }
                    }
                    System.out.println("}");
                }
            }
        }
        //System.out.println(count);
    }

    public static void sameNumber3(String[] color, int[] number){
        int count = 0;
        for (int w=1;w<=13;w++){
            for (int i = 0; i < 4 - 2; i++) {
                for (int j = i + 1; j < 4 - 1; j++) {
                    for (int k = j + 1; k < 4; k++) {
                        System.out.print("{(" + (w) + "," + color[i] + ")."
                                + "(" + (w) + "," + color[j] + ")."
                                + "(" + (w) + "," + color[k] + ")}");
                        count++;
                        //NB
                        System.out.println(";{"+"(" + (w) + "," + color[6-(i+j+k)] + ")}");
                    }
                }
            }
        }
        //System.out.println(count);
    }

    public static void sameNumber31J(String[] color, int[] number){
        int count=0;
        for (int i=1;i<=13;i++){
            for (int k = 0; k < 4; k++) {
                for (int j = k + 1; j < 4; j++) {
                    System.out.print("{(" + (i) + "," + color[k] + ")."
                            + "(" + (i) + "," + color[j] + ")."
                            + "(" + "0" + "," + color[4] + ")}");
                    count++;
                    //NB
                    System.out.print(";{");
                    for (int nb = 0; nb < 4; nb++) {
                        if (nb != k && nb != j) {
                            System.out.print("(" + (i) + "," + color[nb] + ")");
                        }
                    }
                    System.out.println("}");
                }
            }
        }
        //System.out.println(count);
    }

    public static void set5(String[] color, int[] number){
        int count = 0;
        for (int j=0;j<4;j++){
            for (int i=1;i<=9;i++){
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")."
                        + "(" + (i + 4) + "," + color[j] + ")}");
                count++;
                //NB
                System.out.print(";{");
                if(i!=1&&i!=9){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")."
                            + "(" + (i - 1) + "," + color[j] + ")}");
                }else if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }else{
                    System.out.print( "(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
        }
        //System.out.println(count);
    }

    public static void set51J(String[] color, int[] number){
        int count = 0;
        for (int j=0;j<4;j++){
            for (int i=1;i<=9;i++){
                if(i!=9){
                    System.out.print("{(" + "0" + "," + color[4] + ")."
                            + "(" + (i + 1) + "," + color[j] + ")."
                            + "(" + (i + 2) + "," + color[j] + ")."
                            + "(" + (i + 3) + "," + color[j] + ")."
                            + "(" + (i + 4) + "," + color[j] + ")}");
                    count++;
                    //NB
                    System.out.print(";{"+"(" + (i) + "," + color[j] + ").");
                    if(i!=1){
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i + 5) + "," + color[j] + ")}");
                    }else{
                        System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                    }
                    System.out.println();
                }
                if(i==9||i==1){
                    System.out.print("{(" + i + "," + color[j] + ")."
                            + "(" + (i + 1) + "," + color[j] + ")."
                            + "(" + (i + 2) + "," + color[j] + ")."
                            + "(" + (i + 3) + "," + color[j] + ")."
                            + "(" + "0" + "," + color[4] + ")}");
                    count++;
                    //NB
                    System.out.print(";{"+"(" + (i+4) + "," + color[j] + ").");
                    if(i==1){
                        System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                    }
                    if(i==9){
                        System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                    }
                    System.out.println();
                }
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")."
                        + "(" + (i + 4) + "," + color[j] + ")."+"}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();

                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")."
                        + "(" + (i + 4) + "," + color[j] + ")."+"}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 2) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();

                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 4) + "," + color[j] + ")."+"}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 3) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print( "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
        }
        //System.out.println(count);
    }

    public static void set52J(String[] color, int[] number){
        int count = 0;
        for (int j=0; j<4; j++){
            //10305
            for (int i=1;i<=9;i++){
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 4) + "," + color[j] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                        +"(" + (i + 3) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
            //12300
            for (int i=1;i<=11;i++){
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + "0" + "," + color[4] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                        +"(" + (i + 3) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print( "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
            for (int i = 1; i <= 9; i++) {
                //10340

                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")}");
                count++;
                //12040
                //NB
                System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                        +"(" + (i + 4) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print( "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 2) + "," + color[j] + ")."
                        +"(" + (i + 4) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print( "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
                //12005
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 4) + "," + color[j] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 2) + "," + color[j] + ")."
                        +"(" + (i + 3) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print( "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
                //10045
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")."
                        + "(" + (i + 4) + "," + color[j] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                        +"(" + (i + 2) + "," + color[j] + ").");
                if(i!=1&&i!=9){
                    System.out.print( "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                }
                if(i==9){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
                if(i==9){
                    System.out.print("{"+ "(" + "0" + "," + color[4] + ")."
                            + "(" + (i + 1) + "," + color[j] + ")."
                            + "(" + (i + 2) + "," + color[j] + ")."
                            + "(" + "0" + "," + color[4] + ")."
                            + "(" + (i + 4) + "," + color[j] + ")}");
                    count++;
                    //NB
                    System.out.print(";{"+"(" + (i) + "," + color[j] + ")."
                            +"(" + (i + 3) + "," + color[j] + ").");
                    if(i!=1&&i!=9){
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i + 5) + "," + color[j] + ")}");
                    }
                    if(i==1){
                        System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                    }
                    if(i==9){
                        System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                    }
                    System.out.println();
                    System.out.print("{"+ "(" + "0" + "," + color[4] + ")."
                            + "(" + (i + 1) + "," + color[j] + ")."
                            + "(" + "0" + "," + color[4] + ")."
                            + "(" + (i + 3) + "," + color[j] + ")."
                            + "(" + (i + 4) + "," + color[j] + ")}");
                    count++;
                    //NB
                    System.out.print(";{"+"(" + (i) + "," + color[j] + ")."
                            +"(" + (i + 2) + "," + color[j] + ").");
                    if(i!=1&&i!=9){
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i + 5) + "," + color[j] + ")}");
                    }
                    if(i==1){
                        System.out.print("(" + (i + 5) + "," + color[j] + ")}");
                    }
                    if(i==9){
                        System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                    }
                    System.out.println();
                }

            }
            //System.out.println(count);
        }
    }

    public static void set4(String[] color, int[] number){
        int count = 0;
        for (int j=0;j<4;j++){
            for (int i=1;i<=10;i++){
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")}");
                count++;
                //NB
                System.out.print(";{");
                if(i!=1&&i!=10){
                    System.out.print("(" + (i + 4) + "," + color[j] + ")."
                            + "(" + (i - 1) + "," + color[j] + ")}");
                }else if(i==1){
                    System.out.print("(" + (i + 4) + "," + color[j] + ")}");
                }else{
                    System.out.print( "(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
        }
        //System.out.println(count);
    }

    public static void set41J(String[] color, int[] number){
        int count=0;
        for (int j=0; j<4; j++){
            //three consecutive numbers
            for (int i=1;i<=11;i++){
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 3) + "," + color[j] +  ").");
                if(i!=1&&i!=10){
                    System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==10){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ").");
                if(i!=1&&i!=10){
                    System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==10){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + (i + 3) + "," + color[j] + ")}");
                count++;
                //NB
                System.out.print(";{"+"(" + (i + 2) + "," + color[j] +  ").");
                if(i!=1&&i!=10){
                    System.out.print( "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print("(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==10){
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
        }
        //System.out.println(count);
    }

    public static void set42J(String[] color, int[] number){
        int count = 0;
        for(int c = 0; c<4; c++){
            for (int i = 1; i <= 12; i++) {
                int j = c;
                System.out.print("{(" + i + "," + color[c] + ")."
                        + "(" + (i + 1) + "," + color[c] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + "0" + "," + color[4] + ")}");
                //NB
                if(i!=1&&i<10){
                    System.out.print(";{"+"(" + (i + 2) + "," + color[j] + ")."
                            +"(" + (i + 3) + "," + color[j] + ").");
                    System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==1){
                    System.out.print(";{"+"(" + (i + 2) + "," + color[j] + ")."
                            +"(" + (i + 3) + "," + color[j] + ").");
                    System.out.print("(" + (i + 4) + "," + color[j] + ")}");
                }
                if(i==10){
                    System.out.print(";{"+"(" + (i + 2) + "," + color[j] + ")."
                            +"(" + (i + 3) + "," + color[j] + ").");
                    System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                }
                if(i==11){
                    System.out.print(";{"+"(" + (i + 2) + "," + color[j] + ").");
                    System.out.print("(" + (i - 1) + "," + color[j] + ").");
                    System.out.print("(" + (i - 2) + "," + color[j] + ").");
                    System.out.print("(" + (i - 3) + "," + color[j] + ")}");
                }else if (i==12){
                    System.out.print(";{(" + (i - 1) + "," + color[j] + ").");
                    System.out.print("(" + (i - 2) + "," + color[j] + ").");
                    System.out.print("(" + (i - 3) + "," + color[j] + ")}");
                }
                System.out.println();
                count++;
                if(i<=11){
                    System.out.print("{(" + i + "," + color[c] + ")."
                            + "(" + "0" + "," + color[4] + ")."
                            + "(" + (i + 2) + "," + color[c] + ")."
                            + "(" + "0" + "," + color[4] + ")}");
                    count++;
                    //NB
                    if(i!=1&&i<10){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                                +"(" + (i + 3) + "," + color[j] + ").");
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i + 4) + "," + color[j] + ")}");
                    }
                    if(i==1){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                                +"(" + (i + 3) + "," + color[j] + ").");
                        System.out.print("(" + (i + 4) + "," + color[j] + ")}");
                    }
                    if(i==10){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                                +"(" + (i + 3) + "," + color[j] + ").");
                        System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                    }if(i==11){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                                +"(" + (i - 1) + "," + color[j] + ")."
                                +"(" + (i - 2) + "," + color[j] + ")}");
                    }
                    System.out.println();
                }
                if(i<=10){
                    System.out.print("{(" + i + "," + color[c] + ")."
                            + "(" + "0" + "," + color[4] + ")."
                            + "(" + "0" + "," + color[4] + ")."
                            + "(" + (i + 3) + "," + color[c] + ")}");
                    count++;
                    //NB
                    System.out.print(";{"+"(" + (i + 1) + "," + color[j] + ")."
                            +"(" + (i + 2) + "," + color[j] + ").");
                    if(i!=1&&i!=10){
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i + 4) + "," + color[j] + ")}");
                    }
                    if(i==1){
                        System.out.print("(" + (i + 4) + "," + color[j] + ")}");
                    }
                    if(i==10){
                        System.out.print("(" + (i - 1) + "," + color[j] + ")}");
                    }
                    System.out.println();
                }
            }
        }
        //System.out.println(count);
    }

    public static void set3(String[] color, int[] number){
        int count = 0;
        for (int j=0;j<4;j++){
            for (int i=1;i<=11;i++){
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + (i + 2) + "," + color[j] + ")}");
                count++;
                System.out.print(";{");
                if(i!=1&&i!=11){
                    System.out.print("(" + (i + 3) + "," + color[j] + ")."
                            + "(" + (i - 1) + "," + color[j] + ")}");
                }else if(i==1){
                    System.out.print("(" + (i + 3) + "," + color[j] + ")}");
                }else{
                    System.out.print( "(" + (i - 1) + "," + color[j] + ")}");
                }
                System.out.println();
            }
        }
        //System.out.println(count);
    }

    public static void set31J(String[] color, int[] number){
        int count = 0;
        for(int j = 0; j<4; j++){
            for (int i = 1; i <= 12; i++) {
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + (i + 1) + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")}");
                //NB
                if(i!=1&&i<11){
                    if(i==2){
                        System.out.print(";{"+"(" + (i + 2) + "," + color[j] +  ").");
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i + 3) + "," + color[j] + ")}");
                    }else{
                        System.out.print(";{"+"(" + (i + 2) + "," + color[j] +  ").");
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i - 2) + "," + color[j] + ")."
                                + "(" + (i + 3) + "," + color[j] + ")}");
                    }
                }
                if(i==1){
                    System.out.print(";{"+"(" + (i + 2) + "," + color[j] +  ").");
                    System.out.print("(" + (i + 3) + "," + color[j] + ")}");
                }
                if(i==11){
                    System.out.print(";{"+"(" + (i + 2) + "," + color[j] +  ").");
                    System.out.print("(" + (i - 1) + "," + color[j] +  ").");
                    System.out.print("(" + (i - 2) + "," + color[j] + ")}");
                }
                if(i==12){
                    System.out.print(";{"+"(" + (i - 1) + "," + color[j] +  ").");
                    System.out.print("(" + (i - 2) + "," + color[j] + ")}");

                }
                System.out.println();
                if(i<=11){
                    System.out.print("{(" + i + "," + color[j] + ")."
                            + "(" + "0" + "," + color[4] +")."
                            + "(" + (i + 2) + "," + color[j] +")}");
                    count++;
                    //NB
                    if(i!=1&&i<11){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ").");
                        System.out.print("(" + (i - 1 ) + "," + color[j] + ")."
                                + "(" + (i + 3) + "," + color[j] + ")}");
                    }
                    if(i==1){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ").");
                        System.out.print("(" + (i + 3) + "," + color[j] + ")}");
                    }
                    if(i==11){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ").");
                        System.out.print("(" + (i - 1) + "," + color[j] +  ")}");
                    }
                    if(i==12){
                        System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ").");
                        System.out.print("(" + (i - 1) + "," + color[j] +  ").");
                        System.out.print("(" + (i - 2) + "," + color[j] + ")}");

                    }
                    System.out.println();
                }
                count++;
            }
        }
        //System.out.println(count);
    }

    public static void set32J(String[] color, int[] number){
        int count = 0;
        for(int j = 0;j<4; j++){
            for(int i=1;i<=13;i++){
                int c=0;
                System.out.print("{(" + i + "," + color[j] + ")."
                        + "(" + "0" + "," + color[4] + ")."
                        + "(" + "0" + "," + color[4] + ")}");
                count++;
                //NB RUN
                if(i>3&&i<11){
                    System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ")."
                            + "(" + (i + 2 ) + "," + color[j] + ")."
                            + "(" + (i + 3 ) + "," + color[j] + ")."
                            + "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i - 2 ) + "," + color[j] + ")."
                            + "(" + (i - 3 ) + "," + color[j] + ")." );

                }
                if(i==13){
                    System.out.print(";{"+"(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i - 2 ) + "," + color[j] + ")."
                            + "(" + (i - 3 ) + "," + color[j] + ")." );
                }if(i==1){
                    System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ")."
                            + "(" + (i + 2 ) + "," + color[j] + ")."
                            + "(" + (i + 3 ) + "," + color[j] + ").");
                }if(i==12){
                    System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ")."
                            + "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i - 2 ) + "," + color[j] + ")."
                            + "(" + (i - 3 ) + "," + color[j] + ")." );
                }if(i==2){
                    System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ")."
                            + "(" + (i + 2 ) + "," + color[j] + ")."
                            + "(" + (i + 3 ) + "," + color[j] + ")."
                            + "(" + (i - 1 ) + "," + color[j] + ")."  );
                }if(i==11){
                    System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ")."
                            + "(" + (i + 2 ) + "," + color[j] + ")."
                            + "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i - 2 ) + "," + color[j] + ")."
                            + "(" + (i - 3 ) + "," + color[j] + ")." );
                }if(i==3){
                    System.out.print(";{"+"(" + (i + 1) + "," + color[j] +  ")."
                            + "(" + (i + 2 ) + "," + color[j] + ")."
                            + "(" + (i + 3 ) + "," + color[j] + ")."
                            + "(" + (i - 1 ) + "," + color[j] + ")."
                            + "(" + (i - 2 ) + "," + color[j] + ")." );
                }
                //NB GROUP
                for(int x=0;x<4;x++){
                    if(x!=j){
                        c++;
                        System.out.print("(" + (i) + "," + color[x] +  ")");
                        if(c<3){
                            System.out.print(".");
                        }

                    }
                }
                System.out.println("}");

            }


        }

    }

}


