package com.Util.Model.Model;

import java.util.List;

/**
 * Created by user on 02-04-2018.
 */

public class APIListResponse<T> {

    private String status;
    private List<T> term_list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<T> getResult() {
        return term_list;
    }

    public void setResult(List<T> result)   {
        this.term_list = result;
    }
}
