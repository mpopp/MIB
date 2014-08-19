package mib.microservice.commons.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by matthias.popp on 18.07.2014.
 * <p/>
 * Class containing needed perisistence helper methods. This class also creates and manages the entity manager instance.
 */
public class PersistenceUtils {

    private static EntityManagerFactory emf;
    /**
     * No instances should be created.
     */
    private PersistenceUtils(){}

    /**
     * Initialization of the entity manager. This is an EXPENSIVE operation. Only call that once on application level
     * when starting up the application.
     */
    public static void initialize(String persistenceUnitName) {
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public static EntityManager openEm() {
        return emf.createEntityManager();
    }

    public static void closeEm(EntityManager em) {em.close();}

    public static void startTransaction(EntityManager em){
        em.getTransaction().begin();
    }

    public static void commitTransaction(EntityManager em){
        em.getTransaction().commit();
    }

    public static boolean isTransactionActive(EntityManager em){
        return em.getTransaction().isActive();
    }
}
