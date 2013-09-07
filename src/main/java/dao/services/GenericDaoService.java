package dao.services;

import java.io.Serializable;
import java.util.List;

/**
 * Interface que define metodos genericos para DAO.
 *
 * @author Maycon Antonio Junqueira Costa
 * @version 1.0
 *
 */
public interface GenericDaoService <T , ID extends Serializable>{

    /**
     * Pesquisa uma entidade no banco de dados a partir do seu Identificador.
     *
     * @param id
     * @param lock
     * @return entidade pesquisado no banco de dados
     * @since 1.0
     */
    T findById(ID id, boolean lock);

    /**
     * Pesquisa todos os registro de uma entidade.
     *
     * @return uma todos os registros da entidade
     * @since 1.0
     */
    List<T> findAll();

    /**
     *
     * @param size
     * @param first
     * @return
     */
    List<T> findAll(int size, int first);
    T makePersistent(T entity);
    T makeTransient(T entity);
    void update(T entity);
    T save (T entity);

    void saveList(List<T> entities);
    int rowCount();



}
