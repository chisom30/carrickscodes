package io.getarrays.server;

import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static io.getarrays.server.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
          //  serverRepository.save(new Server(-1, "192.168.1.160", "Ubuntu Linux", "16 GB", "Personal PC",
           //         "http://localhost:8080/server/image/server1.png", SERVER_UP));
           // serverRepository.save(new Server(-2, "192.170.2.168", "Mac Pro", "32 GB", "Web Server",
                   // "http://localhost:8080/server/image/server2.png", SERVER_UP));
           // serverRepository.save(new Server(-3, "192.181.1.178", "Lenovo Thinkpad", "64 GB", "Dell Tower",
                   // "http://localhost:8080/server/image/server3.png", SERVER_UP));
           // serverRepository.save(new Server(-4, "192.168.1.160", "HP Pavilion", "128 GB", "Mail Server",
                   // "http://localhost:8080/server/image/server4.png", SERVER_UP));
        };
    }
}
