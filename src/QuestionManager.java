import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class QuestionManager {
	
	private List<Question> questions;
	
	public QuestionManager() {
		this.questions = new ArrayList<Question>();
		//generateQuestions();
//		readQuestionsFromTextfile();
//		readQuestionsFromBinaryFile();
		readQuestionObjectsFromFile();
	}
	
	public boolean addQuestion(Question question) {
		return this.questions.add(question);
	}
	
	public List<Question> getQuestions() {
		Collections.shuffle(questions);
		return questions;
	}
	
	public List<Question> getQuestionsOfCategory(String category) {
		List<Question> selectedQuestions = new ArrayList<Question>();
		
		for (Question q : this.questions)  {
			if (q.getCategory().equalsIgnoreCase(category)) {
				selectedQuestions.add(q);
			}
		}
		Collections.shuffle(selectedQuestions);
		return selectedQuestions;
	}
	
	
	public void writeQuestionsIntoTextfile() {
		
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("questions.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Question q : questions) {	
			writer.write(q.getQuestionText() + ";" + q.getCategory() +"\n");
			for (Answer a : q.getAnswers()) {
				writer.write(a.getText() + ";" + a.isCorrect() + "\n");
			}
			
			
		}
		writer.close();
	}
	
	public void readQuestionsFromTextfile() {
		
		this.questions.clear();
		
		FileReader file = null;
		try {
			file = new FileReader("questions.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scanner scanner = new Scanner(file);
		scanner.useDelimiter(Pattern.compile("(;|\\n)"));
		
		// Read questions
		while (scanner.hasNext()) {
			
			String questionText = scanner.next();
			String category = scanner.next();
			
			Question q = new Question(questionText, category);
			
			for (int i=1; i<=4; i++) {
				q.addAnswer(new Answer(scanner.next(), scanner.nextBoolean()));
			}
			
			this.questions.add(q);
		}
			
	
		// Close scanner and file handle
		scanner.close();
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeQuestionObjectsIntoFile() {
		
		ObjectOutputStream oos = null;
	
		try {
			oos = new ObjectOutputStream(new FileOutputStream("questions.ser"));
		} catch (FileNotFoundException fnfe) {
			System.out.println("Datei questions.ser nicht gefunden.");
			// Nutzer:in  bitten neuen Dateispeicherort anzugeben.
		} catch (IOException e) {
			System.out.println("Fehler beim Öffnen des Dateihandles für questions.ser.");
		} 
		
		for (Question q : this.questions) {
			try {
				oos.writeObject(q);
			} catch (IOException e) {
				System.out.println("Fehler beim Speichern der Frage "+ q.getQuestionText());
				e.printStackTrace();
			}
		}

		try {
			oos.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schließen des questions.ser Dateinhandles.");
		}
		
	}
	
	public void readQuestionObjectsFromFile() {
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream("questions.ser"));
		} catch (FileNotFoundException fnfe) {
			System.out.println("Datei questions.ser nicht gefunden.");
			// Nutzer:in  bitten neuen Dateispeicherort anzugeben.
		} catch (IOException e) {
			System.out.println("Fehler beim Öffnen des Dateihandles für questions.ser.");
		} 
		
		if (ois!=null ) {
			this.questions.clear();
			
			try {
				while (true) {
					this.questions.add((Question) ois.readObject());
				}
			} catch (EOFException eof) {
				
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Klassendefinition Question/Answer nicht gefunden.");
			} catch (IOException e) {
				System.out.println("Fehler beim Lesen einer Frage.");
				e.printStackTrace();
			}
			
			finally {
				try {
					ois.close();
				} catch (IOException e) {
					System.out.println("Fehler beim Schließen des Dateihandles.");
				}
			}
		}
	}
	
	
	public void writeQuestionsToBinaryFile() {
		
		DataOutputStream questionFile = null;
		
		try {
			questionFile = new DataOutputStream(new FileOutputStream("questions.bin"));
		} catch (FileNotFoundException e) {
			System.out.println("Fehler beim Anlegen der Datei questions.bin");
			e.printStackTrace();
		}
		
		for (Question question : questions) {
			
			try {
				
				questionFile.writeUTF(question.getQuestionText());
				questionFile.writeUTF(question.getCategory());
				
				for (Answer answer : question.getAnswers()) {
					questionFile.writeUTF(answer.getText());
					questionFile.writeBoolean(answer.isCorrect());
				}
		
			} catch (IOException e) {
				System.out.println("Fehler beim Schreiben der Questions/Answers in die Binaerdatei.");
				e.printStackTrace();
			}
		}

		try {
			questionFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void readQuestionsFromBinaryFile() {
		
		DataInputStream questionFile = null;
		
		try {
			questionFile = new DataInputStream(new FileInputStream("questions.bin"));
		} catch (FileNotFoundException e) {
			System.out.println("Fehler beim Öffnen der Datei questions.bin!");
			e.printStackTrace();
		}
		
		this.questions.clear();
		
		try {
		
			while (true) {	
				
				String questionText = questionFile.readUTF();
				String category = questionFile.readUTF();
				
				Question question = new Question(questionText, category);
				
				for (int i=1; i<=4; i++) {
					Answer answer = new Answer(
							questionFile.readUTF(), 
							questionFile.readBoolean());
					question.addAnswer(answer);
				}
			
				this.questions.add(question);
			}
		}  catch (EOFException ex) {
			System.out.println("Alle Questions eingelesen");
		}  catch (IOException ex) {
			System.out.println("Fehler beim Lesen der Questions aus der Binaerdatei.");
			ex.printStackTrace();
		}
		
		try {
			questionFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	

	public List<String> getCategories() {
		List<String> categories = new ArrayList<>();
		
		for (Question q : this.questions) {
			if (!categories.contains(q.getCategory())) {
				categories.add(q.getCategory());
			}
		}
		
		return categories;
	}
	

}
