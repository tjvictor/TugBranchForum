package tugbranch.forum.model;

import java.util.List;

public class Post {
    private String id;
    private String title;
    private String content;
    private String staffId;
    private int viewCount;
    private int replyCount;
    private String categoryId;
    private String status;
    private boolean putTop;
    private boolean resolved;
    private boolean essence;
    private String dateTime;

    private Staff staff;

    private List<ReplyPost> replyPostList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPutTop() {
        return putTop;
    }

    public void setPutTop(boolean putTop) {
        this.putTop = putTop;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public boolean isEssence() {
        return essence;
    }

    public void setEssence(boolean essence) {
        this.essence = essence;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<ReplyPost> getReplyPostList() {
        return replyPostList;
    }

    public void setReplyPostList(List<ReplyPost> replyPostList) {
        this.replyPostList = replyPostList;
    }
}
