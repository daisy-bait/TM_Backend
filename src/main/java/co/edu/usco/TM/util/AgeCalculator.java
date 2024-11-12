package co.edu.usco.TM.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@NoArgsConstructor
public class AgeCalculator {

    public int calculateMonths(LocalDate birthDate) {

        return Period.between(birthDate, LocalDate.now()).getMonths();
    }

}
