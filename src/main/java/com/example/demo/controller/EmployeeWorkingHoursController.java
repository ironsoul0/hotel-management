package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/manager/")
public class EmployeeWorkingHoursController {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private TakesPlaceInRepository takesPlaceInRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private EmployeeWorkingHoursRepository employeeWorkingHoursRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/allemployees")
    private List<Employee> showAllEmployees() {
        List<Employee> notmanagers = new ArrayList<>();
        List<Employee> allemployees = (List<Employee>) employeeRepository.findAll();
        for (Employee employee : allemployees) {
            if (!employee.getRole().toLowerCase().equals("manager")) {
                notmanagers.add(employee);
            }
        }

        return (List<Employee>) notmanagers;
    }

    @GetMapping("/schedules")
    private List<EmployeeWorkingHours> showAllschedules() {
        return (List<EmployeeWorkingHours>) employeeWorkingHoursRepository.findAll();
    }

    @GetMapping("/schedule")
    private Set<EmployeeWorkingHours> showEmployeeschedules(@RequestParam Long employeeid ) {
        Employee employee = employeeRepository.findById(employeeid).orElseThrow();
        return employee.getEmployeeWorkingHours();
    }

    @GetMapping("/schedule/{id}/edit")
    public EmployeeWorkingHours scheduleEdit(@PathVariable(value = "id") long id) {
        EmployeeWorkingHours employeeWorkingHours = employeeWorkingHoursRepository.findById(id).orElseThrow();
        return employeeWorkingHours;
    }

    @PostMapping("/schedule/{id}/edit")
    public String schedulePostUpdate(@PathVariable long id, @RequestParam String date_work,
                                     @RequestParam String time_start, @RequestParam String time_end,
                                     @RequestParam(required = false, defaultValue = "0") int total_Payment, @RequestParam int total_hours) throws ParseException {
        EmployeeWorkingHours schedule = employeeWorkingHoursRepository.findById(id).orElseThrow();
        Employee employee = schedule.getEmployee();
        schedule.setTime_start(time_start);
        schedule.setTime_end(time_end);
        schedule.setTotal_hours(total_hours);
        if (total_Payment == 0) {
            schedule.setTotal_Payment(employee.getPayment() * total_hours);
        }
        else {
            schedule.setTotal_Payment(total_Payment);
        }
        schedule.setDate_work(new SimpleDateFormat("yyyy-MM-dd").parse(date_work));
        employeeWorkingHoursRepository.save(schedule);
        return "Changes done!";
    }

//    @GetMapping("/schedule/add")
//    public String hotelsAdd(Model model) {
//        return "hotels-add";
//    }

    @PostMapping("/schedule/add") // works
    public String schedulePostAdd(@RequestParam Long employeeId, @RequestParam String date_work, @RequestParam String time_start, @RequestParam String time_end,
                                  @RequestParam(required = false, defaultValue = "0") int total_Payment, @RequestParam int total_hours) throws ParseException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        EmployeeWorkingHours schedule;
        if (total_Payment == 0)
            schedule = new EmployeeWorkingHours(employee, time_start, time_end, new SimpleDateFormat("yyyy-MM-dd").parse(date_work), total_hours);
        else {
            schedule = new EmployeeWorkingHours(employee, time_start, time_end,
                    total_Payment, new SimpleDateFormat("yyyy-MM-dd").parse(date_work), total_hours);
        }
        employeeWorkingHoursRepository.save(schedule);
        return "Schedule registered successfully!";
    }

    @PostMapping("/schedule/{id}/delete")
    public String scheduleDelete(@PathVariable(value = "id") long id) {
        EmployeeWorkingHours schedule = employeeWorkingHoursRepository.findById(id).orElseThrow();
        employeeWorkingHoursRepository.delete(schedule);
        return "Schedule Deleted";
    }

    public Set <Long> parseHotelIDs (String hotelIds) {

        Set <Long> hotels = new HashSet<>();
        StringBuilder t = new StringBuilder();

        for (int i = 0; i < hotelIds.length(); ++i) {


            if (hotelIds.charAt(i) == ',') {
                hotels.add(Long.parseLong(t.toString()));
                t = new StringBuilder();
            } else {
                t.append(hotelIds.charAt(i));
            }
        }
        hotels.add(Long.parseLong(t.toString()));

        return hotels;
    }

    @PostMapping("/seasons/addSeason") // return id of season, which is name of season
    public String createSeason (
            @RequestParam String hotelIds,
            @RequestParam String name,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String weekdayPrice
    ) {

        Season season = new Season(name, startDate, endDate);
        Set <TakesPlaceIn> takesPlaceIns = new HashSet<>();
        Set <Long> hotelIDs = parseHotelIDs(hotelIds);

        for (Long i : hotelIDs) {

            TakesPlaceIn t = new TakesPlaceIn(weekdayPrice);
            Hotel h = hotelRepository.findById(i).orElseThrow();

            t.setHotel(h);
            t.setSeason(season);

            Set <TakesPlaceIn> st = h.getTakesPlaceIns();
            st.add(t);
            h.setTakesPlaceIns(st);

            takesPlaceInRepository.save(t);
            hotelRepository.save(h);

            takesPlaceIns.add(t);
        }

        season.setTakesPlaceIns(takesPlaceIns);
        seasonRepository.save(season);

        try { sendNotificationOnNewSeason(name); }
        catch (MessagingException e) { e.printStackTrace(); }

        return name;
    }

    @GetMapping("/seasons")
    public Set<Season> showAllSeasons () {

        List <Season> seasons = seasonRepository.findAll();

        return new HashSet<>(seasons);
    }

    @PostMapping("/seasons/{id}/delete")
    public void cancelSeason (@PathVariable Long id) {

        List <TakesPlaceIn> ts = takesPlaceInRepository.findAll();
        for (TakesPlaceIn t : ts) {

            if (t.getSeason().getId().equals(id)) {

                takesPlaceInRepository.deleteById(t.getId());
            }
        }

        List <Season> seasons = seasonRepository.findAll();
        for (Season season : seasons) {

            if (season.getId().equals(id)) {

                seasonRepository.deleteById(id);

                break;
            }
        }

    }

    public void sendNotificationOnNewSeason (
            String seasonName
    ) throws MessagingException {

        Season season = seasonRepository.findBySeasonName(seasonName).orElseThrow();

        Set <TakesPlaceIn> takesPlaceIns = season.getTakesPlaceIns();
        Set <Hotel> hotels = new HashSet<>();
        for (TakesPlaceIn t : takesPlaceIns) { hotels.add(t.getHotel()); }

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");

        String email = "manahotel3system@gmail.com";

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(email, "98765hotel");

            }

        });

        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));

        List<Address> as = new ArrayList<>();

        for (Hotel h : hotels) {

            Set <Employee> employees = h.getEmployees();

            for (Employee e : employees) {

                as.add(new InternetAddress(e.getEmail()));
            }

            Set <Room_type> room_types = h.getRoom_types();

            for (Room_type rt : room_types) {

                Set <Reservation> reservations = rt.getReservations();

                for (Reservation r : reservations) {

                    as.add(new InternetAddress(r.getUser_id().getEmail()));
                }
            }
        }

        message.addRecipients(Message.RecipientType.TO, (Address[]) as.toArray());
        //message.addRecipient(Message.RecipientType.TO, new InternetAddress("rustem.turtayev@nu.edu.kz"));
        message.setSubject("New season!");
        message.setText("Wow! Here is the new season coming next Sunday from Frogs!");

        Transport.send(message);
    }
}








