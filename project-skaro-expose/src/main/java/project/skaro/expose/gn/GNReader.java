package project.skaro.expose.gn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import project.skaro.common.main.Executable;
import project.skaro.common.util.WebUtils;
import project.skaro.expose.common.CommonCaller;

import com.google.common.base.Strings;

/**
 * @version $Id$
 */
public class GNReader extends CommonCaller implements Executable {

    public void execute(String[] args) {
        try {
            String arg = (args == null || args.length != 1) ? null : args[0];
            (new GNReader()).read(getUrl(arg));
        }
        catch (IOException | ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
        }
    }
    
    protected static String getUrl(String q) {
        return Strings.isNullOrEmpty(q) ? getUrl() : (getUrl()+"&q="+q);
    }
    
    protected static String getUrl() {
        return "https://news.google.com/news?output=rss";
    }

    public void read(String sub) throws ClientProtocolException, UnsupportedEncodingException, IOException, ParserConfigurationException, SAXException {
        present(parse(call(getUrl())));
    }

    protected void present(List list) {
        for (int i = 0; i < list.size(); i++) {
            NewsItem item = (NewsItem) list.get(i);
            Display.console(item.title);
            Display.console(item.link);
            Display.console(item.description);
            Display.separate();
        }
    }
    
    protected List parse(String s) throws ParserConfigurationException, SAXException, IOException {
        List list = new ArrayList<NewsItem>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(s.getBytes()));
        parseChildNodes(list, document.getDocumentElement().getChildNodes());
        return list;
    }
    
    @SuppressWarnings("unchecked")
    private void parseChildNodes(List list, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                if (NewsItem.NODE_ITEM.equals(node.getNodeName()))
                    list.add(parseItem(node));
            }
            parseChildNodes(list, node.getChildNodes());
        }
    }

    private NewsItem parseItem(Node node) {
        NewsItem item = new NewsItem();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                String content = child.getLastChild().getNodeValue().trim();
                switch (child.getNodeName()) {
                    case NewsItem.NODE_TITLE :
                        item.title = content;
                        break;
                    case NewsItem.NODE_LINK :
                        item.link = WebUtils.parseLink(content).get(QY_URL);
                        if (item.link == null) item.link = content;
                        break;
                    case NewsItem.NODE_PUBDATE :
                        item.pubDate = content;
                        break;
                    case NewsItem.NODE_DESC :
                        item.description = content;
                        break;
                }
            }
        }
        return item;
    }
        
    private static final String QY_URL = "url";
    
    @SuppressWarnings("unused")
    private class NewsItem {
        private String title;
        private String link;
        private String pubDate;
        private String description;
        
        private static final String NODE_ITEM = "item";
        private static final String NODE_TITLE = "title";
        private static final String NODE_LINK = "link";
        private static final String NODE_PUBDATE = "pubDate";
        private static final String NODE_DESC = "description";

    }

}
