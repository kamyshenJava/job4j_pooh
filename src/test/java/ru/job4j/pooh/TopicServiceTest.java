package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TopicServiceTest {

    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result1.status(), is("200"));
        assertThat(result2.text(), is(""));
        assertThat(result2.status(), is("204"));
    }

    @Test
    public void whenTwoSubscribeOneDoesnt() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        String paramForSubscriber3 = "client6";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber3)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Resp result3 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber3)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result1.status(), is("200"));
        assertThat(result2.text(), is(""));
        assertThat(result2.status(), is("204"));
        assertThat(result3.text(), is("temperature=18"));
        assertThat(result3.status(), is("200"));
    }

    @Test
    public void whenBadRequest() {
        TopicService topicService = new TopicService();
        String paramForSubscriber1 = "client407";
        Resp result1 = topicService.process(
                new Req("GETT", "topic", "weather", paramForSubscriber1)
        );
        assertThat(result1.text(), is(""));
        assertThat(result1.status(), is("400"));
    }
}