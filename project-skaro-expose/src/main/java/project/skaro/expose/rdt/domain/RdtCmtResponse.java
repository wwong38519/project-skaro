package project.skaro.expose.rdt.domain;


public class RdtCmtResponse {
    public String toString() {
        return "Response [kind=" + kind + ", data=" + data + "]";
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RdtCmtResponseData getData() {
        return data;
    }

    public void setData(RdtCmtResponseData data) {
        this.data = data;
    }

    private String kind;
    private RdtCmtResponseData data;
}
