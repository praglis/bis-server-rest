package rag.mil.bis.mtom;

import com.itextpdf.text.DocumentException;

import javax.jws.WebService;
import java.rmi.Remote;

@WebService
public interface MtomClient extends Remote {
    byte[] generatePdf() throws DocumentException;
}

