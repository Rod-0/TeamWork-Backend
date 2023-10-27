package upc.edu.pe.AccountTransaction.exception;

public class ValidationException extends RuntimeException{

        public ValidationException() {
            super();
        }

        public ValidationException(String message) {
            super(message);
        }
}

