package cn.tlh.ex.test;

import cn.tlh.ex.Application;
import cn.tlh.ex.dao.ExportDao;
import cn.tlh.ex.model.Columns;
import cn.tlh.ex.model.Tables;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: TANG
 * @description: 测试
 * @date: 2020-12-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ExportTest {

    @Resource
    ExportDao exportDao;

    @Test
    public void test1() {
        String dbName = "bfp2.0_dev";
        int count = 0;
        List<Tables> tablesByDbName = exportDao.getTables(dbName);
        for (Tables tables : tablesByDbName) {
            System.out.println("tables.toString() = " + tables.toString());
            count++;
        }
        System.out.println("一共 " + count + " 张表");
    }


    RowRenderData header;
    List<RowRenderData> tableDatas;

    public static final String dbName = "bfp2.0_dev";
    public static final String title = dbName + "数据库说明文档";

    @Test
    public void testExport() {
        List<Tables> tablesList = exportDao.getTables(dbName);
        // 表头
        RowRenderData header = this.getTableHeader();
        int count = 0;
        List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
        // 最终返回数据
        Map<String, Object> exportData = new HashMap<>();
        // 文档标题
        exportData.put("title", title);
        for (Tables table : tablesList) {
            count++;
            // 获取某张表的字段列表
            List<RowRenderData> rowList = getTableRow(table);
            Map<String, Object> data = new HashMap<>();
            data.put("no", count);
            data.put("tableComment", table.getTableComment());
            data.put("engine", table.getEngine());
            data.put("tableCollation", table.getTableCollation());
            data.put("tableType", table.getTableType());
            data.put("tableName", table.getTableName());
            // 一张表
            MiniTableRenderData miniTableRenderData = new MiniTableRenderData(header, rowList);
            // 自适应性宽度
//            miniTableRenderData.setWidth(0);
            miniTableRenderData.setWidth(20);
            data.put("tableData", miniTableRenderData);
            tableList.add(data);
        }
        exportData.put("tableList", new DocxRenderData(new File("src/main/resources/template/table_template.docx"), tableList));

        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/template/export_dic_template.docx").render(exportData);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dbName + "数据库文档(MySQL)" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".docx");
            System.out.println("生成文件结束");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("生成文件失败");
        } finally {
            try {
                template.write(out);
                assert out != null;
                out.flush();
                out.close();
                template.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字体样式
     *
     * @return Style
     */
    public Style getHeardFontStyle() {
        Style style = new Style();
        style.setBold(true);
        style.setFontSize(10);
//        style.setColor("000000"); // 黑色
        style.setColor("F8F8FF"); // 白色
        style.setFontFamily("宋体");
        return style;
    }

    // 表头单元格背景
    public TableStyle getHeaderTableStyle() {
        TableStyle style = new TableStyle();
//        style.setBackgroundColor("B7B7B7"); //灰色
        style.setBackgroundColor("4F81BD"); //深蓝色
//        style.setBackgroundColor("7BA0CE"); //中等蓝色
//        style.setBackgroundColor("DBE5F1"); //浅蓝色
        // 左对齐
//        style.setAlign(STJc.Enum.forInt(1));
        return style;
    }

    /**
     * 表头字体设置
     *
     * @return RowRenderData
     */
    private RowRenderData getTableHeader() {
        header = RowRenderData.build(
                new TextRenderData("序号", this.getHeardFontStyle()),
                new TextRenderData("字段名称", this.getHeardFontStyle()),
                new TextRenderData("字段描述", this.getHeardFontStyle()),
                new TextRenderData("字段类型", this.getHeardFontStyle()),
                new TextRenderData("长度", this.getHeardFontStyle()),
                new TextRenderData("允许空", this.getHeardFontStyle()),
                new TextRenderData("缺省值", this.getHeardFontStyle()));
        header.setRowStyle(this.getHeaderTableStyle());
        return header;
    }




    /**
     * 渲染一张表的行
     *
     * @return List<RowRenderData>
     */
    private List<RowRenderData> getTableRow(Tables table) {
        List<Columns> columnsList = exportDao.getColums(dbName, table.getTableName());
        List<RowRenderData> result = new ArrayList<>();
        for (Columns columns : columnsList) {
            Long characterMaximumLength = columns.getCharacterMaximumLength();
            characterMaximumLength = characterMaximumLength == null ? 0 : characterMaximumLength;
            // 渲染每一行数据
            RowRenderData row = RowRenderData.build(
                    new TextRenderData(columns.getOrdinalPosition().toString()),
                    new TextRenderData(columns.getColumnName()),
                    new TextRenderData(columns.getColumnComment()),
                    new TextRenderData(columns.getDataType()),
                    new TextRenderData(characterMaximumLength.toString()),
                    new TextRenderData(columns.getIsNullable()),
                    new TextRenderData(columns.getColumnDefault())
            );
            result.add(row);
        }
        return result;
    }

    /**
     * 获取数据库的所有表名及表的信息
     *
     * @return list
     */
    private static List<Map<String, String>> getTableName(List<Tables> tablesList) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Tables table : tablesList) {
            // 存放单独一张表的结构信息
            Map<String, String> result = new HashMap<>();
            result.put("table_name", table.getTableName());
            result.put("table_type", table.getTableType());
            result.put("engine", table.getEngine());
            result.put("table_collation", table.getTableCollation());
            result.put("table_comment", table.getTableComment());
            result.put("create_options", table.getCreateOptions());
            list.add(result);
        }
        return list;
    }

}


