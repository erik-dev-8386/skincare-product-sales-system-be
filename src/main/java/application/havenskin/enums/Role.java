package application.havenskin.enums;

public enum Role {
    ADMIN((byte) 1),
    STAFF((byte) 2),
    CUSTOMER((byte) 3);

    private final byte value;

    Role(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static Role fromValue(byte value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role value: " + value);
    }
}
