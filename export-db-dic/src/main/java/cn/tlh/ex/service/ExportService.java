package cn.tlh.ex.service;


import cn.tlh.ex.model.Columns;
import cn.tlh.ex.model.Tables;

import java.util.List;


/**
 * @author TANG
 */
public interface ExportService {

    List<Tables> getTables(String dbName);

    List<Columns> getColumns(String dbName, String tableName);
}