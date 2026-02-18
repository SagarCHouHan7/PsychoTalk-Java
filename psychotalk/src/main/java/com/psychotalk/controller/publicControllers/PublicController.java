package com.psychotalk.controller.publicControllers;

import com.psychotalk.dto.AccountDto.RegisterResponse;
import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.dto.expertDto.ExpertProfileDto;
import com.psychotalk.model.account.Expert;
import com.psychotalk.dto.AccountDto.LoginRequest;
import com.psychotalk.model.account.User;
import com.psychotalk.service.ExpertService;
import com.psychotalk.service.publicService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
   private AccountService accountService;
    @Autowired
    private ExpertService expertService;

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
      RegisterResponse response = accountService.registerUser(user);
        if(response == null) return new ResponseEntity<>("username Already exist" ,  HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register/expert")
    public ResponseEntity<?> registerExpert(@RequestBody Expert expert){
       RegisterResponse response = accountService.registerExpert(expert);
        if(response == null) return new ResponseEntity<>("username Already exist" ,  HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> accountLogin(@RequestBody LoginRequest req){
        return accountService.login(req);
    }

    @GetMapping("/getAllExperts")
    public ResponseEntity<PageResponseDto<ExpertProfileDto>> getAllExperts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(expertService.getAllExperts(page , size));
    }

    @GetMapping("/getExpert/{id}")
    public ResponseEntity<ExpertProfileDto> getExpertByExpertId(@PathVariable("id") Long id){
        return ResponseEntity.ok(expertService.getExpertById(id));
    }
}
