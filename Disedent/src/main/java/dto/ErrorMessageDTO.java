package dto;

public class ErrorMessageDTO {

        private String message;

        public ErrorMessageDTO(String message){
            setMessage(message);
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

}
