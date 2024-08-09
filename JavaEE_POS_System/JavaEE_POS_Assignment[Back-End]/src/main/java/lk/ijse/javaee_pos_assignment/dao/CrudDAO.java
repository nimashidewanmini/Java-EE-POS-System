package lk.ijse.javaee_pos_assignment.dao;

import java.util.ArrayList;

public interface CrudDAO <T,C,ID> extends SuperDAO{
    boolean save(T t,C c);
    boolean update(T t,C c);
    boolean delete(ID id,C c);
    ArrayList<T> getAll(C c);
    T search(C c,ID id);
}
