package project.skaro.expose.rdt.domain;


public class RdtCmtResponseChildData {
    public String toString() {
        return "ResponseChildData [kind=" + kind + ", data=" + data + "]";
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public RdtCmtChildData getData() {
        return data;
    }
    public void setData(RdtCmtChildData data) {
        this.data = data;
    }
    private String kind;
    private RdtCmtChildData data;
}