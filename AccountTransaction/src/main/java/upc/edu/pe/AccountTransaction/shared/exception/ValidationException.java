package upc.edu.pe.AccountTransaction.shared.exception;

public class ValidationException extends RuntimeException{

        public ValidationException() {
            super();
        }

        public ValidationException(String message) {
            super(message);
        }
}

