package com.rest.payments.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessages {

    public static final String NO_CUSTOMER_FOUND_MESSAGE = "There is no customer with id: ";

    public static final String NO_PAYMENT_FOUND_MESSAGE = "There is no payment with id: ";

    public static final String AN_ISSUE_OCCURRED = "An issue occurred during '%s' request";

}
