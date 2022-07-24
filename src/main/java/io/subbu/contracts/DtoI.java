package io.subbu.contracts;

public interface DtoI<T, E> {
    T createEntity();

    E createDtoFromEntity(T t);
}
