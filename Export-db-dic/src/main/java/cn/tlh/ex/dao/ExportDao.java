package cn.tlh.ex.dao;

import cn.tlh.ex.model.Columns;
import cn.tlh.ex.model.Tables;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author TANG
 * @description 导出
 * @date 2020-12-07
 */
public interface ExportDao {

    List<Tables> getTables(String dbName);

    List<Columns> getColums(String dbName, String tableName);

    List<Columns> getColumsByTableList(@Param("dbName") String dbName, @Param("list") List<String> list);

}
