package cn.tlh.ex.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class Tables implements Serializable {
    private static final long serialVersionUID = -95389627276769266L;


    private String tableName;

    private String tableType;

    private String engine;

    private String tableCollation;

    private String createOptions;

    private String tableComment;

}