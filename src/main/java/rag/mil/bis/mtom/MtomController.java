package rag.mil.bis.mtom;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rag.mil.bis.events.EventService;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;


@Controller
@WebService(endpointInterface = "rag.mil.bis.mtom.MtomClient")
@RequiredArgsConstructor
@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
@MTOM
public class MtomController implements MtomClient {
    @Autowired
    private EventService eventService;


}
