package eu.happycoders.shop.model.purchase;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.CartLineItem;
import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Accessors(fluent = true)
public class Purchase {

    private final PurchaseId id;
    private final Currency currency = Currency.getInstance("EUR");
    private final List<PurchaseLineItem> lineItems;
    private String notes;
    private final LocalDateTime createdAt = LocalDateTime.now();

    private Purchase(Cart cart) {
        this.id = PurchaseId.generate();
        this.lineItems = cart.lineItems()
                .stream()
                .map(Purchase::mapPurchaseLineItems)
                .toList();
    }

    public static Purchase fromCart(Cart cart) {
        return new Purchase(cart);
    }

    private static PurchaseLineItem mapPurchaseLineItems(CartLineItem cartLineItem) {
        Product product = cartLineItem.product();

        var builder = PurchaseLineItem.builder();

        builder
                .productId(product.id())
                .description(product.description());

        if (cartLineItem.hasDiscount()) {
            builder.discount(cartLineItem.discount());
        }

        builder
                .quantity(cartLineItem.quantity())
                .amount(cartLineItem.subTotal());

        return builder.build();
    }

    public Money subTotal() {
        return lineItems.stream()
                .map(PurchaseLineItem::subTotal)
                .reduce(Money::add)
                .orElse(new Money(currency, BigDecimal.ZERO));
    }
}
