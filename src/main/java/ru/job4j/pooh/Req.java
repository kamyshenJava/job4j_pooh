package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] temp = content.split("/", 5);
        String httpRequestType = temp[0].trim();
        String poohMode = temp[1];
        String sourceName = temp[2].split(" ")[0];
        String param = "";
        if ("POST".equals(httpRequestType)) {
            String[] tmp = content.split(System.lineSeparator());
            param = tmp[tmp.length - 1];
        } else if ("GET".equals(httpRequestType)) {
            if ("queue".equals(poohMode)) {
                param = "";
            } else {
                param = temp[3].split(" ")[0];
            }
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
