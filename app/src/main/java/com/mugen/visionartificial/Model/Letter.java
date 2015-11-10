package com.mugen.visionartificial.Model;

/**
 * Created by root on 18/04/15.
 */
public class Letter {
    private static final int[][] matrixA= {{0,1,1,1},
                                          {1,0,1,0},
                                          {1,0,1,0},
                                          {0,1,1,1}};
    private static final int[][] matrixJ={{1,0,1,1},
                                          {1,0,0,1},
                                          {1,1,1,1},
                                          {1,0,0,0}};
    public static boolean isEqual(int i,int j,char l){
        int[][] letter;
        if(l=='j')
            letter=matrixJ;
        else
            letter=matrixA;
        if(letter[i][j]==1)
            return true;
        return false;
    }

}
