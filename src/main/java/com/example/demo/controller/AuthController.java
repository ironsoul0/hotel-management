package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.demo.model.*;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.*;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeskClerkRepository deskClerkRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getMobilePhone(),
                signUpRequest.getHomePhone(),
                signUpRequest.getAddress(),
                signUpRequest.getIdentificationType(),
                signUpRequest.getIdentificationNumber());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();


        if (strRoles == null) {

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);

            user.setRoles(roles);
            userRepository.save(user);

        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":

                        Random rn = new Random(); // Generate random payment
                        int range = 3001;
                        int payment = rn.nextInt(range) + 1000;
                        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll();
                        Employee deskclerk = new Employee(user.getUsername(), user.getEmail(), user.getName(),
                                user.getSurname(), signUpRequest.getPassword(), user.getMobilePhone(), payment, "manager", hotels.get(0));

//                        user.setRoles(roles);
//                        userRepository.save(user);
                        employeeRepository.save(deskclerk);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
                        Random rn = new Random(); // Generate random payment
                        int range = 3001;
                        int payment = rn.nextInt(range) + 1000;
                        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll();
                        Employee deskclerk = new Employee(user.getUsername(), user.getEmail(), user.getName(),
                                                      user.getSurname(), signUpRequest.getPassword(), user.getMobilePhone(), payment, "deskclerk", hotels.get(0));

//                        user.setRoles(roles);
//                        userRepository.save(user);
                        employeeRepository.save(deskclerk);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);

                        user.setRoles(roles);
                        userRepository.save(user);
                }
            });
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signinemployee")
    public String authenticateEmployee(@Valid @RequestBody LoginRequest loginRequest) {


        Employee employee = employeeRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        if (employee.getRole().equals("deskclerk") && employee.getPassword().equals(loginRequest.getPassword())){
            return "deskclerk " +  employee.getHotelId().toString();
        }
        else if (employee.getPassword().equals(loginRequest.getPassword())) {
            return "manager";
        }

        return "Invalid Input";
    }
}
