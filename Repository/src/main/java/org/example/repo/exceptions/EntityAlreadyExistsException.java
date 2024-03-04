package org.example.repo.exceptions;

/**
 * Class that represents a specific RepoException
 */
public class EntityAlreadyExistsException extends RepositoryException{

    /**
     * Returns an "Entity already exists" message
     */
    public EntityAlreadyExistsException() {
        super("Entity already exists!");
    }
}
