package rag.mil.bis.exceptions;

import com.itextpdf.text.DocumentException;

public class PdfGenerationException extends BisServerRestException {
    public PdfGenerationException(DocumentException e) {
        super(e.getMessage());
    }
}
