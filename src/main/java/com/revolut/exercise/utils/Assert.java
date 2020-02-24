package com.revolut.exercise.utils;

import java.math.BigDecimal;
import java.util.Objects;

public final class Assert {
    private static final String NOT_NULL_MSG_FORMAT = "Argument '%s' may not be null";
    private static final String NOT_NEGATIVE_MSG_FORMAT = "Argument may not be negative";

    private Assert() {
        // intentionally private and blank
    }

    /**
     * Checks that the specified {@code value} is null and throws {@link NullPointerException} with a customized error message if it is.
     *
     * @param value        the value to be checked.
     * @param argumentName the name of the argument to be used in the error message.
     * @return the {@code value}.
     * @throws NullPointerException if {@code value} is null.
     */

    public static <T> void requireNonNull(final T value, final String argumentName) {
        try {
            Objects.requireNonNull(argumentName, String.format(NOT_NULL_MSG_FORMAT, "argumentName"));
            Objects.requireNonNull(value, String.format(NOT_NULL_MSG_FORMAT, argumentName));
        } catch (final NullPointerException npe) {
            throw new IllegalArgumentException(npe);
        }
    }

    public static void requireNonNegative(final BigDecimal amount, final String argumentName){
        if (Objects.isNull(amount) || BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new IllegalArgumentException(argumentName +" : "+ NOT_NEGATIVE_MSG_FORMAT);
        }
    }

}