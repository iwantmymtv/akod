package akod;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Utils {
	
	public static int randInt(int minimum,int maximum) {
		Random rn = new Random();
		int range = maximum - minimum + 1;
		int randomNum =  rn.nextInt(range) + minimum;
		return randomNum;
	}
	
	//ECPLIPSE be torli a console t,feltéve ha a SHIFT + F10 nincs beállítva másra 
	 public static void wipeConsole() throws AWTException{
	        Robot robbie = new Robot();

	        robbie.keyPress(KeyEvent.VK_SHIFT);
	        robbie.keyPress(KeyEvent.VK_F10);
	        robbie.keyRelease(KeyEvent.VK_SHIFT);
	        robbie.keyRelease(KeyEvent.VK_F10);
	        robbie.keyPress(KeyEvent.VK_R);
	        robbie.keyRelease(KeyEvent.VK_R);
	    }
	 
	 public static String getFiveRandomWords(String[] wordlist){
			String fiveWords = "";
			for(int i = 0; i < 5; i++) {
				if (i == 4) {
					fiveWords += wordlist[Utils.randInt(0, wordlist.length)];
				}else {
					fiveWords += wordlist[Utils.randInt(0, wordlist.length)]+ ",";
				}
				
			}
			return fiveWords;
		}
		
	 public static String[] readWordList(String filename) throws IOException {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String szoveg = "";
			String sor = null;
			while((sor=br.readLine())!=null) {
				szoveg += sor + "\n";
			}
			String[] result = szoveg.split(",");
			
			br.close();
			
			return result;
		}
	 
	 public static void wipeWait(int time) throws AWTException, InterruptedException {
		 wipeConsole();
		 Thread.sleep(time);
	 }
	 public static void whereGo() {
		 System.out.println("Merre akarsz továbblépni?");
		 System.out.println("w - előre, a - balra, d - jobbra");
	 }

	 public static void printCodelist(ArrayList<String> codelist) {
		 for( String code : codelist ){
			    System.out.println(ConsoleColor.CYAN + code + ConsoleColor.RESET);
			}
	 }
}
