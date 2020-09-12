package akod;

import java.util.Arrays;

public class Map {
	private int[][] mapArray;
	private int[][] pointMap;
	private int[][] a = {
			{0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,0},
			{0,0,1,1,1,1,1,0,0},
			{0,0,0,1,1,1,0,0,0},
			{0,0,0,1,1,1,0,0,0},
			{0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
		};
	
	public Map(int[][] array) {
		super();
		this.mapArray = array;
		this.pointMap = new int[this.mapArray.length][this.mapArray[0].length];
	}
	
	public Map() {
		super();
		this.mapArray = this.a;
		this.pointMap = new int[this.mapArray.length][this.mapArray[0].length];
	}

	public int[][] getMapArray() {
		return mapArray;
	}

	public void setMapArray(int[][] mapArray) {
		this.mapArray = mapArray;
	}

	public int[][] getPointMap() {
		return pointMap;
	}

	public void setPointMap(int[][] pontok) {
		this.pointMap = pontok;
	}
	
	private void fillPoints(int i,int j, int pont ){
		if (this.mapArray[i][j] != 0) {
			this.pointMap[i][j] = pont;
		}else {
			this.pointMap[i][j] = 0;
		}
	}
	
	//felölti a pointmapet sorronként a megadott pontokkal
	public void generatePointsAndPanicRooms(){
		for (int j = 1;  j < 8; j++) {
			fillPoints(1,j,500);
			fillPoints(2,j,300);
			fillPoints(3,j,200);
			fillPoints(4,j,150);
			fillPoints(5,j,100);
			fillPoints(6,j,50);
		}
		
		//jackpot utolso sorba
		pointMap[1][Utils.randInt(1,7)] = 5000;
		//666 = pánik szoba
		int r = 0;
		while (r < 5) {
			//indulásnál nem lehet pánik szoba
			//randInt(0,3) nal az elso ket sorba nem lehetne
			int row = Utils.randInt(0,4);
			int col = Utils.randInt(0,6);
			//jackpotot se irja felul 
			if (pointMap[row][col] != 0 && pointMap[row][col] != 5000) {
				pointMap[row][col] = 666;
				r++;
			}
		}
	}
	
	/* 
	  palyat jeleniti meg
		1 - még felfedezetlen mezők
		2 - helyesen valaszolt mezok
		3 - rossul valaszolt mezok
		4 - panik szoba
		5 - panik szoba helyes
		6 -panik szoba helytelen
		9 - jatekos jelenlegi poziciója
		
	eclipsebe csak ezzel mukodnek a szinek
	
	 https://marketplace.eclipse.org/content/ansi-escape-console 
	 
	*/

	public void DisplayMap() {
		for (int i = 0; i < this.mapArray.length; i++){
			for (int j = 0;  j < this.mapArray[i].length; j++) {
				int a =this.mapArray[i][j];
				if (a == 0) {
					System.out.print(ConsoleColor.BLACK_BRIGHT+"  .  "+ConsoleColor.RESET);
				}else if(a == 1){
					//alap még fedetlen mezők
					System.out.print(ConsoleColor.CYAN_BOLD+" [■] " +ConsoleColor.RESET);
				}else if(a == 2){
					//helyesen megválaszolt mezők
					System.out.print(ConsoleColor.GREEN_BOLD  +" [■] "+ ConsoleColor.RESET);
				}else if(a == 3){
					//rossul válaszolt mezők
					System.out.print(ConsoleColor.RED_BOLD+ " [■] "+ ConsoleColor.RESET);
				}else if(a == 4){
					//panik szoba
					System.out.print(ConsoleColor.YELLOW_BOLD+ " [P] "+ ConsoleColor.RESET);
				}else if(a == 5){
					//panik szoba helyes
					System.out.print(ConsoleColor.GREEN_BOLD+ " [P] "+ ConsoleColor.RESET);
				}else if(a == 6){
					//panik szoba helytelen
					System.out.print(ConsoleColor.RED_BOLD+ " [P] "+ ConsoleColor.RESET);
				}else if(a == 9){
					//jatekos jelenlegi potíciója
					System.out.print(ConsoleColor.YELLOW_BOLD+ " [X] "+ ConsoleColor.RESET);
				}
			}
			System.out.println("\n");
			
		}
	}
	
	public void displayPoints() {
		for (int i = 0; i < this.pointMap.length; i++){
			System.out.println(Arrays.toString(this.pointMap[i]));

		}
	}
	
	//vizsgálja hogy palyan kivul vagy mar lepett mezore akar lepni
	public  boolean checkIfOutOfMap(int[] p, int[][] a) {
		int posY = p[0];
		int posX = p[1];
		int field = a[posY][posX];
		
		if (field == 0 && posY == 0) {
			System.out.println("Utolsó sor");
			return true;
		}
		if(field != 0 && field != 2 && field != 3  && field != 5 && field != 6) {
			return false;
		}else {
			return true;
		}
	}
	
	
	
	
	
	
}
