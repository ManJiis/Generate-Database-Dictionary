package cn.tlh.ex.service;

import cn.tlh.ex.entity.Columns;
import cn.tlh.ex.entity.Tables;

import java.util.List;


/**
 * @author TANG
 */
public interface ExportService {

    List<Tables> getTables(String dbName);

    List<Columns> getColumns(String dbName, String tableName);
}