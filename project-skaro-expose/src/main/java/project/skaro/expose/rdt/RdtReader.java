package project.skaro.expose.rdt;

import java.util.List;

import project.skaro.expose.rdb.Readb;
import project.skaro.expose.rdt.domain.RdtResponse;
import project.skaro.expose.rdt.domain.RdtResponseChildData;

import com.google.gson.Gson;

/**
 * @version $Id$
 */
public class RdtReader extends RdtParser {

    protected void parseSub(String s) {
        Readb readb = new Readb();
        RdtResponse target = new Gson().fromJson(s, RdtResponse.class);
        List<RdtResponseChildData> children = target.getData().getChildren();
        for (RdtResponseChildData child : children) {
            Display.console(child.getData().getId(), child.getData().getTitle(), child.getData().getNum_comments());
            Display.console(child.getData().getUrl());
            readb.read(child.getData().getUrl());
            Display.log();
        }
    }
}
