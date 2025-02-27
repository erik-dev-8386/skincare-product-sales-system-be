package application.havenskin.enums;

public enum CoinWalletEnums {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);

    private final byte value;

    CoinWalletEnums(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static CoinWalletEnums fromValue(byte value) {
        for (CoinWalletEnums coinWalletEnums : CoinWalletEnums.values()) {
            if (coinWalletEnums.getValue() == value) {
                return coinWalletEnums;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
