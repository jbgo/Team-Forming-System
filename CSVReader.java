import java.io.*;
import java.util.Scanner;
import java.util.Vector;

// WARNING! This class expects a valid CSV file and will happily parse invalid CSV files
// as if nothing is wrong.

public class CSVReader
{
	Scanner in;

	public CSVReader(String infile) throws FileNotFoundException
	{
		in = new Scanner(new File(infile));
	}

	public CSVReader(File infile) throws FileNotFoundException
	{
		in = new Scanner(infile);
	}

	// returns an array of column values for the next line of input
	// or null if EOF has been reached or an error has occurred
	public String[] nextLine()
	{
		String line = null;
		if (in.hasNextLine()) {
			line = in.nextLine();
		}

		if (line == null) {
			return null;
		}

		return parseCSV(line);
	}

	private String[] parseCSV(String line)
	{
		Vector<String> fields = new Vector<String>();

		final char delim = ',';
		final char quote = '"';
		final char escape = '\\';

		boolean inQuotes = false;
		int fieldStart = 0;
		int fieldEnd = 0;
		char lastc = ' ';

		for (int i = 0; i < line.length(); ++i) {
			char c = line.charAt(i);
			switch (c) {
				case delim:
					if (!inQuotes) {
						if (lastc != quote) {
							fieldEnd = i;
						}

						fields.add(line.substring(fieldStart, fieldEnd));

						fieldStart = i + 1;
						fieldEnd = i + 1;
					}
					break;
				case quote:
					if (!inQuotes) {
						inQuotes = true;
						fieldStart = i + 1;
					} else if (inQuotes && lastc != escape) {
						fieldEnd = i;
						inQuotes = false;
					}
					break;
			}
			lastc = c;
		}

		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return arrFields;
	}

	public boolean hasNextLine()
	{
		return in.hasNextLine();
	}

	public void close()
	{
		in.close();
	}

	public static void main(String[] args) throws FileNotFoundException
	{
		if (args.length != 1) {
			System.out.println("usage: java CSVReader <infile.csv>");
			return;
		}

		CSVReader csv = new CSVReader(args[0]);
		while (csv.hasNextLine()) {
			String[] fields = csv.nextLine();
			for (int i = 0; i < 4; i++) {
				System.out.print("-- " + fields[i] + " --");
			}
			System.out.println();
		}
		csv.close();
	}
}

