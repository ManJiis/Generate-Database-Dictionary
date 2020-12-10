package cn.tlh.ex.test;

import cn.tlh.ex.Application;
import cn.tlh.ex.example.MyDataModel;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.*;

/**
 * @author: TANG
 * @description:
 * @date: 2020-12-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class XWPFTemplateTest {

    RowRenderData header;
    List<RowRenderData> tableDatas;

    @Before
    public void init() {
        // 表头内容与格式
        List<TextRenderData> textRenderDataList = new ArrayList<>();
        textRenderDataList.add(new TextRenderData("FFFFFF", "字段名称"));
        textRenderDataList.add(new TextRenderData("FFFFFF", "字段描述"));
        textRenderDataList.add(new TextRenderData("FFFFFF", "字段类型"));
        textRenderDataList.add(new TextRenderData("FFFFFF", "长度"));
        textRenderDataList.add(new TextRenderData("FFFFFF", "允许为空"));
        textRenderDataList.add(new TextRenderData("FFFFFF", "缺省值"));
        // 设置表头与表头背景颜色
        header = new RowRenderData(textRenderDataList, "5CACEE");
        // 表格内容
        tableDatas = new ArrayList<>();
        tableDatas.add(RowRenderData.build("id", "主键ID", "varchar", "32", "NO", "null"));
        tableDatas.add(RowRenderData.build("user_no", "商户、供应商号", "varchar", "32", "YES", "null"));
        tableDatas.add(RowRenderData.build("user_id", "APP用户唯一标识", "varchar", "32", "YES", "null"));
        tableDatas.add(RowRenderData.build("user_type", "用户类型 1 商户 2 供应商", "tinyint", "null", "YES", "null"));
        tableDatas.add(RowRenderData.build("notice_type", "通知类型 1 系统通知 2 交易通知 3 还款通知", "tinyint", "null", "YES", "null"));
        tableDatas.add(RowRenderData.build("amount", "金额", "decimal", "null", "YES", "null"));

    }

    @SuppressWarnings("serial")
    @Test
    public void testRenderMap() throws Exception {
        // A4
//        MiniTableRenderData miniTableRenderData = new MiniTableRenderData(header, tableDatas, MiniTableRenderData.WIDTH_A4_FULL);

        MiniTableRenderData miniTableRenderData = new MiniTableRenderData(header, tableDatas, 16F);
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("theHeard", "数据库词典文档");
                put("time", LocalDate.now());
                put("tableName", "app_notice");
                put("title", "数据库字典文档");
                put("author", new TextRenderData("000000", "蛮吉"));
                // 表格
                put("table_data", miniTableRenderData);
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/template/table_template.docx").render(datas);

        FileOutputStream out = new FileOutputStream("export-db-dic.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @SuppressWarnings("serial")
    @Test
    public void testRenderObject() throws Exception {
        MyDataModel obj = new MyDataModel();
        obj.setHeader("Deeply love what you love.");
        obj.setName("Poi-tl");
        obj.setWord("模板引擎");
        obj.setTime("2019-05-31");
        obj.setWhat("Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
        obj.setAuthor("Sayi卅一");
        obj.setIntroduce("http://www.deepoove.com");
        obj.setPortrait(new PictureRenderData(60, 60, "src/main/test/resources/sayi.png"));
        // 表格
        obj.setSolutionCompare(new MiniTableRenderData(header, tableDatas));
        // 有序列表
        obj.setFeature(new NumbericRenderData(new ArrayList<TextRenderData>() {
            {
                add(new TextRenderData("Plug-in grammar, add new grammar by yourself"));
                add(new TextRenderData("Supports word text, local pictures, web pictures, table, list, header, footer..."));
                add(new TextRenderData("Templates, not just templates, but also style templates"));
            }
        }));

        XWPFTemplate template = XWPFTemplate.compile("src/main/test/resources/template/table_template.docx").render(obj);

        FileOutputStream out = new FileOutputStream("out_template_object.docx");
        template.write(out);
        template.close();
        out.flush();
        out.close();
    }
}
