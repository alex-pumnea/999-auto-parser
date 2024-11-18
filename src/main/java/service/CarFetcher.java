package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alex Pumnea
 */
public class CarFetcher implements Fetcher {
    private static final Logger logger = LoggerFactory.getLogger(CarFetcher.class);
    public static final String BASE_URL = "https://999.md/";

    @Override
    public Elements getCarElements(String url) {
        Document doc = fetchDocument(url);
        return doc.select(".ads-list-photo-item");
    }

    @Override
    public String getYearFromDetails(Element carDetails) {
        Element link = carDetails.select(".ads-list-photo-item-title a").first();
        if (link != null) {
            String fullLink = BASE_URL + link.attr("href");
            try {
                return getYearFromMain(Jsoup.connect(fullLink).get());

            } catch (IOException e) {
                logger.warn("Failed to fetch year from URL:  {}", fullLink);
            }
        }
        return "Year not available";
    }

    @Override
    public String getYearFromMain(Document doc) {
        Element yearElement = doc.select("span[itemprop=name]:contains(Год выпуска)").first();

        if (yearElement != null) {
            Element valueElement = yearElement.nextElementSibling();

            if (valueElement != null && valueElement.attr("itemprop").equals("value")) {
                return valueElement.text().trim();
            }
        }

        return "Year not available";
    }

    @Override
    public Document fetchDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch document from URL: " + url, e);
        }
    }
}
