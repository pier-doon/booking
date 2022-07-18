package com.ws.hotelsearch.mapper;

import com.ws.hotelsearch.dto.AbstractDto;
import com.ws.hotelsearch.model.AbstractEntity;

import java.util.Collection;
import java.util.List;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(Collection<D> dtos);

    List<D> toDto(Collection<E> entities);
}