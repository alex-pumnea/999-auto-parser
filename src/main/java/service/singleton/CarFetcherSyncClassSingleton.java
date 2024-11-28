package service.singleton;

import service.base.BaseFetcher;

/**
 * Synchronized double check locking singleton implementation
 *
 * @author Alex Pumnea
 */


public class CarFetcherSyncClassSingleton extends BaseFetcher {
    private static volatile CarFetcherSyncClassSingleton instance;

    private CarFetcherSyncClassSingleton() {
    }

    public static CarFetcherSyncClassSingleton getInstance() {
        if (instance == null) {
            synchronized (CarFetcherSyncClassSingleton.class) {
                if (instance == null) {
                    instance = new CarFetcherSyncClassSingleton();
                }
            }
        }
        return instance;
    }
}
