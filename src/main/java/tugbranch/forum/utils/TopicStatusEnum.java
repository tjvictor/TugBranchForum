package tugbranch.forum.utils;

public enum TopicStatusEnum {

    OPEN("开放", 1),
    CLOSED("结贴", 2);

    private String name;
    private int index;
    private TopicStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public static String getName(int index) {
        for (TopicStatusEnum c : TopicStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
}
