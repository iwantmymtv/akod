package akod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	public static List < Question > readQuestionFromCSV(String filename) throws IOException { 
		List < Question > question;
	    List<String[]> content = readFile("kerdesek.csv");
	    question = new ArrayList <>();
	    for (String[] kerdes: content) {
			Question q = new Question(kerdes[0],kerdes[1],kerdes[2],kerdes[3],kerdes[4],kerdes[5],kerdes[Integer.parseInt(kerdes[5])]);
			question.add(q);
		}
	    return question;
	}
	
	public static void writeFile(String filename,String content) throws IOException {
		FileWriter fstream = new FileWriter(filename, true);
		BufferedWriter out = new BufferedWriter(fstream);
		try {
			out.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.newLine();
		out.close();
	}
	
	public static List<String[]> readFile(String filename) throws IOException {
		List<String[]> content = new ArrayList<>();
	    try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line = "";
	        while ((line = br.readLine()) != null) {
	            content.add(line.split(";"));
	        }
	    } catch (FileNotFoundException e) {
	      System.err.println("Fájl nem található " + filename);
	    }
	    return content;
	}
	
	

}