Этот проект — аналог асинхронной очереди.
Приложение запускает Socket и ждет клиентов.
Клиенты могут быть двух типов: отправители (publisher), получатели (subscriber).
В качестве клиента будем использовать cURL. https://curl.se/download.html
В качестве протокола будем использовать HTTP.
Pooh будет иметь два режима: queue, topic.
Queue.
Отправитель посылает запрос на добавление данных с указанием очереди (weather) и значением параметра (temperature=18). Сообщение помещается в конец очереди. Если очереди нет в сервисе, то нужно создать новую и поместить в нее сообщение.
Получатель посылает запрос на получение данных с указанием очереди. Сообщение забирается из начала очереди и удаляется.
Если в очередь приходят несколько получателей, то они поочередно получают сообщения из очереди.
Каждое сообщение в очереди может быть получено только одним получателем.
Topic.
Отправитель посылает запрос на добавление данных с указанием топика (weather) и значением параметра (temperature=18). Сообщение помещается в конец каждой индивидуальной очереди получателей. Если топика нет в сервисе, то данные игнорируются.
Получатель посылает запрос на получение данных с указанием топика. Если топик отсутствует, то создается новый. А если топик присутствует, то сообщение забирается из начала индивидуальной очереди получателя и удаляется.
Когда получатель впервые получает данные из топика – для него создается индивидуальная пустая очередь. Все последующие сообщения от отправителей с данными для этого топика помещаются в эту очередь тоже.
Таким образом в режиме "topic" для каждого потребителя своя будет уникальная очередь с данными, в отличие от режима "queue", где для все потребители получают данные из одной и той же очереди.

