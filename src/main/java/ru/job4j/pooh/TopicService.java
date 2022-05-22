package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {

        String text = "";
        String status = "204";
        String type = req.httpRequestType();
        String name = req.getSourceName();
        String param = req.getParam();

        if ("POST".equals(type)) {
            for (var user : topics.keySet()) {
                var queue = topics.get(user).get(name);
                if (queue != null) {
                    queue.add(param);
                }
            }
        } else if ("GET".equals(type)) {
            topics.putIfAbsent(param, new ConcurrentHashMap<>());
            var tmp = topics.get(param).putIfAbsent(name, new ConcurrentLinkedQueue<>());
            if (tmp != null) {
                text = tmp.poll();
                status = "200";
            }
        } else {
            status = "400";
        }
        return new Resp(text, status);
    }
}
