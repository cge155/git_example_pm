import java.util.List;
import java.util.Scanner;

class QuizRound {
    private Player player;
    private List<Question> questions;

    public QuizRound(Player player, List<Question> questions) {
        this.player = player;
        this.questions = questions;
    }

    public void start() {
        for (Question question : questions) {
        	
        	question.shuffleAnswers();
        	
            System.out.println("Question: " + question.getQuestionText());
            for (Answer a : question.getAnswers()) {
            	int position = question.getAnswers().indexOf(a)+1;
                System.out.println((position) + ". " + a.getText());
            }

            int userInput = QuizGame.scanner.nextInt();
            
            if (question.getCorrectAnswerIndex()==userInput-1) {
            	System.out.println("Richtig!");
            } else {
            	System.out.print("Das ist leider falsch! Korrekt wäre die Antwort: ");
            	System.out.println(question.getCorrectAnswer());
            }
            
            System.out.println("\n");
                       
        }
    }
}
