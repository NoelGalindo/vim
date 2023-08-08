package com.example.vim.controllers;

import com.example.vim.auth.AuthService;
import com.example.vim.auth.AuthUserInfo;
import com.example.vim.dao.Solicitud_formulariosDao;
import com.example.vim.models.Formularios_enviados;
import com.example.vim.models.Solicitud_formularios;
import com.example.vim.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitud_form/")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class Solicitud_formulariosController {

    // Inyeccion de dependenciass
    private final AuthService authService;

    private final Solicitud_formulariosDao solicitudDao; // Se crea sobre la interface, no la implementación.

    private final JWTUtil jwtUtil;


    // Mostrar formularios activos
    @PostMapping(value = "api/FormulariosActivos")
    public List<Solicitud_formularios> getFormularios (@RequestBody String token){
        return solicitudDao.getFormularios(authService.usuarioUsername(token));
    }

    // Eliminar formulario
    @DeleteMapping(value = "api/eliminarFormulario/{id}") // Se establece que es para eliminar valore con deleteMapping remplazando method = RequestMethod.DELETE
    public void eliminarFormulario(@PathVariable Long id){ // Se agrega el path variable para pasar la variable
        solicitudDao.eliminarFormulario(id); // Llama a la funcion eliminar
    }

    // Agregar el formulario a la base de datos
    @PostMapping(value = "api/agregarForm")
    public void agregarFormulario(@RequestBody Solicitud_formularios form){ // Obtiene el valor del
        solicitudDao.agregarFormulario(form);
    }

    // Obtener la información de un formulario
    @GetMapping(value = "api/getFormData/{id}")
    public Solicitud_formularios getFormData(@PathVariable Long id){
        return solicitudDao.getFormData(id);
    }

    @PutMapping(value = "api/changeForm")
    public void editForm(@RequestBody Solicitud_formularios form){
        solicitudDao.changeForm(form);
    }

    @PostMapping(value="api/sendForm")
    public void sendForm(@RequestBody Formularios_enviados form){
        solicitudDao.changeStatusForm(form, "En revisión");
    }

    // Obtener las credenciales del usuario al cargar la pagina, para validar al usuario
    @PostMapping("/userData")
    public AuthUserInfo userData(@RequestBody String token){
        return authService.usuarioInfo(token);
    }
}

