package cn.tlh.ex.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TANG
 */
@Data
public class Columns implements Serializable {
    private static final long serialVersionUID = 103814573127575128L;

    private Long ordinalPosition;

    private String columnName;

    private String columnType;

    private String columnKey;

    private String extra;

    private String isNullable;

    private String columnComment;

    private String columnDefault;

    private String dataType;

    private Long characterMaximumLength;




}