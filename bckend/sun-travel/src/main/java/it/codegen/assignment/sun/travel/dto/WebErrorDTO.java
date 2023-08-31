package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebErrorDTO {
    private String message;
    private Integer status;
    private String errorCode;
    private String detail;

    public WebErrorDTO(String message, Integer status, String errorCode, String detail) {
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public WebErrorDTO(String message, Integer status, String errorCode) {
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
    }

}
