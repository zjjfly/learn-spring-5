package com.github.zjjfly.spring5;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/19
 */
class ReactorTest {

    @Test
    void mono() {
        //Mono是Publisher的一个实现类,它特定用于已知的返回项不会多于一个的响应式类型
        Mono.just("jjzi")
            .map(String::toUpperCase)
            .map(s -> "Hello," + s + "!")
            .subscribe(System.out::println);
    }

    @Test
    void createFlux1() {
        //Publisher的另一个实现类Flux表示零个、一个或多个（可能是无限个）数据项的管道
        Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry")
            .subscribe(f -> System.out.println("Here's some fruit: " + f));
        //相比于打印,更好的验证Flux和Mono的方法是使用StepVerifier
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orange")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }

    @Test
    void createFlux2() {
        //通过数组初始化Flux
        String[] fruits = new String[]{
                "Apple", "Orange", "Grape", "Banana", "Strawberry"};
        Flux<String> fruitFlux = Flux.fromArray(fruits);
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orange")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }

    @Test
    void createFlux3() {
        //通过列表初始化Flux
        ArrayList<String> fruits = Lists.newArrayList("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFlux = Flux.fromIterable(fruits);
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orange")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }

    @Test
    void createFlux4() {
        //通过Java Stream初始化Flux
        Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFlux = Flux.fromStream(fruitStream);
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orange")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }

    @Test
    void createFlux5() {
        //通过Flux的range方法初始化一个Flux,可以作为计数器
        Flux<Integer> intervalFlux = Flux.range(1, 5);
        StepVerifier.create(intervalFlux)
                    .expectNext(1)
                    .expectNext(2)
                    .expectNext(3)
                    .expectNext(4)
                    .expectNext(5)
                    .verifyComplete();
    }

    @Test
    void createFlux6() {
        //通过Flux的interval方法初始化一个发出递增值的Flux,和range方法不同的时
        //它不需要指定起始值和结束值,而是指定一个持续时间或一个值的发出频率
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1L)).take(5);
        StepVerifier.create(intervalFlux)
                    .expectNext(0L)
                    .expectNext(1L)
                    .expectNext(2L)
                    .expectNext(3L)
                    .expectNext(4L)
                    .verifyComplete();
    }

    @Test
    void mergeFlux() {
        //多个Flux可以通过mergeWith合并,合并后的Flux发出的数据的顺序,与源发出的数据的时间顺序一致
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));
        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
        StepVerifier.create(mergedFlux)
                    .expectNext("Garfield")
                    .expectNext("Lasagna")
                    .expectNext("Kojak")
                    .expectNext("Lollipops")
                    .expectNext("Barbossa")
                    .expectNext("Apples")
                    .verifyComplete();
    }

    @Test
    void zipFlux1() {
        //如果想让两个Flux:f1和f2合并之后的Flux的第一个消息由f1的第一个元素和f2的第一个元素组成
        //第二个消息由f1的第二个元素和f2的第二个元素组成...以此类推,则可以使用zip函数
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");
        Flux<Tuple2<String, String>> mergedFlux = Flux.zip(characterFlux, foodFlux);
        StepVerifier.create(mergedFlux)
                    .expectNextMatches(p -> p.getT1().equals("Garfield") &&
                            p.getT2().equals("Lasagna"))
                    .expectNextMatches(p -> p.getT1().equals("Kojak") &&
                            p.getT2().equals("Lollipops"))
                    .expectNextMatches(p -> p.getT1().equals("Barbossa") &&
                            p.getT2().equals("Apples"))
                    .verifyComplete();
    }

    @Test
    void zipFlux2() {
        //zip默认产生的是一个Tuple2的Flux,也可以使用一个BiFunction指定两个元素的组合方式
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");
        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux,
                                           (c, f) -> c + " eats " + f);
        StepVerifier.create(zippedFlux)
                    .expectNext("Garfield eats Lasagna")
                    .expectNext("Kojak eats Lollipops")
                    .expectNext("Barbossa eats Apples")
                    .verifyComplete();
    }

    @Test
    void firstFlux() {
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                                    .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
        //如果希望只从多个Flux中的最先有数据的Flux中取数据,使用firstWithSignal,如果是最早有信号的Flux,则使用firstWithSignal
        Flux<String> firstFlux = Flux.firstWithValue(slowFlux, fastFlux);
        StepVerifier.create(firstFlux)
                    .expectNext("hare")
                    .expectNext("cheetah")
                    .expectNext("squirrel")
                    .verifyComplete();
    }

    @Test
    void skipSomeElements() {
        Flux<String> skipFlux = Flux.just(
                "one", "two", "skip a few", "ninety nine", "one hundred")
                                    .skip(3);
        StepVerifier.create(skipFlux)
                    .expectNext("ninety nine", "one hundred")
                    .verifyComplete();
    }

