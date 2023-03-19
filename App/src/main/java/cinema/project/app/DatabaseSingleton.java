package cinema.project.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;



public class DatabaseSingleton{
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static EntityTransaction transaction = entityManager.getTransaction();


    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    public static EntityManager getEntityManager() {
        return entityManager;
    }
    public static EntityTransaction getTransaction() {
        return transaction;
    }


}



