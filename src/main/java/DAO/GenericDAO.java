package DAO;

import java.util.List;
import java.sql.SQLException;

public interface GenericDAO<T> {
    void save(T entity) throws SQLException;   // 保存实体
    T getById(int id) throws SQLException;     // 根据ID查询实体
    void update(T entity) throws SQLException; // 更新实体
    void delete(int id) throws SQLException;   // 删除实体
    List<T> getAll() throws SQLException;      // 获取所有实体
}
