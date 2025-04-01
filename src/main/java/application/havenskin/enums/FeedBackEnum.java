package application.havenskin.enums;

public enum FeedBackEnum {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);
    private final byte feedBack_status;

    FeedBackEnum(byte feedBack_status) {
        this.feedBack_status = feedBack_status;
    }

    public byte getFeedBack_status() {
        return feedBack_status;
    }

    public static FeedBackEnum getFeedBackEnum(byte feedBack_status) {
        for (FeedBackEnum feedBackEnum : FeedBackEnum.values()) {
            if (feedBackEnum.feedBack_status == feedBack_status) {
                return feedBackEnum;
            }
        }
        throw new IllegalArgumentException("Invalid feedback status: " + feedBack_status);
    }
}
