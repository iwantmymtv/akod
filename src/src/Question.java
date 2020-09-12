package akod;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Question {
	String question;
	String ans1;
	String ans2;
	String ans3;
	String ans4;
	String rightAnswerIndex;
	String rightAnswer;
	
	

	public Question(String question, String ans1, String ans2, String ans3, String ans4, String rightAnswerIndex,
			String rightAnswer) {
		super();
		this.question = question;
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.ans4 = ans4;
		this.rightAnswerIndex = rightAnswerIndex;
		this.rightAnswer = rightAnswer;
	}
	
	

	public String getAns1() {
		return ans1;
	}



	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}



	public String getAns2() {
		return ans2;
	}



	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}



	public String getAns3() {
		return ans3;
	}



	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}



	public String getAns4() {
		return ans4;
	}



	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}



	public String getRightAnswerIndex() {
		return rightAnswerIndex;
	}



	public void setRightAnswerIndex(String rightAnswerIndex) {
		this.rightAnswerIndex = rightAnswerIndex;
	}



	public String getRightAnswer() {
		return rightAnswer;
	}



	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}



	@Override
	public String toString() {
		return question + "\n1: " + ans1 + "\n2: " + ans2 + "\n3: " + ans3 + "\n4: " + ans4;
	}

	public static Question askRandomQuestion() {
		List<Question> kerdesek = null;
		try {
			kerdesek = FileManager.readQuestionFromCSV("kerdesek.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Question kerdes = kerdesek.get(Utils.randInt(0,kerdesek.size()-1));
		System.out.println(kerdes);
		return kerdes;
	}
	
	
	public static void createNewQuestion() throws AWTException, InterruptedException {
		Utils.wipeWait(200);
		Scanner kerdesSc = new Scanner(System.in);
		System.out.println("Írd le a kérdésedet:");
		String kerdes = kerdesSc.nextLine();
		System.out.println("Első válaszlehetőség: ");
		String valasz1 = kerdesSc.nextLine();
		System.out.println(ConsoleColor.CYAN + "Sorszám : 1.\n" +ConsoleColor.RESET);
		System.out.println("Második válaszlehetőség: \n");
		String valasz2 = kerdesSc.nextLine();
		System.out.println(ConsoleColor.CYAN + "Sorszám : 2.\n" +ConsoleColor.RESET);
		System.out.println("Harmadik válaszlehetőség: \n");
		String valasz3 = kerdesSc.nextLine();
		System.out.println(ConsoleColor.CYAN + "Sorszám : 3.\n" +ConsoleColor.RESET);
		System.out.println("Negyedik válaszlehetőség: \n");
		String valasz4 = kerdesSc.nextLine();
		System.out.println(ConsoleColor.CYAN + "Sorszám : 4.\n" +ConsoleColor.RESET);
		System.out.println("Helyes megfejtés sorszáma:");
		int helyes = kerdesSc.nextInt();
		System.out.println("\nKérdesed hozzá lett adva a kérdésekhez!");
		String question = kerdes+";"+valasz1+";"+valasz2+";"+valasz3+";"+valasz4+";"+helyes;
		try {
			FileManager.writeFile("kerdesek.csv", question);
		} catch (IOException e) {
			e.printStackTrace();
		}
		kerdesSc.close();
	}
	
	
	

	
}
