package project.skaro.expose.rdt.domain;

import java.util.List;

public class RdtCmtResponseData {
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
    public List<RdtCmtResponseChildData> getChildren() {
        return children;
    }
    public void setChildren(List<RdtCmtResponseChildData> children) {
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
    private List<RdtCmtResponseChildData> children;
    private String after;
    private String before;
}