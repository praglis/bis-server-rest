//package rag.mil.bis.mtom;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import rag.mil.bis.events.EventService;
//
//import javax.jws.WebService;
//import javax.xml.ws.BindingType;
//import javax.xml.ws.soap.MTOM;
//import javax.xml.ws.soap.SOAPBinding;
//import java.io.File;
//
//
//@Controller
//@WebService(endpointInterface = "rag.mil.bis.mtom.MtomClient")
//@RequiredArgsConstructor
//@MTOM
//@BindingType(value = SOAPBinding.SOAP12HTTP_MTOM_BINDING)
//public class MtomController {
//    @Autowired
//    private EventService eventService;
//
//    @Override//todo obrazki w eventDto
//    public File downloadImageForEvent(long id) {
//        return eventService.getImage(id);
//    }
//
//    @Override
//    public void uploadImageForEvent(File image, long id) {
//        eventService.postImage(image, id);
//    }
//}
