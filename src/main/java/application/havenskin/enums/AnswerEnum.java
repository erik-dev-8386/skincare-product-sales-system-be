package application.havenskin.enums;

public enum AnswerEnum {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);

    private final byte status;

    AnswerEnum(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }
    public static AnswerEnum getByStatus(byte status) {
        for (AnswerEnum answerEnum : AnswerEnum.values()) {
            if (answerEnum.getStatus() == status) {
                return answerEnum;
            }
        }
        throw new IllegalArgumentException("Invalid answer status");
    }
}
