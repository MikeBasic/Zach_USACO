/*
 ID: Tuschristine
 LANG: JAVA
 TASK: ride  
*/
package USACO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

	class ride {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(System.getProperty("user.dir"));

		// The name of the file to open.
		String fileName = "ride.in";
		String fileOut = "ride.out";

		// This will reference one line at a time
		String line = null;
		int resLine1 = -1, result=0;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);
			PrintWriter writer = new PrintWriter(fileOut, "UTF-8");

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);

				{
					char A = 'A';
					char[] letter = new char[2];
					int i;
					
					result = 1;

					for (i = 0; i < line.length(); i++) {
						line.getChars(i, i + 1, letter, 0);
//						System.out.println(letter);

						result *= (letter[0] - A + 1);
						//System.out.println(result);
					}

					result = result % 47;
//					System.out.println(result);

					if (resLine1 == -1) {
						resLine1 = result;
					}
				}
			}

			if (resLine1 == result)
			{
				System.out.println("GO");
				writer.println("GO");

			}
			else
			{
				System.out.println("STAY");
				writer.println("STAY");


			}
			// Always close files.
			bufferedReader.close();
			writer.close();

		} 
		catch (FileNotFoundException ex) {
			//System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			//System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}
}

