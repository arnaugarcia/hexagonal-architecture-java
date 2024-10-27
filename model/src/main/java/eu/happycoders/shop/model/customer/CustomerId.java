package eu.happycoders.shop.model.customer;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * A customer ID value object (enabling type-safety and validation).
 *
 * @author Sven Woltmann
 */
@ValueObject
public record CustomerId(int value) {

  public CustomerId {
    if (value < 1) {
      throw new IllegalArgumentException("'value' must be a positive integer");
    }
  }
}
