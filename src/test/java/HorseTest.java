import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseTest {

    @Test
    void firstParameterNullTest() {
        assertEquals("Name cannot be null.", assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1)).getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "   "})
    void firstParameterBlankNameTest(String blankName) {
        assertEquals("Name cannot be blank.", assertThrows(IllegalArgumentException.class, () -> new Horse(blankName, 1, 1)).getMessage());
    }

    @Test
    void secondParameterIllegalTest() {
        assertEquals("Speed cannot be negative.", assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 1)).getMessage());
    }

    @Test
    void thirdParameterIllegalTest() {
        assertEquals("Distance cannot be negative.", assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1, -1)).getMessage());
    }

    @Test
    void getNameTest() {
        assertEquals("name", new Horse("name", 1, 1).getName());
    }

    @Test
    void getSpeedTest() {
        assertEquals(1, new Horse("name", 1, 1).getSpeed());
    }

    @Test
    void getDistanceTest() {
        assertEquals(1, new Horse("name", 1, 1).getDistance());
        assertEquals(0, new Horse("name", 1).getDistance());
    }

    @Test
    void moveCallGetRandomDoubleTest() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("name", 1, 1).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }

    }

    @ParameterizedTest
    @CsvSource({"0.2, 0.5, 0.4",
            "0.3, 0.7, 0.6"})
    void moveFormulaTest(double distance, double speed, double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse testHorse = new Horse("name", speed, distance);
            testHorse.move();
            double testHorseDistance = testHorse.getDistance();
            double expected = distance + speed * random;
            assertEquals(expected, testHorseDistance);

        }
    }



}