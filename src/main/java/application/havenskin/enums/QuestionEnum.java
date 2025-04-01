package application.havenskin.enums;

public enum QuestionEnum {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);

    private final byte status;

    QuestionEnum(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

    public static QuestionEnum fromStatus(byte status) {
        for (QuestionEnum q : QuestionEnum.values()) {
            if (q.getStatus() == status) {
                return q;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
