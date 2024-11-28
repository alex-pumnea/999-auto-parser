package service.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.api.Fetcher;

import java.io.IOException;

/**
 * Base fetcher holding fetching logic
 *
 * @author Alex Pumnea
 */

public abstract class BaseFetcher implements Fetcher {
    private static final Logger logger = LoggerFactory.getLogger(BaseFetcher.class);
    protected static final String BASE_URL = "https://999.md/";

    protected Document doFetchDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    @Override
    public final Elements getCarElements(String url) {
        Document doc = fetchDocument(url);
        return doc.select(".ads-list-photo-item");
    }

    @Override
    public final String getYearFromDetails(Element carDetails) {
        Element link = carDetails.select(".ads-list-photo-item-title a").first();
        if (link != null) {
            String fullLink = BASE_URL + link.attr("href");
            try {
                return getYearFromMain(Jsoup.connect(fullLink).get());
            } catch (IOException e) {
                logger.warn("Failed to fetch year from URL: {}", fullLink);
            }
        }
        return "Year not available";
    }

    @Override
    public final String getYearFromMain(Document doc) {
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
    public final Document fetchDocument(String url) {
        try {
            return doFetchDocument(url);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch document from URL: " + url, e);
        }
    }
}
