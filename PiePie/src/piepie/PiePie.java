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
    private static ArrayList<Integer> mBessie = new ArrayList<Integer>();
    private static ArrayList<Integer> mElsie = new ArrayList<Integer>();
    private static ArrayList<Integer> mBfromE = new ArrayList<Integer>();
    private static ArrayList<Integer> mEfromB = new ArrayList<Integer>();
    private static ArrayList<Integer> mEfromBIndexSorted = new ArrayList<Integer>();
    private static ArrayList<Integer> mBfromEIndexSorted = new ArrayList<Integer>();
    private static Queue<Integer> mNodes = new LinkedList<Integer>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
            throws IOException {

        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));

        int N = 0, D = 0;
        int pieIn, ret;

        try {
            File file = new File("piepie.in");
            FileWriter fileWriter = new FileWriter("piepie.out");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            Scanner sc = new Scanner(file);

            // N, D
            if (sc.hasNextLine()) {
                N = sc.nextInt();
                D = sc.nextInt();
            }

            int[] dist = new int[2*N];
            
            // Taste array
            for (int i = 0; i < N; i++) {
                mBessie.add(i, sc.nextInt());
                pieIn = sc.nextInt();
                mEfromB.add(i, pieIn);
                
                // distance initial values starting from Bessie
                if (pieIn == 0) {
                    dist[i] = 1;
                    mNodes.add(i);
                    System.out.println("--------------------------------");
                } else {
                    dist[i] = -1;
                    mEfromBIndexSorted.add(i);
                }
                
                System.out.println(i + " " + mBessie.get(i) + " " + mEfromB.get(i));
            }
            for (int i = 0; i < N; i++) {
                pieIn = sc.nextInt();
                mBfromE.add(i, pieIn);
                mElsie.add(i, sc.nextInt());
                
                // distance initial values starting from Bessie
                if (pieIn == 0) {
                    dist[N+i] = 1;
                    mNodes.add(N+i);
                    System.out.println("--------------------------------");
                } else {
                    dist[N+i] = -1;
                    mBfromEIndexSorted.add(i);
                }
                
                System.out.println(i + " " + mBfromE.get(i) + " " + mElsie.get(i));
            }
            IndexComparator bessieComp = new IndexComparator(mBfromE);
            Collections.sort(mBfromEIndexSorted, bessieComp);

            System.out.println("Bessie sorted: -----------");
            int j = 0;
            for (int i : mBfromEIndexSorted) {
                System.out.println(j + "  " + i + "  " + mBfromE.get(i));
                j++;
            }

            IndexComparator ElsieComp = new IndexComparator(mEfromB);
            Collections.sort(mEfromBIndexSorted, ElsieComp);

            System.out.println("Ellie sorted: -----------");
            j = 0;
            for (int i : mEfromBIndexSorted) {
                System.out.println(j + "  " + i + "  " + mEfromB.get(i));
                j++;
            }

            sc.close();

            // Traverse all the 0 tasty cookies
            while(mNodes.size() > 0)
            {
                int node = mNodes.remove();
                
                if (node < N) // B's node, find connected nodes in E
                {
                    // Give this pie from E to B and see how many choices E can give back
                    while(true)
                    {
                        int itmp2 = mBessie.get(node);

                        ret = FindCookieIndex(mBfromEIndexSorted, mBfromE, mBessie.get(node)-D, mBessie.get(node));


                        if (ret != -1)
                        {
                            int itmp1 = mBessie.get(ret);

                            mNodes.add(ret+N);
                            dist[ret+N] = dist[node] + 1;
                        }
                        else
                            break;
                    }
                }
                else // E's node, find connected nodes in B
                {
                    // Give this pie from B to E and see how many choices E can give back
                    while(true)
                    {
                        ret = FindCookieIndex(mEfromBIndexSorted, mEfromB, mElsie.get(node-N)-D, mElsie.get(node-N));
                        if (ret != -1)
                        {
                            mNodes.add(ret);
                            dist[ret] = dist[node] + 1;
                        }
                        else
                            break;
                    }
                }
            }
            
            for (int i=0; i<N; i++)
            {
                System.out.println(dist[i]);
                printWriter.println(dist[i]);
            }

            printWriter.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int FindCookieIndex(
            ArrayList<Integer> sortedIndex,
            ArrayList<Integer> pieOpinion,
            int lowBound,
            int highBound) {
        int ret = 0;
        int iBegin = 0, iEnd = sortedIndex.size() - 1;
        int iMiddle, index, target=lowBound;

        if (iEnd == -1) return(-1);
        
        if (target <= pieOpinion.get(sortedIndex.get(0)))
       {
            System.out.println("--------------------");
            System.out.println("target = " + target);
            System.out.println("person.get(sortedIndex.get(iBegin)) = " + pieOpinion.get(sortedIndex.get(iBegin)));
            System.out.println("--------------------");

           ret = -1;
           if ((pieOpinion.get(sortedIndex.get(0)) >= lowBound)
                   && (pieOpinion.get(sortedIndex.get(0)) <= highBound)) {
               ret = sortedIndex.get(0);
               sortedIndex.remove(0);
           }

           return ret;
        }

        if (target > pieOpinion.get(sortedIndex.get(iEnd))) {
            System.out.println("--------------------");
            System.out.println("target = " + target);
            System.out.println("person.get(sortedIndex.get(iEnd)) = " + pieOpinion.get(sortedIndex.get(iEnd)));
            System.out.println("--------------------");

            return -1; // not possible
        }

        while (true) {
            iMiddle = (iBegin + iEnd) / 2;
            if (iBegin == iMiddle) {
                // Search is ending
                System.out.println("--------------------");
                System.out.println("target = " + target);
                System.out.println("person.get(sortedIndex.get(iMiddle)) = " + pieOpinion.get(sortedIndex.get(iMiddle)));
                System.out.println("person.get(sortedIndex.get(iEnd)) = " + pieOpinion.get(sortedIndex.get(iEnd)));
                System.out.println("--------------------");

                if (target <= pieOpinion.get(sortedIndex.get(iMiddle))) {
                    index = iMiddle;
                } else {
                    index = iEnd;
                }

                ret = -1;
                if ((pieOpinion.get(sortedIndex.get(index)) >= lowBound) &&
                        (pieOpinion.get(sortedIndex.get(index)) <= highBound))
                {
                    ret = sortedIndex.get(index);
                    sortedIndex.remove(index);
                }
                
                return ret;
            }

            if (target > pieOpinion.get(sortedIndex.get(iMiddle))) {
                iBegin = iMiddle;
            } else if (target < pieOpinion.get(sortedIndex.get(iMiddle))) {
                iEnd = iMiddle;
            } else {

                System.out.println("--------------------");
                System.out.println("target = " + target);
                System.out.println("person.get(sortedIndex.get(iMiddle)) = " + pieOpinion.get(sortedIndex.get(iMiddle)));
                System.out.println("--------------------");

                // Found the value, return node index
                ret = -1;
                if ((pieOpinion.get(sortedIndex.get(iMiddle)) >= lowBound) &&
                        (pieOpinion.get(sortedIndex.get(iMiddle)) <= highBound))
                {
                    ret = sortedIndex.get(iMiddle);
                    sortedIndex.remove(iMiddle);
                }
                
                return ret;
            }
        }
    }

    public static class IndexComparator implements Comparator<Integer> {

        private final ArrayList<Integer> mArray;

        public IndexComparator(ArrayList<Integer> array) {
            mArray = array;
        }

        @Override
        public int compare(Integer index1, Integer index2) {
            // Autounbox from Integer to int to use as array indexes
            return mArray.get(index1).compareTo(mArray.get(index2));
        }
    }
}
