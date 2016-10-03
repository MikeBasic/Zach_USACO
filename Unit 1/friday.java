/*
 ID: Tuschristine
 LANG: JAVA
 PROG: friday
 */

package USACO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Scanner;

public class friday {

	private static final Reader FileReader = null;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int yearCount = 0;
		String fileOut = "friday.out";
		int[] weekdays = new int[7];
		int[] monthdays = new int[13];
		int year, whichday, leapyear, monthend;

		Scanner scanner = new Scanner(new File("friday.in"));
		while (scanner.hasNextInt()) {
			yearCount = scanner.nextInt();
		}
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));

		// System.out.println(yearCount);

		monthdays[1] = 31;
		monthdays[2] = 29;
		monthdays[3] = 31;
		monthdays[4] = 30;
		monthdays[5] = 31;
		monthdays[6] = 30;
		monthdays[7] = 31;
		monthdays[8] = 31;
		monthdays[9] = 30;
		monthdays[10] = 31;
		monthdays[11] = 30;
		monthdays[12] = 31;

		year = 1900;
		whichday = 1; // monday

		for (int y = 0; y < yearCount; y++) {
			leapyear = 0;

			// see if it applies the century year rule for leap year
			if (year / 100 * 100 == year) {
				if (year / 400 * 400 == year) {
					leapyear = 1;
				}
			} else if (year / 4 * 4 == year) {
				leapyear = 1;
			}

			for (int m = 1; m <= 12; m++) {

				if (m == 2) {
					if (leapyear == 1) {
						monthend = 29;
					} else {
						monthend = 28;
					}
				} else {
					monthend = monthdays[m];
				}

				for (int d = 1; d <= monthend; d++) {
					if (d == 13) {
						weekdays[whichday]++;
					}

					whichday++;
					if (whichday > 6) {
						whichday = 0;
					}
				}
			}

			year++;
		}
		
		System.out.print(weekdays[6] + " " + weekdays[0] + " " + weekdays[1] + " " + weekdays[2] + " " + weekdays[3] + " " + weekdays[4]
				+ " " + weekdays[5]);
		out.println(weekdays[6] + " " + weekdays[0] + " " + weekdays[1] + " " + weekdays[2] + " " + weekdays[3] + " " + weekdays[4]
				+ " " + weekdays[5]);
		out.close();
	}

}