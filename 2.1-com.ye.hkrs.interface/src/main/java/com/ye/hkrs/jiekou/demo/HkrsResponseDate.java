package com.ye.hkrs.jiekou.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zjx on 2017/12/26.
 */
@NoArgsConstructor
@Data
public class HkrsResponseDate {
    public String data;
    public String sign;
    public String time;

    private void init(){
        this.data = "W3sidXNlcl9pZCI6IiIsInBvbF9ubyI6Ijg2MTEwMDIwMTYwMjExMDUyMDAyIiwicHJvZF9pZCI6\n" +
                "IjQxMTUwMSIsInByb2RfbmFtZSI6IuW8mOW6t+i0neWBpeW6t+WwkeWEv+mHjeWkp+eWvueXheS/\n" +
                "nemZqSIsImNvbXBfbmFtZSI6IuW8mOW6t+S6uuWvv+iCoeS7veaciemZkOWFrOWPuCIsInRvdWJh\n" +
                "b3JlbiI6IuWImOafkOafkCIsInRlbCI6IjEyMTQxMzIzOTg2IiwidG91YmFvcmVuX2lkX3R5cGUi\n" +
                "OiIwIiwidG91YmFvcmVuX2lkIjoiMTEwMTAxMTk4MDAxMDEwMDEwIiwiYWRkciI6IiIsImluc3Vy\n" +
                "ZWQiOiLliJjmn5AiLCJwcmVtIjo3MC4wLCJmYWNlX2Ftb3VudCI6MTAwMDAwLjAsInByZW1fZGF0\n" +
                "ZSI6IjIwMTYtNy0yNiAyMToyMjowNiIsInByZW1fdHlwZSI6MCwicHJlbV9wZXJpb2QiOjEsImJl\n" +
                "bmVmaXRfcGVyaW9kX3R5cGUiOjEsImJlbmVmaXRfcGVyaW9kIjoxLCJhZGRfdGltZSI6IjIwMTYt\n" +
                "OC0xNSAxNzowMjo0MSIsInZhbGlfZGF0ZSI6IjIwMTYtMDctMjciLCJzdGF0dXMiOjEsImFkdmlj\n" +
                "ZV9jb2RlIjoieWdiIiwicG9sX3VybCI6Imh0dHA6Ly93d3cuaG9uZ2thbmctbGlmZS5jb20vZmls\n" +
                "ZVVwbG9hZC9kb3duTG9hZENvbnROby5kbz9jb250Tm9cdTAwM2QyQ0FBMDM3MTc0RDVBNzA4NDQw\n" +
                "RkNFQTQ4MEJEOENEMEFCQzIwMUFCOTQwQkU2ODkiLCJjYXJkTm8iOiJISzIwMTYwMDEwNTIxIn1d";
        this.sign = "M2MwZjIwNzIxNDcyMTc5NTAwMDEy";
        this.time = "1472179500012";
    }

}
