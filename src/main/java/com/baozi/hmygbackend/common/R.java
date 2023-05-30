package com.baozi.hmygbackend.common;

public class R<T> {
    public T message;
    public Meta meta;

    public static class Meta {
        public String msg;
        public int status;

        public Meta() {
            msg = "";
            status = 0;
        }

        public Meta(String msg, int status) {
            this.msg = msg;
            this.status = status;
        }
    }

    public R() {
        message = null;
        meta = new Meta();
    }

    public static <T>R<T> success(T message, String msg) {
        R<T> r = new R<>();
        r.message = message;
        r.meta = new Meta(msg, 200);
        return r;
    }

    public static <T>R<T> success(T message) {
        R<T> r = new R<>();
        r.message = message;
        r.meta = new Meta("", 200);
        return r;
    }

    public static <T>R<T> error(String msg) {
        R<T> r = new R<>();
        r.message = null;
        r.meta = new Meta(msg, 400);
        return r;
    }

}
