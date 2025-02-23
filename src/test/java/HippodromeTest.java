import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void constructorNullTest() {
        String message = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null)).getMessage();
        assertEquals("Horses cannot be null.", message);
    }

    @Test
    void constructorEmptyTest() {
        String message = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>())).getMessage();
        assertEquals("Horses cannot be empty.", message);
    }

    @Test
    void getHorsesTest() {
        List<Horse> horsesList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horsesList.add(new Horse("name"+i, i+0.1, i+0.2));
        }
        Hippodrome hippodrome = new Hippodrome(horsesList);
        assertEquals(horsesList, hippodrome.getHorses());
    }

    @Test
    void allMoveTest() {
        List<Horse> horsesList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horsesList.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horsesList);
        hippodrome.move();
        for (Horse horse : hippodrome.getHorses()) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinnerTest() {
        List<Horse> horsesList = new ArrayList<>();
        Horse skakun = new Horse("Skakun", 2.0, 3.0);
        horsesList.add(skakun);
        horsesList.add(new Horse ("Dohodiaga", 1.0, 1.2));
        Hippodrome hippodrome = new Hippodrome(horsesList);
        assertEquals(skakun, hippodrome.getWinner());
    }
}