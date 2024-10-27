package eu.happycoders.shop.model.purchase;

public record PurchaseId(int value) {

    public PurchaseId {
        if (value < 1) {
            throw new IllegalArgumentException("'value' must be a positive integer");
        }
    }

    public static PurchaseId generate() {
        return new PurchaseId((int) (Math.random() * Integer.MAX_VALUE));
    }
}
