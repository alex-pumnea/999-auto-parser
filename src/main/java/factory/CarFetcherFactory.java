package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.api.Fetcher;
import service.impl.CarFetcher;
import service.singleton.CarFetcherClassSingleton;
import service.singleton.CarFetcherEnumSingleton;
import service.singleton.CarFetcherOnDemandSingleton;
import service.singleton.CarFetcherSyncClassSingleton;

/**
 * @author Alex Pumnea
 */
public class CarFetcherFactory extends FetcherFactory {
    private static final Logger logger = LoggerFactory.getLogger(CarFetcherFactory.class);

    @Override
    public Fetcher getCarFetcher(String type) {
        if (type == null || type.isBlank()) {
            logger.warn("Received unknown type, defaulting to POJO fetcher");
            return new CarFetcher();
        }

        return switch (type) {
            case "1" -> {
                logger.info("Creating Class-based Singleton fetcher");
                yield CarFetcherClassSingleton.getInstance();
            }
            case "2" -> {
                logger.info("Creating Class-based Synchronized Singleton fetcher");
                yield CarFetcherSyncClassSingleton.getInstance();
            }
            case "3" -> {
                logger.info("Creating Class-based On-Demand Holder Singleton fetcher");
                yield CarFetcherOnDemandSingleton.getInstance();
            }
            case "4" -> {
                logger.info("Creating Enum-based Singleton fetcher");
                yield CarFetcherEnumSingleton.INSTANCE;
            }
            default -> {
                logger.info("Creating POJO fetcher");
                yield new CarFetcher();
            }
        };
    }
}
