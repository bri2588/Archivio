import com.aspose.pdf.ConvertErrorAction;
import com.aspose.pdf.Document;
import com.aspose.pdf.PdfFormat;
import com.spire.pdf.conversion.PdfStandardsConverter;

import java.util.Locale;

public class Converter {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        Document pdfDocument = new Document("output/test.pdf");
        pdfDocument.convert("output/conversion-log.xml", PdfFormat.PDF_A_1A, ConvertErrorAction.None);
        pdfDocument.save("output/testPDFA.pdf");
        System.out.println("PDF/A compliance issue fixed and document saved.");
    }


}
