package bop.parse.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Test {

	public static void main(String[] args) {
		// TODO Check if I can read the pdf through the url
		// https://www.irs.gov/pub/irs-pdf/i1040tt.pdf

		try {
			getDataFromURL();
			parseData();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void getDataFromURL() throws Exception {
		// since we know the url path; no need in asking for it.
		URL path = new URL("https://www.irs.gov/pub/irs-pdf/i1040tt.pdf");
		URLConnection connect = path.openConnection();
		// let's check to see that the connection is established
		InputStream input = connect.getInputStream();
		Files.copy(input, Paths.get("temp.pdf"), StandardCopyOption.REPLACE_EXISTING);
		// System.out.println(data);
		input.close();
		System.out.println("Should be done.");
	}

	public static ArrayList<String> parseData() throws Exception {

		// TODO: Take data from File extract it from the file using pdfbox
		String data = getText(new File("temp.pdf"));
		// System.out.println(data);

		// TODO: Delete the local version of the file
		deleteTheFile(new File("temp.pdf"));

		// TODO: Write out the data to another file
		writeFile(data);

		// TODO: Parse data using pdfbox
		ArrayList<String> datalist = sortData();

		return datalist;
	}

	private static ArrayList<String> sortData() throws Exception {

		File f = new File("temp.data");
		Scanner sc = new Scanner(f);
		String capture = "";

		ArrayList<String> datalist = new ArrayList<>();
		while (sc.hasNext()) {
			capture = sc.nextLine().trim();
			if (capture.contains("line 44.")) {
				capture = sc.nextLine().trim();
				while (sc.hasNext()) {
					capture = sc.nextLine().trim();
					if (capture.contains("Your tax is")) {
						while (sc.hasNext()) {
							capture = sc.nextLine().trim();
							// System.out.println(capture);
							if (capture.contains(",") || (capture.contains(" ") && !capture.contains("If line 43"))) {
								datalist.add(capture);
								System.out.println(capture);
							} else {
								break;
							}
						}
					}
				}
			}
		}
		sc.close();
		System.out.println("Going out of data");
		return datalist;
	}

	private static void writeFile(String data) throws Exception {
		PrintWriter out = new PrintWriter("temp.data");
		out.println(data);
		out.close();
	}

	public static String getText(File pdfFile) throws IOException {
		PDDocument doc = PDDocument.load(pdfFile);
		return new PDFTextStripper().getText(doc);
	}

	public static void deleteTheFile(File f) {
		// TODO: Delete the local version of the file.
		f.delete();
		System.out.println("Deleting done.");

	}
}
