package akod;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Core {
	private ArrayList<String> codelist;
	private Map map;
	private int[] position= {6,4};
	private int[] p;
	private int[][] a;
	private int[][] pointMap;
	private int allPoints;
	boolean questionsStarted;
	private String[] wordlist;
	private int steps;
	
	public Core(){
		super();
		this.codelist = new ArrayList<String>();
		this.map = new Map();
		this.p = position;
		this.a = this.map.getMapArray();
		this.pointMap = this.map.getPointMap();
		this.allPoints = 0;
		this.questionsStarted = false;
		this.wordlist = null;
		this.steps = 0;
	}

	public void start() throws AWTException, InterruptedException {
		this.map.generatePointsAndPanicRooms();
		try {
			this.wordlist = Utils.readWordList("szavak.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Utils.wipeWait(250);
		Scanner sc = new Scanner(System.in);
		this.questionsStarted = true;
		//alap jatekos pozícó beállítás
		this.a[this.p[0]][this.p[1]] = 9; 
			
			//itt kezdődik a jatek
			while(questionsStarted) {
				//ha kilenc vagy tobbet erintett mehet a vegjatekba
				if (this.steps >= 9) {
					System.out.println("Érintettél " + this.steps +" mezőt, kezdőthet a végjáték?");
					System.out.println("(igen / nem)");
					System.out.println("Pontszámod: " + this.allPoints);
					if (sc.next().contentEquals("igen")) {
						endgame();
						return;
					}
				}
				
				this.steps += 1;
				
				//elso kor kivetelevel minden kor elején kiirja a kodlistat majd 2 mp utan eltunik
				if (!this.codelist.isEmpty()) {
					Utils.wipeWait(150);
					System.out.println("A kódlista 2 másodperc múlva eltűnik:\n");
					Utils.printCodelist(this.codelist);
					Thread.sleep(2000);					
				}
				//pontszám plusz palya megjelenités
				Utils.wipeWait(150);
				System.out.println("Pontszám: " + Integer.toString(this.allPoints));
				this.map.DisplayMap();
				Question kerdes = Question.askRandomQuestion();
				
				//ellenorzi a kerdest
				if (checkAnswer(kerdes,sc)) {
					//amig nem ervenyes helyre lep nem enged tovabb
					while (!move(sc,true,false));
				} else {
					while (!move(sc,false,false));
				}
			}
			
	}
	
	
	private boolean checkAnswer(Question kerdes,Scanner sc) {
		if (sc.next().contentEquals(kerdes.getRightAnswerIndex())) {
			rightAnswer(kerdes,sc);
			return true;
		}else {
			wrongAnswer(kerdes,sc);
			return false;
		}
	}
	
	private void rightAnswer(Question kerdes,Scanner sc) {
		this.codelist.add(kerdes.getRightAnswer());
		//ha 666 on van nem ad hozza mivel az a panikmezo
		if (this.pointMap[this.p[0]][this.p[1]] != 666) {
			this.allPoints += this.pointMap[this.p[0]][this.p[1]];
		}
		System.out.println("\nHelyes\n");
		Utils.whereGo();
	}
	
	private void wrongAnswer(Question kerdes,Scanner sc) {
		this.codelist.addAll(Arrays.asList(kerdes.getAns1(),kerdes.getAns2(),kerdes.getAns3(),kerdes.getAns4()));
		System.out.println("\nHelytelen\n");
		Utils.whereGo();
	}
	
	//leptetés
	private boolean move(Scanner sc,boolean right,boolean panic) throws InterruptedException, AWTException {
		boolean moved = false;
		//ellenorzi hogy beragadt e
		if(checkIfStucked(right)) {
			stuckedMove(panic);
			this.allPoints = 0;
			return true;
		}
		String key = sc.next();
		switch (key){
		case "w":
			if(moving(right,0,true,panic,"w")) {
				moved = true;
			}else {
				moved=false;
			}
			
			break;
		case "a":
			if(moving(right,1,true,panic,"a")) {
				moved = true;
			}else {
				moved=false;
			}
			break;
		case "d":
			if(moving(right,1,false,panic,"d")) {
				moved = true;
			}else {
				moved=false;
			}
			break;
		}
		return moved;
		
	}
	
	private boolean moving(boolean right,int index,boolean minus,boolean panic,String key) {
		
		if(right) {
			lastFieldColor(panic,5,2);
		} else {
			lastFieldColor(panic,6,3);
			if (key.contentEquals("w")) {
				System.out.println("Mivel előzőleg vesztettél,ezért nem léphetsz felfelé");
				return false;
			}
		}
		//minus -- kivonjon vagy hozzadjoneadjon a posicióhoz
		if(minus) {
			this.p[index] -= 1;
			
			//ha palyan kivulre lep visszaallitja a poziciot
			if (this.map.checkIfOutOfMap(this.p,this.a)) {
				this.p[index] += 1;
				System.out.println("Arra nem léphetsz");
				return false;
			}
		}else {
			this.p[index] += 1;
			if (this.map.checkIfOutOfMap(this.p,this.a)) {
				this.p[index] -= 1;
				System.out.println("Arra nem léphetsz");
				return false;
			}
		}
		//ha 666 betölti a panikmezot
		if (checkIfPanicField()) {
			this.questionsStarted = false;
			this.a[this.p[0]][this.p[1]]=4;
			try {
				panicField();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		//léptetett poiziciot 9 re vagyis [X] re allitja
		this.a[this.p[0]][this.p[1]]=9;
		return true;
	}
	
	
	//ha elakad léptet felflé
	private void stuckedMove(boolean panic) throws AWTException, InterruptedException {
		//ha utolso sorban akad el elinditja a vegjatekot
		if (p[0]-1 == 0) {
			System.out.println("Vége!, Mindjárt kezdődik a végjáték!");
			Thread.sleep(2000);
			endgame();
			return;
		}
		Utils.wipeWait(200);
		System.out.println(ConsoleColor.RED + "ELAKADTÁL" +ConsoleColor.RESET+", így egyet előrelépsz és az összes eddigi pontod elveszett\n");
		this.map.DisplayMap();
		Thread.sleep(2000);
		lastFieldColor(panic,6,3);
		this.p[0] -= 1;
		this.a[this.p[0]][this.p[1]]=9;
	}
	
	//vizsgálja hogy elakadt e
	private boolean checkIfStucked(boolean right) {
		int posY = this.p[0];
		int posX = this.p[1];
		int f1 = this.a[posY][posX-1];
		int f2 = this.a[posY][posX+1];
		
		if ((f1== 0 || f1 == 2 || f1 == 3|| f1 == 5|| f1 == 6) && 
			(f2 == 0 || f2 == 2 || f2 == 3 || f2 == 5|| f2 == 6) && !right) {
			return true;
		//ha az utolso sorba szorul be de jo a valasz akkor is "elakad"	
		}else if((f1== 0 || f1 == 2 || f1 == 3|| f1 == 5|| f1 == 6) && 
				(f2 == 0 || f2 == 2 || f2 == 3 || f2 == 5|| f2 == 6) && right && posY == 1){
			return true;
		}else {
			return false;
		}
		
	}
	
	//666 a pánikmező
	private boolean checkIfPanicField() {
		if(this.pointMap[this.p[0]][this.p[1]] == 666) {
			return true;
		}else {
			return false;
		}
	}
	
	private void panicField() throws InterruptedException, AWTException {
		Scanner ps = new Scanner(System.in);
		Utils.wipeWait(150);
		
		System.out.println("\n"+ ConsoleColor.RED_BOLD +"  --------- Pánik mező ---------\n" + ConsoleColor.RESET);
		this.map.DisplayMap();
		
		System.out.println("1 másodperc múlva megjelenik 5 szó amit memorizálnod kell");
		System.out.println("\n");
		Thread.sleep(1000);
		//random kivalaszt 5 szot majd megjeleniti 4 mp ig
		String words = Utils.getFiveRandomWords(this.wordlist);
		System.out.println(ConsoleColor.GREEN_BRIGHT + words  + ConsoleColor.RESET);
		Thread.sleep(4000);
		
		Utils.wipeWait(150);
		System.out.println("Most írd le a szavakat vesszővel elválsztva!");
		
		String result = ps.nextLine();
		if (result.equals(words)) {
			this.steps += 1;
			Utils.wipeWait(150);
			System.out.println("Helyes!");
			this.map.DisplayMap();
			Utils.whereGo();
			while (!move(ps,true,true));
		}else {
			this.steps += 1;
			Utils.wipeWait(150);
			System.out.println("Helytelen\n");
			System.out.println("Eredeti szavak:" + ConsoleColor.GREEN_BRIGHT+ words + ConsoleColor.RESET);
			System.out.println("Te szavaid:" + ConsoleColor.RED_BRIGHT +result + ConsoleColor.RESET +"\n");
			this.map.DisplayMap();
			Utils.whereGo();
			while (!move(ps,false,true));
		}
		this.questionsStarted = true;
		
	}
	
	//milyenre szinezi az elozo mezot
	private void lastFieldColor(boolean panic,int colorNum1, int colorNum2) {
		if (panic) {
			this.a[this.p[0]][this.p[1]]=colorNum1;
		}else {
			this.a[this.p[0]][this.p[1]]=colorNum2;
		}
	}
	
	private void endgame() throws AWTException, InterruptedException {
		this.questionsStarted = false;
		Endgame end = new Endgame(this.steps,this.allPoints,this.codelist);
		end.start();
	}
	
}
