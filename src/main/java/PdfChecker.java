import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;

public class PdfChecker {
    public static boolean isPDF(File file) {
        Tika tika = new Tika();
        String mimeType;

        try {
            mimeType = tika.detect(file);
            System.out.println("Detected MIME type: " + mimeType);
            return mimeType.equals("application/pdf");
        } catch (IOException e) {
            System.err.println("Error detecting file type: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        File file = new File("src/test/resources/example.pdf");
        if (isPDF(file)) {
            System.out.println("The file is a PDF.");
        } else {
            System.out.println("The file is not a PDF.");
        }
    }
}
