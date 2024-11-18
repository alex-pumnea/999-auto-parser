package model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author Alex Pumnea
 */
public record Car(@NotBlank(message = "Make cannot be blank.") String make,
                  @Pattern(regexp = "\\d{4}|Year not available", message = "Year must be exactly 4 digits or 'Year not available'.") String year,
                  @Pattern(regexp = "\\d+|Договорная", message = "Price must be a valid number or 'Договорная'.") String price) {

}
