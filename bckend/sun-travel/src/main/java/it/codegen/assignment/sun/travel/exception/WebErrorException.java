package it.codegen.assignment.sun.travel.exception;

import it.codegen.assignment.sun.travel.dto.WebErrorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebErrorException extends RuntimeException{
    private final WebErrorDTO webErrorDTO;

    public WebErrorException(WebErrorDTO webErrorDTO) {
        this.webErrorDTO = webErrorDTO;
    }
}
