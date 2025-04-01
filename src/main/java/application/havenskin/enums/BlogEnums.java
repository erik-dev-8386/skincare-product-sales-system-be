package application.havenskin.enums;

public enum BlogEnums {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);

    private final byte blog_status;

    BlogEnums(byte blog_status) {
        this.blog_status = blog_status;
    }

    public byte getBlog_status() {
        return blog_status;
    }

    public static BlogEnums fromEnums(byte blog_status) {
        for (BlogEnums enums : BlogEnums.values()) {
            if(enums.getBlog_status() == blog_status){
                return enums;
            }
        }
        throw new IllegalArgumentException("Invalid status");
    }
}
