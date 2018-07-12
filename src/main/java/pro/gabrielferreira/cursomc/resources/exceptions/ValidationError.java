package pro.gabrielferreira.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

//extendo do modelo de ero que criei
public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message)); // adiciona um erro, uso o metodo pra percorrer a lista e
															// adicionar os erros
	}

}
