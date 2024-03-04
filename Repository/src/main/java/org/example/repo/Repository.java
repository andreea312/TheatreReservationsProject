package org.example.repo;

import org.example.domain.Entity;
import org.example.repo.exceptions.RepositoryException;

/**
 * Interface for repository
 * @param <EntityType>
 */
public interface Repository<ID, EntityType extends Entity<ID>> {
    void add(EntityType entity);
    void remove(EntityType entity);
    void update(EntityType entity);
    EntityType getById(int id);
    Iterable<EntityType> getAll();
}


