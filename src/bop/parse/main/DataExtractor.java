package bop.parse.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

//Short version
public class DataExtractor {

	public static void main(String[] args) {
		// TODO Check if I can read the pdf through the url
		// https://www.irs.gov/pub/irs-pdf/i1040tt.pdf
		
		try {
			getDataFromURL();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ArrayList<String> getDataFromURL() throws Exception {
		// since we know the url path; no need in asking for it.
		URL path = new URL("https://www.irs.gov/pub/irs-pdf/i1040tt.pdf");
		URLConnection connect = path.openConnection();
		// let's check to see that the connection is established
		InputStream input = connect.getInputStream();
		// TODO: Parse data using pdfbox
		ArrayList<String> datalist = sortData(writeFile(getText(input)));
		input.close();
		return datalist;
	}

	private static ArrayList<String> sortData(File f) throws Exception {
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
		deleteTheFile(f);
		System.out.println("Going out of data");
		return datalist;
	}

	private static File writeFile(String data) throws Exception {
		File f = new File("temp.data");
		PrintWriter out = new PrintWriter(f);
		out.println(data);
		out.close();
		return f;
	}

	public static String getText(InputStream pdfFile) throws IOException {
		PDDocument doc = PDDocument.load(pdfFile);
		return new PDFTextStripper().getText(doc);
	}

	public static void deleteTheFile(File f) {
		// TODO: Delete the local version of the file.
		f.delete();
		System.out.println("Deleting done.");

	}

}
