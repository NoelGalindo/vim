package com.example.vim.controllers;

import com.example.vim.auth.AuthService;
import com.example.vim.auth.AuthUserInfo;
import com.example.vim.dao.EventosDao;
import com.example.vim.models.Eventos;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.InternationalFormatter;
import java.util.List;

@RestController
@RequestMapping("/eventos/")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class EventosController {

    private final AuthService authService;
    private final EventosDao eventosDao;

    /* CREATE THE EVENT */
    @PostMapping("api/addEvent")
    public ResponseEntity<String> createEvent(@RequestParam("username") String username,
                                              @RequestParam("direccion_url") String direccion_url,
                                              @RequestParam("nombre_evento") String nombre_evento,
                                              @RequestParam("informacion_evento") String informacion_evento,
                                              @RequestParam("cupo_maximo") int cupo_maximo,
                                              @RequestParam("banner") MultipartFile file) {
        // SEND THE IMAGE TO BE SAVE IN THE DOMAIN
        String direccionBanner = eventosDao.uploadFile(file, "banners");
        // CREATE THE EVENT TO BE SAVED IN THE DB
        Eventos evento = Eventos.builder().build();
        evento.setUsername(username);
        evento.setDireccion_url(direccion_url);
        evento.setNombre_evento(nombre_evento);
        evento.setInformacion_evento(informacion_evento);
        evento.setCupo_maximo(cupo_maximo);
        evento.setImagen_evento(direccionBanner);
        // SENDING THE EVENT TO BE SAVED IN THE DB
        eventosDao.addEvent(evento);
        return new ResponseEntity<>("Creado correctamente", HttpStatusCode.valueOf(200));
    }

    /* GET THE LIST OF ALL THE EVENTS */
    @PostMapping(value = "api/getEvents")
    public ResponseEntity<List<Eventos>> getAllEvents(@RequestBody String token) {
        String username = authService.usuarioUsername(token);
        List<Eventos> events = eventosDao.getAllEvents(username);
        return new ResponseEntity<>(events, HttpStatusCode.valueOf(200));
    }

    /* DELETE THE EVENT */
    @DeleteMapping("api/deleteEvent/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id) {
        eventosDao.deleteEvent(id);
        return new ResponseEntity<>("Eliminado correctament", HttpStatusCode.valueOf(200));
    }

    /* ADD CERTIFICATE IMAGE TO THE EVENT TABLE */
    @PutMapping("api/certificateUrl")
    public ResponseEntity<String> certificateUrl(@RequestParam("id_evento") int id_evento,
                                                 @RequestParam("imagen_constancia") MultipartFile imagen_constancia) {
        // SAVING THE IMAGE IN THE DOMAIN INTO THE CARPER "constancias"
        String direccion_constancia = eventosDao.uploadFile(imagen_constancia, "constancias");
        // INSERTING THE URL OF THE CERTIFICATE INTO THE EVENT ROW
        eventosDao.addImageCertificate(id_evento, direccion_constancia);
        return new ResponseEntity<>("Constancia actualizado correctamente", HttpStatusCode.valueOf(200));
    }

    /* CHECK IF THE EVENT HAS A CERTIFICATE IMAGE */
    @PostMapping("api/getCertificate")
    public ResponseEntity<String> getCertificate(@RequestBody int id_evento){
        String response = eventosDao.getCertificate(id_evento);
        return  new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    /* SEND(CHANGE THE STATUS OF THE EVENT AND SEND IT TO EVENTOS_ENVIADOS)*/
    @PutMapping("api/sendEvent")
    public ResponseEntity<String> sendEvent(@RequestBody String id_event) {
        eventosDao.senEvent(Integer.parseInt(id_event));
        return new ResponseEntity<>("Evento enviado para su revisión", HttpStatusCode.valueOf(200));
    }

    /* GET THE INFORMATION OF THE USER */
    @PostMapping("/userData")
    public AuthUserInfo userData(@RequestBody String token) {
        return authService.usuarioInfo(token);
    }
}
