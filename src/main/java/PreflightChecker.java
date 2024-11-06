import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;

import java.io.File;
import java.io.IOException;

public class PreflightChecker {

    public static boolean isPDFA(File file) {
        PreflightParser parser = null;
        try {
            parser = new PreflightParser(file);
            parser.parse();

            PreflightDocument document = parser.getPreflightDocument();
            document.validate();

            ValidationResult result = document.getResult();
            document.close();

            if (result.isValid()) {
                System.out.println("The file is a valid PDF/A document.");
                return true;
            } else {
                System.out.println("The file is NOT a valid PDF/A document.");
                for (ValidationResult.ValidationError error : result.getErrorsList()) {
                    System.out.println("Error: " + error.getDetails());
                }
                return false;
            }

        } catch (SyntaxValidationException e) {
            System.out.println("The file is NOT a valid PDF. Error: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("Error parsing PDF: " + e.getMessage());
            return false;
        } finally {
            if (parser != null) {
                try {
                    parser.getDocument().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("output/testPDFA.pdf");

        boolean isPDFA = isPDFA(file);
        if (isPDFA) {
            System.out.println("The document is PDF/A compliant.");
        } else {
            System.out.println("The document is NOT PDF/A compliant.");
        }
    }
}
