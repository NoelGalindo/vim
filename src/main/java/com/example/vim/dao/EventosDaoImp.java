package com.example.vim.dao;

import com.example.vim.models.Eventos;
import com.example.vim.models.Eventos_enviados;
import com.example.vim.models.dto.EventsDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
@RequiredArgsConstructor
public class EventosDaoImp implements EventosDao{
    private final EntityManager entityManager;

    @Override
    public void addEvent(Eventos event) {
        event.setStatus("Creado");
        entityManager.merge(event);
    }

    @Override
    public List getAllEvents(String username) {
        String query = "FROM Eventos WHERE username = :usr";
        return entityManager.createQuery(query).setParameter("usr", username).getResultList();
    }

    @Override
    public void deleteEvent(int id) {
        Eventos event = entityManager.find(Eventos.class, id);
        entityManager.remove(event);
    }

    @Override
    public void senEvent(int id_event) {
        Eventos event = entityManager.find(Eventos.class, id_event);
        LocalDate date = LocalDate.now();
        Eventos_enviados sendEvent = new Eventos_enviados(id_event, event.getUsername(), date);
        entityManager.merge(sendEvent);
        event.setStatus("En revision");
        entityManager.merge(event);
    }

    @Override
    public void addImageCertificate(int id_evento, String imagen_constancia) {
        Eventos event = entityManager.find(Eventos.class, id_evento);
        event.setImg_constancia(imagen_constancia);
        entityManager.merge(event);
    }

    @Override
    public String getCertificate(int id_evento) {
        Eventos event = entityManager.find(Eventos.class, id_evento);
        if(event.getImg_constancia() == null){
            return  "NO CONTENT";
        }
        return  event.getImg_constancia();
    }

    @Override
    public int eventMaxCapacity(int id_evento) {
        Eventos event = entityManager.find(Eventos.class, id_evento);
        return event.getCupo_maximo();
    }

    @Override
    public List getPublishedEvents() {
        String query = "SELECT direccion_url, nombre_evento, informacion_evento, cupo_maximo, imagen_evento FROM Eventos WHERE status='Publicado'";
        return entityManager.createQuery(query).getResultList();
    }

    // Uploadiong images/documents
    @Override
    public String uploadFile(MultipartFile file, String folder){
        Date uploadDate = new Date(System.currentTimeMillis());
        String toName = uploadDate.toString();
        String name = toName.replace(' ', '_');
        // THE PATH WERE THE FILE IS GOING TO BE UPLOADED
        String uploadDirection = "/encurso.vim.com.mx/documents/"+folder+"/"+name+"."+getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        // Connect using FTP server
        String server = "ftp.vim.com.mx";
        int port = 21; // FTP port (default is 21)
        String username = "noe.vim@encurso.vim.com.mx";
        String password = "QWAS12zx_";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(username, password);

            // Set passive mode (recommended for most connections)
            ftpClient.enterLocalPassiveMode();

            // Set binary file type (you may change it to ASCII if needed)
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            boolean success = ftpClient.storeFile(uploadDirection, (InputStream) file.getInputStream());

            if (success) {
                System.out.println("File updated successfully.");

            } else {
                System.out.println("Failed to update file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  "https:/"+uploadDirection;
    }

    // Getting the extension of the file
    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return ""; // No file extension found
        }
    }

}
