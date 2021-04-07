package com.example.myeverytime;

public class CMRespDto<T> {
    private int code;
    private T data;

    public CMRespDto(int code, T data) {
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return "CMRespDto{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public CMRespDto() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
