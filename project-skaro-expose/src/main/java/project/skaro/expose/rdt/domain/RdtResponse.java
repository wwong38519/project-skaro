package project.skaro.expose.rdt.domain;


public class RdtResponse {
    public String toString() {
        return "Response [kind=" + kind + ", data=" + data + "]";
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RdtResponseData getData() {
        return data;
    }

    public void setData(RdtResponseData data) {
        this.data = data;
    }

    private String kind;
    private RdtResponseData data;
}
