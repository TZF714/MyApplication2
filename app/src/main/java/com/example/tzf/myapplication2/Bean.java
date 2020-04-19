package com.example.tzf.myapplication2;

import java.util.List;

/**
 * Created by TZF on 2020/4/17.
 */



public class Bean {
    private int code;
    private List<Data> data;
    private String msg;

    public Bean(int code, List<Data> data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class Data {
        private int id;
        private String efficacy;
        private String goods_img;

        public Data(int id, String efficacy, String goods_img) {
            this.id = id;
            this.efficacy = efficacy;
            this.goods_img = goods_img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEfficacy() {
            return efficacy;
        }

        public void setEfficacy(String efficacy) {
            this.efficacy = efficacy;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", efficacy='" + efficacy + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    '}';
        }
    }
}