    @Test
    void skipDuration() {
        Flux<String> skipFlux = Flux.just(
                "one", "two", "skip a few", "ninety nine", "one hundred")
                                    .delayElements(Duration.ofSeconds(1))
                                    .skip(Duration.ofSeconds(4));
        StepVerifier.create(skipFlux)
                    .expectNext("ninety nine", "one hundred")
                    .verifyComplete();
    }

    @Test
    void takeSomeElements() {
        Flux<String> nationalParkFlux = Flux.just(
                "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                                            .take(3);
        StepVerifier.create(nationalParkFlux)
                    .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                    .verifyComplete();
    }

    @Test
    void takeDuration() {
        Flux<String> nationalParkFlux = Flux.just(
                "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                                            .delayElements(Duration.ofSeconds(1))
                                            .take(Duration.ofMillis(3500));
        StepVerifier.create(nationalParkFlux)
                    .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                    .verifyComplete();
    }

    @Test
    void filter() {
        Flux<String> nationalParkFlux = Flux.just(
                "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                                            .filter(np -> !np.contains(" "));
        StepVerifier.create(nationalParkFlux)
                    .expectNext("Yellowstone", "Yosemite", "Zion")
                    .verifyComplete();
    }

    @Test
    void distinct() {
        Flux<String> animalFlux = Flux.just(
                "dog", "cat", "bird", "dog", "bird", "anteater")
                                      .distinct();
        StepVerifier.create(animalFlux)
                    .expectNext("dog", "cat", "bird", "anteater")
                    .verifyComplete();
    }

    @Data
    @AllArgsConstructor
    static class Player {
        private String firstName;
        private String lastName;
    }

    @Test
    void map() {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(n -> {
                    String[] split = n.split("\\s");
                    return new Player(split[0], split[1]);
                });
        StepVerifier.create(playerFlux)
                    .expectNext(new Player("Michael", "Jordan"))
                    .expectNext(new Player("Scottie", "Pippen"))
                    .expectNext(new Player("Steve", "Kerr"))
                    .verifyComplete();
    }

    @Test
    void flatMap() {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n).map(p -> {
                             String[] split = p.split("\\s");
                             return new Player(split[0], split[1]);
                         })
                                  //subscribeOn可以设置应该如何并发地处理订阅,这里的Schedulers.parallel()是要的是一个固定大小的线程池来并行执行
                                  .subscribeOn(Schedulers.parallel())
                        );

        //由于并行执行无法保证执行完成的先后顺序,所以使用contains去验证
        List<Player> playerList = Arrays.asList(
                new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr"));
        StepVerifier.create(playerFlux)
                    .expectNextMatches(playerList::contains)
                    .expectNextMatches(playerList::contains)
                    .expectNextMatches(playerList::contains)
                    .verifyComplete();
    }

    @Test
    void buffer() {
        //有的时候想要把收到的消息先放到一个缓冲中,当数量达到一定值之后再一起处理,可以使用buffer方法
        Flux<String> fruitFlux = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");
        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
        StepVerifier
                .create(bufferedFlux)
                .expectNext(Arrays.asList("apple", "orange", "banana"))
                .expectNext(Arrays.asList("kiwi", "strawberry"))
                .verifyComplete();
        //把响应式缓冲转到非响应式List集合看着效率很低,但如果把buffer和flatMap结合起来,可以并行处理每个 List 集合
        Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
            .buffer(3)
            .flatMap(x -> Flux.fromIterable(x)
                              .map(String::toUpperCase)
                              .subscribeOn(Schedulers.parallel())
                              .log()
                    ).subscribe();
    }

    @Test
    void collectList() {
        //collectList类似Stream的collect(Collectors.toList()),但它返回的是一个Mono而不是Flux
        Flux<String> fruitFlux = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");
        Mono<List<String>> fruitListMono = fruitFlux.collectList();
        StepVerifier
                .create(fruitListMono)
                .expectNext(Arrays.asList(
                        "apple", "orange", "banana", "kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    void collectMap() {
        //collectList类似Stream的collect(Collectors.toMap()),但它返回的是一个Mono而不是Flux
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Map<Character, String>> animalMapMono =
                animalFlux.collectMap(a -> a.charAt(0));
        StepVerifier
                .create(animalMapMono)
                .expectNextMatches(map -> map.size() == 3 &&
                        map.get('a').equals("aardvark") &&
                        map.get('e').equals("eagle") &&
                        map.get('k').equals("kangaroo"))
                .verifyComplete();
    }

    @Test
    void all() {
        //all类似Stream的allMatch,返回的是Mono<Boolean>
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
        StepVerifier.create(hasAMono)
                    .expectNext(true)
                    .verifyComplete();

        Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));
        StepVerifier.create(hasKMono)
                    .expectNext(false)
                    .verifyComplete();
    }

    @Test
    void any() {
        //any类似Stream的anyMatch,返回的是Mono<Boolean>
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasAMono = animalFlux.any(a -> a.contains("t"));
        StepVerifier.create(hasAMono)
                    .expectNext(true)
                    .verifyComplete();

        Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));
        StepVerifier.create(hasZMono)
                    .expectNext(false)
                    .verifyComplete();
    }
}
