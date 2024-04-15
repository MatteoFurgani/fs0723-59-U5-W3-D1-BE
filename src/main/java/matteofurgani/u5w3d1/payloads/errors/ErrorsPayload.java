package matteofurgani.u5w3d1.payloads.errors;

import java.util.Date;

public record ErrorsPayload(String message, Date timeStamp) {

    public ErrorsPayload(String message, Date timeStamp){
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
