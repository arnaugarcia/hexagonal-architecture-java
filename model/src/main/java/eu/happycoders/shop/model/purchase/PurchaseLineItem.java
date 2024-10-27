package eu.happycoders.shop.model.purchase;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseLineItem {

    private final ProductId productId;
    private String description;
    private Money amount;
    private int quantity;
    private int discount;

    public boolean hasDiscount() {
        return discount > 0;
    }

    public Money subTotal() {
        var productAmount = amount.multiply(quantity);
        if (hasDiscount()) {
            return productAmount.multiply(100 - discount() / 100);
        }
        return productAmount;
    }
}
