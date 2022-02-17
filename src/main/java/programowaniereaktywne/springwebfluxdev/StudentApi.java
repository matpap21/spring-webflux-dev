package programowaniereaktywne.springwebfluxdev;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;

@RestController
public class StudentApi {

    private Flux<Student> studentFlux;

    public StudentApi() {

        studentFlux = Flux.just(

                new Student("Marian", "Nowak"),
                new Student("Jarosław", "Kowalski"),
                new Student("Szymon", "Szymanski"),
                new Student("Aneta", "Aneciak")
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> getStudent() {
        return studentFlux.delayElements(Duration.ofSeconds(4));
    }

    @PostMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> getStudent(@RequestBody Student student) {
        this.studentFlux = this.studentFlux.mergeWith(Mono.just(student));
        return studentFlux;
    }
}
