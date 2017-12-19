package com.minibox.po;

/**
 * @author MEI
 */
public class Box {
    private int boxId;
    private String boxSize;
    private int boxStatus;
    private int groupId;

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public int getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(int boxStatus) {
        this.boxStatus = boxStatus;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
