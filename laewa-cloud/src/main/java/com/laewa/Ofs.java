package com.laewa;

public class Ofs {
    String str(String data) {
        return JsonParser.parse(data).get("str").toString();
    }
}
