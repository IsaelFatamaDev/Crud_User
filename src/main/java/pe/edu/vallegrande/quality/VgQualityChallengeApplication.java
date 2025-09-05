
package pe.edu.vallegrande.quality;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class VgQualityChallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(VgQualityChallengeApplication.class, args);
        log.info("Aplicación iniciada correctamente - VG Quality Challenge");
    }
}
