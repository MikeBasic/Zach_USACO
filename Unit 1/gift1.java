/*
 ID: Tuschristine
 LANG: JAVA
 PROG: gift1
 */

package USACO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class GiftCount {
	String name;
	int money;
	int netGain;
}

public class gift1 {

	public static void main(String[] args) throws IOException {

		BufferedReader f = new BufferedReader(new FileReader("gift1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
		String name;
		String fline;
		String hello;

		int NP;
		GiftCount[] gc = new GiftCount[20];

		NP = Integer.parseInt(f.readLine());
		// System.out.println(NP);

		for (int i = 0; i < NP; i++) {
			gc[i] = new GiftCount();

			// gc[i].money = 100;
			// gc[i].name = "ztu";

			gc[i].name = f.readLine();
			// name = f.readLine();
			gc[i].netGain = 0;

			// System.out.println(gc[i].name);
		}

		// loop over number of people
		for (int c = 0; c < NP; c++) {

			name = f.readLine(); // EVERYONE
			// System.out.println(name);
			fline = f.readLine();
			// System.out.println(fline);

			int totalMoney, numberPeople, index = 0;
			String[] strs = fline.trim().split(" ");

			totalMoney = Integer.parseInt(strs[0]);
			numberPeople = Integer.parseInt(strs[1]);

			for (int i = 0; i < NP; i++) {
				if (name.compareTo(gc[i].name) == 0) {
					gc[i].money = totalMoney;
					index = i;

					break;
				}
			}

			// System.out.println(totalMoney);
			// System.out.println(numberPeople);

			int moneyEachPerson, leftOver;
			if (numberPeople == 0) {
				continue;
			} else {
				moneyEachPerson = totalMoney / numberPeople;
				leftOver = totalMoney % numberPeople;
			}

			// System.out.println(moneyEachPerson);
			// giving money
			gc[index].netGain += leftOver - gc[index].money;

			// reciving money
			for (int j = 0; j < numberPeople; j++) {
				name = f.readLine();
				for (int i = 0; i < NP; i++) {
					if (name.compareTo(gc[i].name) == 0) {

						gc[i].netGain += moneyEachPerson;

						break;
					}
				}
			}
		}

		for (int i = 0; i < NP; i++) {
			System.out.println(gc[i].name + " " + gc[i].netGain);
			out.println(gc[i].name + " " + gc[i].netGain);
		}
		out.close();
		System.exit(0);
	}
}