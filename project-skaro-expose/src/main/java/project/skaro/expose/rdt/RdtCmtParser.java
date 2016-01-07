package project.skaro.expose.rdt;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.comparators.ComparatorChain;

import project.skaro.common.util.Constants;
import project.skaro.expose.rdb.Readb;
import project.skaro.expose.rdt.domain.RdtResponse;
import project.skaro.expose.rdt.domain.RdtResponseChildData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RdtCmtParser extends AbstractRdtParser implements Constants {

    protected void parseSub(String s) {
        RdtResponse target = new Gson().fromJson(s, RdtResponse.class);
        List<RdtResponseChildData> children = target.getData().getChildren();
        for (RdtResponseChildData child : children)
            Display.console(child.getData().getId(), child.getData().getTitle(), child.getData().getAuthor(), child.getData().getNum_comments());
    }
    
    protected void parseComments(String s) {
        JsonElement root = new JsonParser().parse(s);
        parseElement(root, 0, null);
        read();
    }
    
    protected void read() {
        if (link == null) return;
        Display.console(link.title, link.id, link.subreddit, link.author, link.score, link.num_comments);
        Display.console(link.url);
        Readb readb = new Readb();
        readb.read(link.url);
        Display.separate();
        sort(comments);
        for (Comment c : comments)
            readComments(c);
        Display.separate();
        execute(ACT_SUB, link.subreddit);
    }
    
    @SuppressWarnings("unchecked")
    protected void sort(List<Comment> comments) {
        ComparatorChain comparators = new ComparatorChain();
        comparators.addComparator(new Comparator<Comment>(){
            public int compare(Comment o1, Comment o2) {
                return o1.ups - o2.ups;
            }
        }, true);
        comparators.addComparator(new Comparator<Comment>(){
            public int compare(Comment o1, Comment o2) {
                return o1.downs - o2.downs;
            }
        });
        Collections.sort(comments, comparators);
        for (Comment c : comments)
            if (c.replies != null)
                sort(c.replies);
    }
    
    protected void readComments(Comment c) {
        if (c == null) return;
        String indent = BLANK;
        for (int i = 0; i < c.level; i++) indent += INDENT;
        Display.console(indent, c.body, c.author, c.parent_id, c.id, c.ups, c.downs);
        if (c.replies != null)
            for (Comment r : c.replies)
                readComments(r);
    }
    
    protected void parseElement(JsonElement e, int level, Comment parent) {
        if (e == null) return;
        if (e.isJsonNull())
            return;
        else if (e.isJsonPrimitive())
            return;
        else if (e.isJsonArray())
            parseArray((JsonArray) e, level, parent);
        else if (e.isJsonObject())
            parseObject((JsonObject) e, level, parent);
     }
    
    protected void parseArray(JsonArray e, int level, Comment parent) {
        if (e == null) return;
        for (int i = 0, l = e.size(); i < l; i++)
            parseElement(e.get(i), level, parent);
    }
    
    protected void parseObject(JsonObject e, int level, Comment parent) {
        if (e == null) return;
//        Set<Entry<String, JsonElement>> set = e.entrySet();
//        Iterator<Entry<String, JsonElement>> i = set.iterator();
//        while (i.hasNext()) {
//            Entry<String, JsonElement> entry = i.next();
//            String key = entry.getKey();
//            JsonElement value = entry.getValue();
//            CommonUtils.cleanConsole("key:", key);
//            parseElement(value);
//        }
        parseContents(e, level, parent);
    }
    
    protected void parseContents(JsonObject e, int level, Comment parent) {
        try {
            if (equal(e, "kind", "t1")) {
                JsonObject data = getObject(e, "data");
                Comment c = new Comment();
                c.id = getString(data, "id");
                c.author = getString(data, "author");
                c.body = getString(data, "body");
                c.ups = getInt(data, "ups");
                c.downs = getInt(data, "downs");
                c.level = level;
                if (parent != null) {
                    if (parent.replies == null) parent.replies = new LinkedList<Comment>();
                    parent.replies.add(c);
                    c.parent_id = parent.id;
                } else {
                    if (comments == null) comments = new LinkedList<Comment>();
                    comments.add(c);
                }
                parseElement(getElement(data, "replies", "data", "children"), level+1, c);
            } else
            if (equal(e, "kind", "t3")) {
                JsonObject data = getObject(e, "data");
                Link k = new Link();
                k.id = getString(data, "id");
                k.title = getString(data, "title");
                k.subreddit = getString(data, "subreddit");
                k.author = getString(data, "author");
                k.url = getString(data, "url");
                k.ups = getInt(data, "ups");
                k.score = getInt(data, "score");
                k.num_comments = getInt(data, "num_comments");
                link = k;
            } else {
                parseElement(getElement(e, "data", "children"), level+1, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private JsonElement getElement(JsonElement e, String... key) {
        JsonElement r = e;
        for (int i = 0; i < key.length && r != null; i++) r = getElement(r, key[i]);
        return r;
    }
    private JsonElement getElement(JsonElement e, String key) {
        return (e != null && e.isJsonObject() && ((JsonObject) e).get(key) != null) ? ((JsonObject) e).get(key) : null ;
    }
    private JsonObject getObject(JsonObject e, String key) {
        return (JsonObject) ((e != null && e.get(key) != null) ? e.get(key) : null);
    }
    private String getString(JsonObject e, String key) {
        return getElement(e, key) != null ? getElement(e, key).getAsString() : null;
    }
    private int getInt(JsonObject e, String key) {
        return getElement(e, key) != null ? getElement(e, key).getAsInt() : null;
    }
    private boolean equal(JsonObject e, String key, String value) {
        return getString(e, key) != null ? getString(e, key).equals(value) : false;
    }
    
    private Link link;
    private List<Comment> comments;
    
    private class Link {
        public String toString() {
            return "Link [id=" + id + ", title=" + title + ", subreddit=" + subreddit + ", author=" + author + ", url=" + url + ", ups=" + ups + ", score=" + score + ", num_comments=" + num_comments + "]";
        }
        private String id;
        private String title;
        private String subreddit;
        private String author;
        private String url;
        private int ups;
        private int score;
        private int num_comments;
    }

    private class Comment {
        public String toString() {
            return "Comment [level=" + level + ", parent_id=" + parent_id + " id=" + id + ", author=" + author + ", body=" + body + ", ups=" + ups + ", downs=" + downs + "]";
        }
        private String id;
        private String author;
        private String body;
        private int ups;
        private int downs;
        private int level;
        private String parent_id;
        private List<Comment> replies;
    }

}
