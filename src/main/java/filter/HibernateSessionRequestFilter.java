package filter;

import dao.services.HibernateUtil;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import javax.servlet.*;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import util.Util;

/**
 * Filtro que incia uma transação na sessão do hibernate quando uma pagina e
 * requisitada.
 *
 * @author Maycon Antonio Junqueira Costa
 * @version 1.0
 */
public class HibernateSessionRequestFilter implements Filter {

    // Objeto que salva os erros/exceptions no arquivo de log.
    static Logger logger = Logger.getLogger(HibernateSessionRequestFilter.class);
    // Instancia que guarda a fabrica de conexões.
    private SessionFactory sessionFactory;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // Session factory retorna a sessão atual e incia uma transação.
            sessionFactory.getCurrentSession().beginTransaction();
            chain.doFilter(request, response);
            // Após o termino da requisação, sicronizamos os dados da memoria com os dados
            // do banco de dados usando o commit.
            sessionFactory.getCurrentSession().getTransaction().commit();
        //} catch(org.postgresql.util.PSQLException eex){
            
        } catch (PersistenceException e) {
            System.out.println("Entramos");
            Throwable lastCause = e;
            String constraintName = null;
            while (lastCause != null) {
                if (lastCause.toString().startsWith("java.sql.BatchUpdateException")) {
                    BatchUpdateException bu = (BatchUpdateException) lastCause;                    
                }
                lastCause = lastCause.getCause();
            }
            if (constraintName != null) {
                throw new ConstraintViolationException("Mensagem", new SQLException(), constraintName);
            }
        } /*catch (java.io.IOException e) {
         e.printStackTrace();
         } catch (Exception e) {
         System.out.println("Error 2");
         e.printStackTrace();
         try {
         // Se houve algum erro com o banco de dados, verifica se a transação está ativada,
         // caso esteja iniamos um roolback, para desfazer as ultimas modificações.
         if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
         sessionFactory.getCurrentSession().getTransaction().rollback();
         }
         } catch (Throwable rbEx) {
         logger.info(rbEx.getMessage(), rbEx);
         }
         // throw new ServletException(e);
         }*/ catch (StaleObjectStateException staleEx) {
            //logger.info(staleEx.getMessage(), staleEx);
            staleEx.printStackTrace();
            //throw staleEx;
        } catch (Throwable ex) {
           ex.printStackTrace();
            try {
                // Se houve algum erro com o banco de dados, verifica se a transação está ativada,
                // caso esteja iniamos um roolback, para desfazer as ultimas modificações.
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable rbEx) {
             //   logger.info(rbEx.getMessage(), rbEx);
            }
            //logger.info(ex.getMessage(), ex);
            //throw ex;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Obtendo a fabrica de conexões do hibernate.
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void destroy() {
    }
}
