package service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Alex Pumnea
 */public interface Fetcher {
    Elements getCarElements(String url);

    String getYearFromDetails(Element carDetails);

    String getYearFromMain(Document doc);

    Document fetchDocument(String url);
}
