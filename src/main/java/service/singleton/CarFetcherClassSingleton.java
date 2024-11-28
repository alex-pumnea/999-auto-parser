package service.singleton;

import service.base.BaseFetcher;

/**
 * Not tread safe singleton implementation
 * @author Alex Pumnea
 */

public final class CarFetcherClassSingleton extends BaseFetcher {
    private static CarFetcherClassSingleton instance;

    private CarFetcherClassSingleton() {
    }

    public static CarFetcherClassSingleton getInstance() {
        if (instance == null) {
            instance = new CarFetcherClassSingleton();
        }
        return instance;
    }
}
