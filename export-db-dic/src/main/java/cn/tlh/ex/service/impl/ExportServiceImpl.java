package cn.tlh.ex.service.impl;

import cn.tlh.ex.dao.ExportDao;
import cn.tlh.ex.model.Columns;
import cn.tlh.ex.model.Tables;
import cn.tlh.ex.service.ExportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author TANG
 */
@Service("tablesService")
public class ExportServiceImpl implements ExportService {
    @Resource
    private ExportDao exportDao;


    @Override
    public List<Tables> getTables(String dbName) {
        return this.exportDao.getTables(dbName);
    }

    @Override
    public List<Columns> getColumns(String dbName, String tableName) {
        return this.exportDao.getColums(dbName, tableName);
    }
}