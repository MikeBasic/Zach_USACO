/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piepie;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.lang.Override;

/**
 *
 * @author zhemitu
 */
public class PiePie {

    private static int mNextCookieIndex = 0;
    private static ArrayList <Integer> mBessie = new ArrayList();
    private static ArrayList <Integer> mElsie = new ArrayList();
    private static ArrayList <Integer> mBfromE = new ArrayList();
    private static ArrayList <Integer> mEfromB = new ArrayList();
    private static ArrayList <Integer> mElsieIndexSortedOrg = new ArrayList();
    private static ArrayList <Integer> mBessieIndexSortedOrg = new ArrayList();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    throws IOException {
        
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        
        int N=0, D=0;
        
        try {
            File file = new File("in.txt");
            FileWriter fileWriter = new FileWriter("piepie.out");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            Scanner sc = new Scanner(file);

            // N, D
            if (sc.hasNextLine()) {
                N = sc.nextInt();
                D = sc.nextInt();
            }
        
            // Taste array
            for (int i=0; i<N; i++) {
                 mBessie.add(i, sc.nextInt());
                 mEfromB.add(i, sc.nextInt());
                 mBessieIndexSortedOrg.add(i, i);
                 System.out.println(mBessie.get(i) + " " + mEfromB.get(i));
            }
            for (int i=0; i<N; i++) {
                 mBfromE.add(i, sc.nextInt());
                 mElsie.add(i, sc.nextInt());
                 mElsieIndexSortedOrg.add(i, i);
                 System.out.println(mBfromE.get(i) + " " + mElsie.get(i));
            }
            IndexComparator bessieComp = new IndexComparator(mBessie);
            Collections.sort(mBessieIndexSortedOrg, bessieComp);
            IndexComparator ElsieComp = new IndexComparator(mElsie);
            Collections.sort(mElsieIndexSortedOrg, ElsieComp);

            sc.close();
            
            // Loop over Bessie's cookies
            for (int i=0; i<N; i++) {
                int ret = 0;
                
                ret = PieExchange(i, D);
                printWriter.printf("%d\n", ret);
                System.out.println(ret);
            }
            
            printWriter.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static int PieExchange(
            int index,
            int D) 
    {
        ArrayList <Integer> mElsieIndexSorted = new ArrayList(mBessieIndexSortedOrg);
        ArrayList <Integer> mBessieIndexSorted = new ArrayList(mElsieIndexSortedOrg);

        ArrayList <Integer> sortedIndexArray, opinionForOther, opinionForSelf;
        int target;
        int pieCount = 1;
        int stepFlag = 0;
        int ret = 0;
        
        mNextCookieIndex = index;
        
        while(true) {
            if (stepFlag == 0) {
                sortedIndexArray = mElsieIndexSorted;
                opinionForOther = mEfromB;
                opinionForSelf = mElsie;
                stepFlag = 1;
            } else {
                sortedIndexArray = mBessieIndexSorted;
                opinionForOther = mBfromE;
                opinionForSelf = mBessie;
                stepFlag = 0;
            }

            if (opinionForOther.get(mNextCookieIndex) == 0) {
                // Happy ending
                return(pieCount);
            } else {
                target = opinionForOther.get(mNextCookieIndex) + D;
            }
                    
            ret = FindMaxValueUnderD(sortedIndexArray, opinionForSelf, target);
            if (ret == 1) {
                pieCount++;
            } else {
                // Sad ending
                return(-1);
            }
        }
    }
    
    private static int FindMaxValueUnderD(
            ArrayList <Integer> sortedIndex,
            ArrayList <Integer> person,
            int target)
    {
        int ret = 0;
        int iBegin=0, iEnd=sortedIndex.size()-1;
        int iMiddle;
        
        if (target < person.get(sortedIndex.get(0))) {
            return 0; // not possible
        }

        if (target >= person.get(sortedIndex.get(iEnd))) {
            mNextCookieIndex = sortedIndex.get(iEnd);
            sortedIndex.remove(iEnd);
            return 1; // got valid value
        }

        while (true) {
            iMiddle = (iBegin + iEnd) / 2;
            if (iBegin == iMiddle) {
                // return highest one below target
                mNextCookieIndex = iMiddle;
                sortedIndex.remove(iMiddle);
                return(1);
            }  
            
            if (target > person.get(sortedIndex.get(iMiddle))) {
                iBegin = iMiddle;
            } else if (target < person.get(sortedIndex.get(iMiddle))){
                iEnd = iMiddle;
            } else {
                // Found the value, return target
                mNextCookieIndex = iMiddle;
                sortedIndex.remove(iMiddle);
                return(1);
            }
        }
    }
    
    public static class IndexComparator implements Comparator<Integer>
    {
        private final ArrayList <Integer> mArray;

        public IndexComparator(ArrayList <Integer> array)
        {
            mArray = array;
        }

        @Override
        public int compare(Integer index1, Integer index2)
        {
             // Autounbox from Integer to int to use as array indexes
            return mArray.get(index1).compareTo(mArray.get(index2));
        }
    }
}

