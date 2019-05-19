package ua.kiev.prog.oauth2.loginviagoogle.dto;

public class ResultDTO {
    private String status = "OK";

    public ResultDTO() { }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
