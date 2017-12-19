package com.minibox.vo;

public class GroupVo {
    private String groupName;
    private int quantity;
    private int empty;
    private String position;
    private int isNear;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getEmpty() {
        return empty;
    }

    public void setEmpty(int empty) {
        this.empty = empty;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getIsNear() {
        return isNear;
    }

    public void setIsNear(int isNear) {
        this.isNear = isNear;
    }
}
