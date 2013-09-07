
package dao.services;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


    /**
    *
    * @author Maycon
    */
    public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    static Configuration conf;

        private static SessionFactory buildSessionFactory() {
            try {
                /*Configuration configuration = new Configuration();
                configuration.configure();
                Properties properties = configuration.getProperties();
                serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);  */
                //logger.warn("Entrando para configurar");
                conf = new Configuration().configure();
                return conf.buildSessionFactory();
            } catch (Throwable ex) {
                ex.printStackTrace();
                throw new ExceptionInInitializerError(ex);
            }
        }

        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

        public static void shutdown() {
            getSessionFactory().close();
        }

        public static Configuration getConfiguration(){
            return conf;
        }
    }
