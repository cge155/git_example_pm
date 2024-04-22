import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Question implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String questionText;
    private List<Answer> answers;
    private String category;

    public Question(String questionText, String[] answers, int correctAnswerIndex, String category) {
        this.answers = new ArrayList<Answer>();
    	this.questionText = questionText;
        addAnswers(answers, correctAnswerIndex);
        this.category = category;
    }
    
    public Question(String questionText, String category) {
    	this.questionText = questionText;
    	this.category = category;
    	this.answers = new ArrayList<>();
    }
    

	private void addAnswers(String[] answers, int correctAnswerIndex) {
		for (int i=0; i<answers.length; i++) {
			if (correctAnswerIndex==i) {
				this.answers.add(new Answer(answers[i], true));
			} else {
				this.answers.add(new Answer(answers[i], false));
			}
		}
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public String getQuestionText() {
		return this.questionText;
	}

	public int getCorrectAnswerIndex() {
		for (Answer a : this.answers) {
			if (a.isCorrect()) {
				return this.answers.indexOf(a);
			}
		}
		return -1;
	}

	public String getCategory() {
		return category;
	}
	
	public void shuffleAnswers() {
		Collections.shuffle(answers);
	}

	public String getCorrectAnswer() {
		// TODO Auto-generated method stub
		return "";
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}

}