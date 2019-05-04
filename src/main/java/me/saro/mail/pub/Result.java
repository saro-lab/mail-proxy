package me.saro.mail.pub;

import lombok.Data;

@Data
public class Result<T> {
    Code code;
    String msg;
    T data;

    public Result(Code code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Code code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public Result(Code code) {
        this.code = code;
        this.msg = null;
        this.data = null;
    }
}
