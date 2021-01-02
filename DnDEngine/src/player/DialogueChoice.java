package player;

public class DialogueChoice {
	
	private String question;
	private String answer;
	
	public DialogueChoice(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public String getAnswer() {
		return this.answer;
	}
}
