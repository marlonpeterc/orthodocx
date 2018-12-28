package nz.co.orthodocx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@SpringBootApplication
public class OrthodocxApplication {

    public static void main(String[] args) {
        wordCount();
        SpringApplication.run(OrthodocxApplication.class, args);
    }

    private static void wordCount() {
        String text = "The quick brown fox jumps over the lazy dog";
        final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
        Stream<String> stream = Stream.of(pattern.split(text.toLowerCase()));
        Map<String, Long> collect = stream.collect(groupingBy(word -> word, counting()));
        System.out.println(collect);
    }

}
