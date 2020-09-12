package akod;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Endgame {
	
	private int steps;
	private int score;
	private ArrayList<String> codelist;
	private float endscore;
	
	public Endgame(int steps, int score, ArrayList<String> codelist) {
		super();
		this.steps = steps;
		this.score = score;
		this.codelist = codelist;
		this.endscore = 0;
	}


	public void start() throws AWTException, InterruptedException {
		Thread.sleep(150);
		Utils.wipeWait(150);
		System.out.println("Végjáték");
		//ha alapbol 0 pontja van vesztett
		if (this.score == 0) {
			this.endscore = 0;
			System.out.println("Sajnáljuk, 0 pontod van.Vesztettél");
			System.out.println("Lépések száma: " + this.steps);
			return;
		}
		System.out.println("Pontjaid: " + this.score);
		int percent =(Utils.randInt(35,50));
		float multi = (float)percent / 100;
		float multiScore =this.score * multi;
		
		System.out.println("Elfogadod a pontjaid garantált " + percent + "% -át, azaz " + multiScore + " pontont?");
		System.out.println("(igen / nem)");
		Scanner sc = new Scanner(System.in);
		
		if (sc.next().contentEquals("nem")) {
			Utils.wipeWait(150);
			System.out.println("A kódlistád:\n");
			Utils.printCodelist(this.codelist);
			Thread.sleep(3000);
			Utils.wipeWait(150);
			System.out.println("Most gépeld le a szavakat vesszővel elválasztva:");
			String codeString = sc.next();
			
			if(checkCodeList(codeString)) {
				this.endscore = this.score;
				System.out.println("Gratulálunk, pontszámod: " + this.endscore);
				System.out.println("Lépések száma: " + this.steps);
			}else {
				this.endscore = 0;
				System.out.println("Sajnáljuk,vesztettél. Pontszámod: " + this.endscore);
				System.out.println("Lépések száma: " + this.steps);
			} 
			sc.close();
		//ha elfogadja a garantalt nyeremenyt	
		}else {
			this.endscore = multiScore;
			System.out.println("\nGratulálunk, pontszámod: " + this.endscore);
			System.out.println("Lépések száma: " + this.steps);
		}
		
	}
	
	//ellenorzi h jo e a beirt lista
	private boolean checkCodeList(String userlist) {
		ArrayList<String> strList = new ArrayList<String>(Arrays.asList(userlist.split(",")));
		if (strList.equals(this.codelist)) {
			return true;
		}
		return false;
	}
}
