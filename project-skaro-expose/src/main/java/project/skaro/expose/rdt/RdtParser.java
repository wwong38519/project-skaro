package project.skaro.expose.rdt;

import java.util.ArrayList;
import java.util.List;

import project.skaro.expose.rdt.domain.RdtCmtResponse;
import project.skaro.expose.rdt.domain.RdtCmtResponseChildData;
import project.skaro.expose.rdt.domain.RdtResponse;
import project.skaro.expose.rdt.domain.RdtResponseChildData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @version $Id$
 */
public class RdtParser extends AbstractRdtParser {

    protected void parseSub(String s) {
        RdtResponse target = new Gson().fromJson(s, RdtResponse.class);
        List<RdtResponseChildData> children = target.getData().getChildren();
        for (RdtResponseChildData child : children) {
            Display.console(child.getData().getId(), child.getData().getTitle(), child.getData().getNum_comments());
            Display.console(child.getData().getUrl());
            Display.log();
        }
    }
    
    protected void parseComments(String s) {
        logger.debug(s);
        List<RdtCmtResponse> targets = new Gson().fromJson(s, new TypeToken<ArrayList<RdtCmtResponse>>() {}.getType());
        for (RdtCmtResponse target : targets) {
            List<RdtCmtResponseChildData> children = target.getData().getChildren();
            for (RdtCmtResponseChildData child : children)
                Display.console(child.getData().getUps(), child.getData().getBody());
        }
    }
}
