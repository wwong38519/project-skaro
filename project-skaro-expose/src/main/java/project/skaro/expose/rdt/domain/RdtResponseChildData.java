package project.skaro.expose.rdt.domain;


public class RdtResponseChildData {
    public String toString() {
        return "ResponseChildData [kind=" + kind + ", data=" + data + "]";
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public RdtChildData getData() {
        return data;
    }
    public void setData(RdtChildData data) {
        this.data = data;
    }
    private String kind;
    private RdtChildData data;
}