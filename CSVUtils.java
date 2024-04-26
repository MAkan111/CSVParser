import java.io.*;
import java.util.*;

public class CSVUtils {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("input.csv");
        File outputFile = new File("output.csv");
        int bufferSize = 10000; // Размер буфера для сортировки в оперативной памяти
        externalSort(inputFile, outputFile, bufferSize);
    }

    public static void externalSort(File inputFile, File outputFile, int bufferSize) throws IOException {

        List<File> sortedTempFiles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
                if (lines.size() >= bufferSize) {
                    sortedTempFiles.add(sortAndSaveTempFile(lines));
                    lines.clear();
                }
            }
            if (!lines.isEmpty()) {
                sortedTempFiles.add(sortAndSaveTempFile(lines));
            }
        }

        mergeSortedFiles(sortedTempFiles, outputFile);

        for (File tempFile : sortedTempFiles) {
            tempFile.delete();
        }

    }

    private static File sortAndSaveTempFile(List<String> lines) throws IOException {

        Collections.sort(lines, Comparator.comparingInt(line -> Integer.parseInt(line.split(",")[0])));

        File tempFile = File.createTempFile("temp", null);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }

        return tempFile;
    }


    public static void mergeSortedFiles(List<File> sortedTempFiles, File outputFile) throws IOException {

        PriorityQueue<BufferedReader> pq = new PriorityQueue<>(Comparator.comparing(br -> {
            try {
                return Integer.parseInt(br.readLine().split(",")[0]);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }));

        for (File tempFile : sortedTempFiles) {
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            pq.add(br);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            while (!pq.isEmpty()) {
                BufferedReader br = pq.poll();
                String line = br.readLine();
                if (line != null) {
                    bw.write(line);
                    bw.newLine();
                    pq.add(br);
                }
            }
        }

    }
}