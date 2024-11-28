package service.singleton;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.api.Fetcher;
import service.base.BaseFetcher;

/**
 * Enum based singleton implementation
 *
 * @author Alex Pumnea
 */

public enum CarFetcherEnumSingleton implements Fetcher {
    INSTANCE;

    private final BaseFetcher fetcher = new BaseFetcher() {
    };

    @Override
    public Elements getCarElements(String url) {
        return fetcher.getCarElements(url);
    }

    @Override
    public String getYearFromDetails(Element carDetails) {
        return fetcher.getYearFromDetails(carDetails);
    }

    @Override
    public String getYearFromMain(Document doc) {
        return fetcher.getYearFromMain(doc);
    }

    @Override
    public Document fetchDocument(String url) {
        return fetcher.fetchDocument(url);
    }
}
