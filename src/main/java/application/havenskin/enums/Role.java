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
    public enum Status {
        INACTIVE((byte) 1),
        ACTIVE((byte) 2);

        private final byte status;

        Status(byte status) {
            this.status = status;
        }

        public byte getStatus() {
            return status;
        }

        public static Status fromValue(byte status) {
            for (Status statuss : Status.values()) {
                if(statuss.status == status) {
                    return statuss;
                }
            }
            throw new IllegalArgumentException("Unknown status value: " + status);
        }
    }
}
