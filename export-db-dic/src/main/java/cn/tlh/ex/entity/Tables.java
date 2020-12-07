package cn.tlh.ex.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@ToString
public class Tables implements Serializable {
    private static final long serialVersionUID = -95389627276769266L;

    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String tableType;

    private String engine;

    private Long version;

    private String rowFormat;

    private Long tableRows;

    private Long avgRowLength;

    private Long dataLength;

    private Long maxDataLength;

    private Long indexLength;

    private Long dataFree;

    private Long autoIncrement;

    private LocalDate createTime;

    private LocalDate updateTime;

    private LocalDate checkTime;

    private String tableCollation;

    private Long checksum;

    private String createOptions;

    private String tableComment;

}