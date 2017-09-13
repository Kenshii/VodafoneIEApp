package pm.shane.mobileapi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shane on 11/05/2017.
 */

public class Sms {

    private List<String> recipients = new ArrayList<>();
    private String message;

    public Sms(String recipient, String message) {
        this.recipients.add(recipient);
        this.message = message;
    }

    public Sms(List<String> recipients, String message) {
        this.recipients.addAll(recipients);
        this.message = message;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getMessage() {
        return message;
    }

}
