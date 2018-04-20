package com.pnf.elar.app.Bo;

/**
 * Created by VKrishnasamy on 12-10-2016.
 */

public class ImageVideoBean {

    public String id;
    public String name;
    public String type;

    public String fileExtension;

    public ImageVideoBean(String id, String name, String type, String fileExtension) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public ImageVideoBean(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
