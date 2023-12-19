package online.epochsolutions.mafaro.exceptions;

import lombok.Getter;

@Getter
public class UserNotVerifiedException extends Throwable {
    private final boolean newEmailSent;

    public UserNotVerifiedException(boolean newEmailSent) {
        this.newEmailSent = newEmailSent;
    }

}
