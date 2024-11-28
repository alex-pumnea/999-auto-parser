package factory;

import service.api.Fetcher;

/**
 * @author Alex Pumnea
 */
public abstract class FetcherFactory {
    public abstract Fetcher getCarFetcher(String type);
}
