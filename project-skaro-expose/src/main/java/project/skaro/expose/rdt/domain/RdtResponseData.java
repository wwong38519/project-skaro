package project.skaro.expose.rdt.domain;

import java.util.List;

public class RdtResponseData {
    public String toString() {
        return "ResponseData [modhash="
            + modhash
            + ", children="
            + children
            + ", after="
            + after
            + ", before="
            + before
            + "]";
    }
    public String getModhash() {
        return modhash;
    }
    public void setModhash(String modhash) {
        this.modhash = modhash;
    }
    public List<RdtResponseChildData> getChildren() {
        return children;
    }
    public void setChildren(List<RdtResponseChildData> children) {
        this.children = children;
    }
    public String getAfter() {
        return after;
    }
    public void setAfter(String after) {
        this.after = after;
    }
    public String getBefore() {
        return before;
    }
    public void setBefore(String before) {
        this.before = before;
    }
    private String modhash;
    private List<RdtResponseChildData> children;
    private String after;
    private String before;
}