import com.aspose.pdf.Document;
import com.aspose.pdf.PdfFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;
import java.util.Locale;

public class PDFAChecker {

    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.ENGLISH);

            Document pdfDocument = new Document("output/testPDFA.pdf");

            String validationResultPath = "output/validation-result-A1A.xml";
            pdfDocument.validate(validationResultPath, PdfFormat.PDF_A_1A);

            boolean isCompliant = checkPDFValidationResult(validationResultPath);

            if (isCompliant) {
                System.out.println("The PDF is PDF/A-1A compliant.");
            } else {
                System.out.println("The PDF is NOT PDF/A-1A compliant. Check the issues above.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkPDFValidationResult(String xmlFilePath) {
        try {
            // Create a document builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            org.w3c.dom.Document xmlDocument = builder.parse(new File(xmlFilePath));

            // Normalize the XML structure
            xmlDocument.getDocumentElement().normalize();

            // Find any <Problem> elements in the XML, which indicates compliance issues
            NodeList problemNodes = xmlDocument.getElementsByTagName("Problem");

            // If there are no <Problem> nodes, the PDF is compliant
            if (problemNodes.getLength() == 0) {
                return true;
            } else {
                // Display the problems
                System.out.println("Validation Issues Found:");
                for (int i = 0; i < problemNodes.getLength(); i++) {
                    Node problemNode = problemNodes.item(i);
                    if (problemNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element problemElement = (Element) problemNode;

                        // Extract and print the problem details (Severity, Clause, and Description)
                        String severity = problemElement.getAttribute("Severity");
                        String clause = problemElement.getAttribute("Clause");
                        String details = problemElement.getTextContent().trim();

                        System.out.println("Issue " + (i + 1) + ":");
                        System.out.println("  Severity: " + severity);
                        System.out.println("  Clause: " + clause);
                        System.out.println("  Details: " + details);
                        System.out.println();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
