package com.pnf.elar.app.adapter;

/**
 * Created by VKrishnasamy on 09-09-2016.
 */
public class SearchSchoolBo {

    public String name;
    public String domain_name;
    public String domain_header;
    public long id;

    public SearchSchoolBo(long id,String name, String domain_name,String domain_header) {
        this.id=id;
        this.name = name;
        this.domain_name = domain_name;
        this.domain_header=domain_header;
    }

    public SearchSchoolBo() {
    }

    public String getDomain_header() {
        return domain_header;
    }

    public void setDomain_header(String domain_header) {
        this.domain_header = domain_header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}