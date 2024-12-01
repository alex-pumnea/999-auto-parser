package model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author Alex Pumnea
 */
public record Car(@NotBlank(message = "Make cannot be blank.") String make,
                  @Pattern(regexp = "\\d{4}|Year not available", message = "Year must be exactly 4 digits or 'Year not available'.") String year,
                  @Pattern(regexp = "\\d+|Договорная", message = "Price must be a valid number or 'Договорная'.") String price) {

    public static class Builder {
        private String make;
        private String year;
        private String price;

        public Builder setMake(String make) {
            this.make = make;
            return this;
        }

        public Builder setYear(String year) {
            this.year = year;
            return this;
        }

        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }

        public Car build() {
            return new Car(make, year, price);
        }
    }
}
