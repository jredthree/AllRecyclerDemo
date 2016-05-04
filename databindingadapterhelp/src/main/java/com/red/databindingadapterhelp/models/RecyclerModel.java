package com.red.databindingadapterhelp.models;

/**
 * email: redthree3333@gmail.com
 * author: jredthree
 * time: 2016/5/3
 */
public class RecyclerModel<T> {
    private Integer layoutId;
    private Integer variableId;
    private T data;

    public RecyclerModel(){

    }

    public RecyclerModel(Integer layoutId, Integer variableId, T data) {
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.data = data;
    }

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public Integer getVariableId() {
        return variableId;
    }

    public void setVariableId(Integer variableId) {
        this.variableId = variableId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
