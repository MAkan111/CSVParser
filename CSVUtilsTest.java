import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;

public class CSVUtilsTest {

    @Test
    public void testExternalSort() throws IOException {
        // Создание временного файла для входных данных
        File inputFile = createInputFile();

        // Создание временного файла для выходных данных
        File outputFile = File.createTempFile("output", ".csv");

        // Выполнение внешней сортировки
        CSVUtils.externalSort(inputFile, outputFile, 3);

        // Проверка, что файл outputFile существует
        assertTrue(outputFile.exists());

        // Проверка, что outputFile содержит те же строки, что и inputFile, но в отсортированном порядке
        try (BufferedReader brInput = new BufferedReader(new FileReader(inputFile));
             BufferedReader brOutput = new BufferedReader(new FileReader(outputFile))) {
            String lineInput;
            String lineOutput;
            while ((lineInput = brInput.readLine()) != null && (lineOutput = brOutput.readLine()) != null) {
                assertEquals(lineInput, lineOutput);
            }
            assertNull(brInput.readLine()); // Проверка, что inputFile не содержит дополнительных строк
            assertNull(brOutput.readLine()); // Проверка, что outputFile не содержит дополнительных строк
        }
    }

    // Вспомогательный метод для создания временного файла с тестовыми данными
    private File createInputFile() throws IOException {
        File inputFile = File.createTempFile("input", ".csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile))) {
            bw.write("3,a\n");
            bw.write("2,b\n");
            bw.write("1,c\n");
        }
        return inputFile;
    }
}