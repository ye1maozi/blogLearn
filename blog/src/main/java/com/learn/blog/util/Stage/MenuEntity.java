package com.learn.blog.util.Stage;

public class MenuEntity extends BaseEntity {

    private String name;
    private String path;
    private Integer id;
    private Integer parentId;
    private String component;

    private String nickname;
    private String icon;

    private MetaEntity meta;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public MetaEntity getMeta() {
        return meta;
    }

    public void setMeta(MetaEntity meta) {
        this.meta = meta;
    }
}
