package com.example.vim.controllers;

import com.example.vim.dao.EmailServiceDao;
import com.example.vim.dao.ExampleFormDao;
import com.example.vim.models.Example_form;
import com.example.vim.utils.JWTUtil;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class ExampleFormController {

    private final ExampleFormDao exampleForm;

    private final JWTUtil jwtUtil;

    private final EmailServiceDao emailService;

    @PostMapping(value="/example_forms/api/registerUserExampleForm")
    public void registerUserExample(@RequestBody Example_form data){
        exampleForm.registerUserExample(data);
    }

    @GetMapping(value="api/getUsersData/{id}")
    public List<Example_form> getRegisterUsers(@PathVariable Long id){
        return exampleForm.getRegisterUsers(id);
    }

    @GetMapping(value="api/getConfirmedUsersData/{id}")
    public List<Example_form> getConfirmedUsers(@PathVariable Long id){
        return exampleForm.getConfirmedUsersData(id);
    }

    // Validate the information of the user
    @PostMapping(value="api/validateRegisterUser")
    public void validateRegisterUser(@RequestBody Long folio){
        exampleForm.validateRegisterUser(folio);
    }

    // Refuse the information of the user
    @PostMapping(value="api/refuseRegisterUser")
    public void refuseRegisterUser(@RequestBody Long id){
        exampleForm.refuseRegisterUser(id);
    }

    // Getting and returning values to create the Qrcode
    @PostMapping(value="/example_forms/api/dataQrCode")
    public Example_form dataQrCode(@RequestBody String data){
        JSONObject obj = new JSONObject(data);
        String folio = obj.get("folio").toString();
        String email = obj.getString("email").toString();
        Example_form form = exampleForm.dataForQrCode(folio, email);

        return form;
    }

    @GetMapping(value = "api/sendMailTest")
    public void sendMailTest(){
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Noel Galindo");
        emailService.sendMail(model);
    }
}
