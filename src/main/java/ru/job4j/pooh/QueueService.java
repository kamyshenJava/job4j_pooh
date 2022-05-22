package ru.job4j.pooh;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "200";
        String type = req.httpRequestType();
        String name = req.getSourceName();
        if ("POST".equals(type)) {
            map.putIfAbsent(name, new ConcurrentLinkedQueue<>());
            map.get(name).add(req.getParam());
        } else if ("GET".equals(type)) {
            text = map.get(name).poll();
        } else {
            text = "";
            status = "400";
        }
        if (Objects.equals(text, null)) {
            text = "";
            status = "204";
        }
        return new Resp(text, status);
    }
}
