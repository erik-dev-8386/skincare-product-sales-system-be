package application.havenskin.enums;

public enum CategoryEnums {
    INACTIVE((byte) 0),
//    PENDING((byte) 1),
    ACTIVE((byte) 2);
//    DELETED((byte) 3);

    private final byte status;

    CategoryEnums(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

    public static CategoryEnums fromStatus(byte status) {
        for (CategoryEnums c : CategoryEnums.values()) {
            if(c.getStatus() == status){
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid status");
    }
}
