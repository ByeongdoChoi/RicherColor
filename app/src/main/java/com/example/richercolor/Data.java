package com.example.richercolor;

public class Data {
    String[] datas = new String[10];

    private static Data data;
    int idx = 0;
    private Data() {

    }

    public static Data getData() {
        if (data == null) {
            data = new Data();
        }

        return data;
    }

    public void setData(String data) {
        datas[idx++] = data;
    }

    public void setData(String data, int idx) {
        datas[idx] = data;
        this.idx = idx;
    }

    public String getData(int idx) {
        return datas[idx];
    }

}
