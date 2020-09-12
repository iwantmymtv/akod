package akod;
import java.util.Scanner;
import java.awt.AWTException;
import java.io.IOException;


public class Main {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("\n");
		System.out.println(ConsoleColor.GREEN_BRIGHT +" Üdvözlet! EZ itt a kód. Válaszd ki mit szeretnél csinálni\n" + ConsoleColor.RESET);
		System.out.println("  Nyomj k-t ha játszani szeretnél");
		System.out.println("  Nyomj q -t ha egy új kérdést szeretnél bevinni");
			
		
		Scanner sc = new Scanner(System.in);
		
		switch (sc.next()) {
		case "k":
			Core app = new Core();
			try {
				app.start();
			
			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
			}
			break;
		case "q":
			try {
				Question.createNewQuestion();
			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Ervenytelen");
			main(args);
			sc.close();
			break;
		}

				
	}

}
