import java.util.List;
import java.util.Scanner;

public class QuizGame {
	
	public static Scanner scanner;
	private static Player player;
	private static QuestionManager questionManager;
	private static PlayerManager playerManager;
	private static String category = "Sport";
	
    public static void main(String[] args) {
        // Spieler anlegen
        player = new Player("Christian");
        playerManager = new PlayerManager();
        playerManager.addPlayer(player);        
        
        questionManager = new QuestionManager();
        
        System.out.println("********************************************");
        System.out.println("* Willkommen beim Quiz Game (Arbeitstitel) *");
        System.out.println("********************************************");
        
        boolean exit = false;
        scanner = new Scanner(System.in);
        
        while (!exit) {
        	System.out.println("1. Quiz Kategorie auswählen (aktuell: " +category +")");
        	System.out.println("2. Quiz starten");
        	System.out.println("3. Anwendung beenden");
        	
        	int selectedMenuItem = scanner.nextInt();
        	
        	switch (selectedMenuItem) {
        		case 1 : selectQuizCategory();
        				 break;
        		case 2 : startQuiz();
        				 break;
        		case 3 : exit = true;
        				 break;
        	}
        
        }
       
        scanner.close();	
        
//        questionManager.writeQuestionObjectsIntoFile();
        questionManager.writeQuestionsToBinaryFile();
        
    }

	private static void startQuiz() {
		QuizRound gameRound = new QuizRound(player, questionManager.getQuestionsOfCategory(category));
        gameRound.start();
	}

	private static void selectQuizCategory() {
		
		System.out.println("Kategorien:");
		System.out.println("-----------");
		List<String> categories = questionManager.getCategories();
		for (String s : categories) {
			System.out.println("- "+s);
		}
		System.out.print("Bitte Kategorie auswählen: ");
		category = scanner.next();
		// TODO: Check whether the selected category exists in categories. I.e. check for typos.
	}
}
