package cn.tlh.ex.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

import static cn.tlh.ex.controller.ExportController.*;

@ToString
@ApiModel("请求实体")
public class DbVO {
    @ApiModelProperty(value = "数据库名称", required = true)
    @NotBlank(message = "数据库名称不能为空")
    private String dbName;

    @ApiModelProperty("文档名称")
    private String title;

    @ApiModelProperty("数据库类型 (例如:MySQL、Oracle),目前仅支持MySQL")
    private String dbType;

    @ApiModelProperty("当前项目版本 (例如:1.0、2.0)")
    private String projectVersion = PROJECT_VERSION;

    @ApiModelProperty("项目名称")
    private String projectName = PROJECT_NAME;

    @ApiModelProperty("修改人")
    private String updateBy;

    public String getDbName() {
        if (dbName == null || "".equals(dbName)) {
            return DB_NAME;
        }
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTitle() {
        if (title == null || "".equals(title)) {
            return dbName + "数据库说明文档";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDbType() {
        if (dbType == null || "".equals(dbType)) {
            return DB_TYPE;
        }
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getProjectVersion() {
        if (projectVersion == null || "".equals(projectVersion)) {
            return PROJECT_VERSION;
        }
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getProjectName() {
        if (projectName == null || "".equals(projectName)) {
            return PROJECT_NAME;
        }
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUpdateBy() {
        if (updateBy == null || "".equals(updateBy)) {
            return UPDATE_BY;
        }
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}