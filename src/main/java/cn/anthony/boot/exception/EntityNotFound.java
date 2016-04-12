package cn.anthony.boot.exception;

public class EntityNotFound extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6871724929507065855L;

    public EntityNotFound(String className) {
	super(className);
    }
}
