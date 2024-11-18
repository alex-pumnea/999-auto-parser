package repository;

import java.util.List;

/**
 * @author Alex Pumnea
 */
public interface Repository<T> {
    List<T> getAll();

    void add(T t);

    void addAll(List<T> t);
}
