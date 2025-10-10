package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EntityService {
    private final EntityRepository entityRepository;
    private final EntityMapper entityMapper;

    @Transactional
    public EntityDTO createEntity(@Valid CreateEntityRequest request) {
        Entity entity = entityMapper.toEntity(request);
        entity = entityRepository.save(entity);
        return entityMapper.toDto(entity);
    }
}