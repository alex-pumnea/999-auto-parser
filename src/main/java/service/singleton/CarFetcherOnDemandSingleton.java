package service.singleton;

import service.base.BaseFetcher;

/**
 * On-demand holder singleton implementation
 *
 * @author Alex Pumnea
 */

public class CarFetcherOnDemandSingleton extends BaseFetcher {

    private CarFetcherOnDemandSingleton() {
    }

    private static class SingletonHolder {
        private static final CarFetcherOnDemandSingleton INSTANCE = new CarFetcherOnDemandSingleton();
    }

    public static CarFetcherOnDemandSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

}