package com.rahulchauhan.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceRequest {

    @NotBlank
    private String userId;
    @Min(10)
    @Max(50)
    private Integer amount;
}

/**
 * Other annotations exists
 *
 * Here’s a quick overview:
 *
 * @AssertFalse / @AssertTrue → makes sure that a boolean field is set to false / true.
 *
 * @DecimalMin / @DecimalMax → makes sure that a number (BigDecimal, BigInteger, CharSequence, byte, short, int, long etc.) is >= or ⇐ a value. It’s an equivalent of @Min, @Max you used above.
 *
 * @Digits
 *
 * @Email → the string needs to be a well-formed e-mail address
 *
 * @Future / @FutureOrPresent → a date (pre Java 8 types and Java8+ types) needs to be in the future or present
 *
 * @Min / @Max → same as DecimalMin / DecimalMax
 *
 * @Negative / @NegativeOrZero → self-explanatory
 *
 * @NotBlank / @NotEmpty → a string must not be blank or empty
 *
 * @Null / @NotNull → self-explanatory
 *
 * @Past / @PastOrPresent → a date (pre Java 8 types and Java8+ types) needs to be in the past or present
 *
 * @Pattern → a string needs to match a regex pattern
 *
 * @Positive / @PositiveOrZero → self-explanatory
 *
 * @Size → the element size must be between a boundary. valid for strings, collections, maps, arrays.
 */
