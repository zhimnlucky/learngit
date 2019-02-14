package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModelProperty;

/**app版本Vo
 * Created by lixiaoxiao on 18-3-15.
 */
public class AppVersionVo {

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "构建包")
    private int build;

    @ApiModelProperty(value = "链接地址")
    private String link;

    @ApiModelProperty(value = "md5")
    private String md5;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
