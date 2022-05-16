package rag.mil.bis.mtom;

import javax.jws.WebService;
import java.awt.*;
import java.io.File;
import java.rmi.Remote;

@WebService
public interface MtomClient extends Remote {

    File downloadImageForEvent(long id);

    void uploadImageForEvent(File image, long id);
}

