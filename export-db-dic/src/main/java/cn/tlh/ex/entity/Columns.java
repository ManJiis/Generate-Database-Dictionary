package cn.tlh.ex.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TANG
 */
@Data
public class Columns implements Serializable {
    private static final long serialVersionUID = 103814573127575128L;

    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String columnName;

    private Long ordinalPosition;

    private String columnDefault;

    private String isNullable;

    private String dataType;

    private Long characterMaximumLength;

    private Long characterOctetLength;

    private Long numericPrecision;

    private Long numericScale;

    private Long datetimePrecision;

    private String characterSetName;

    private String collationName;

    private String columnType;

    private String columnKey;

    private String extra;

    private String privileges;

    private String columnComment;

    private String generationExpression;



}