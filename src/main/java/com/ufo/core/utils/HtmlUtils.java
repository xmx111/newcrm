package com.ufo.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlUtils {

    public static String innerText(String html) {
        if (StringUtils.isBlank(html)) {
            return "";
        }
        try {
            Parser parser = new Parser();
            parser.setInputHTML(html);
            Node node = parser.elements().nextNode();
            return node.toPlainTextString();
        } catch (ParserException e) {
            return html;
        }
    }

    public static List<String> getImages(String html) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isNotBlank(html)) {
            try {
                Parser parser = new Parser();
                parser.setInputHTML(html);
                NodeList imags = parser.extractAllNodesThatMatch(new TagNameFilter("img"));
                int size = imags.size();
                for (int i = 0; i < size; i++) {
                    ImageTag node = (ImageTag) imags.elementAt(i);
                    result.add(node.getAttribute("src"));
                }

            } catch (ParserException e) {
                //donothing
            }
        }

        return result;
    }
}
