package application.havenskin.enums;

public enum TransactionsEnums {
    CANCELED((byte) 1),
    NOT_PAID((byte) 2),
    PROCESSING((byte) 3),
    PAID((byte) 4);

    private final byte value;

    TransactionsEnums(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
    public static TransactionsEnums fromValue(byte value) {
        for (TransactionsEnums x : TransactionsEnums.values()) {
            if (x.getValue() == value) {
                return x;
            }
        }
        throw new IllegalArgumentException("No TransactionsEnums with value " + value);
    }

    // For type
    public enum Type {
        MOMO((byte) 1),
        COD((byte) 2);

        private final byte value;

        Type(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        public static Type fromValue(byte value) {
            for (Type x : Type.values()) {
                if (x.getValue() == value) {
                    return x;
                }
            }
            throw new IllegalArgumentException("No TransactionsEnums Type with value " + value);
        }
    }
}
