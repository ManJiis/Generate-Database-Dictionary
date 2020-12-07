package cn.tlh.ex.test;

import cn.tlh.ex.Application;
import cn.tlh.ex.dao.ExportDao;
import cn.tlh.ex.entity.Tables;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.*;

import static javax.swing.UIManager.put;

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

    @Test
    public void test2() {
        RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "姓名"), new TextRenderData("FFFFFF", "学历"));

        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        put("table", new MiniTableRenderData(header, Arrays.asList(row0, row1)));
    }

    RowRenderData header;
    List<RowRenderData> tableDatas;

    @Before
    public void init() {
        header = new RowRenderData(
                Arrays.asList(
                        new TextRenderData("FFFFFF", "序号"),
                        new TextRenderData("FFFFFF", "字段名称"),
                        new TextRenderData("FFFFFF", "字段描述"),
                        new TextRenderData("FFFFFF", "字段类型"),
                        new TextRenderData("FFFFFF", "长度"),
                        new TextRenderData("FFFFFF", "允许空"),
                        new TextRenderData("FFFFFF", "缺省值")
                ),
                "ff9800");
        RowRenderData row0 = RowRenderData.build("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装");
        RowRenderData row1 = RowRenderData.build("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装");
        RowRenderData row2 = RowRenderData.build("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构");
        RowRenderData row3 = RowRenderData.build("OpenOffice", "需要安装OpenOffice软件", "复杂，需要了解OpenOffice的API");
        RowRenderData row4 = RowRenderData.build("Jacob、winlib", "Windows平台", "复杂，不推荐使用");
        tableDatas = Arrays.asList(row0, row1, row2, row3, row4);
    }

    @Test
    public void testRenderMap() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("header", "Deeply love what you love.");
                put("name", "Poi-tl");
                put("word", "模板引擎");
                put("time", "2019-05-31");
                put("what", "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
                put("author", new TextRenderData("000000", "Sayi卅一"));
                put("introduce", new HyperLinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
                put("portrait", new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
                // 表格
                put("solution_compare", new MiniTableRenderData(header, tableDatas, MiniTableRenderData.WIDTH_A4_FULL));
                // 有序列表
                put("feature", new NumbericRenderData(new ArrayList<TextRenderData>() {
                    {
                        add(new TextRenderData("Plug-in grammar, add new grammar by yourself"));
                        add(new TextRenderData("Supports word text, local pictures, web pictures, table, list, header, footer..."));
                        add(new TextRenderData("Templates, not just templates, but also style templates"));
                    }
                }));
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/template/db-dic-template.docx").render(datas);
        FileOutputStream out = new FileOutputStream("out_template.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
