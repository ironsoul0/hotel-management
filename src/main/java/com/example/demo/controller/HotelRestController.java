package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hotels")
public class HotelRestController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Room_typeRepository room_typeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TakesPlaceInRepository takesPlaceInRepository;

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/{city}")
    public Iterable<Hotel> hotelSearch(@PathVariable String city, Model model) {
        Iterable<Hotel> hotelsAll = hotelRepository.findAll();
        ArrayList<Hotel> resHotels = new ArrayList<>();
        for (Hotel hotel : hotelsAll) {
            if (hotel.getAddress().toLowerCase().contains(city.toLowerCase())) {
                resHotels.add(hotel);
            }
        }
        return resHotels;
    }

    private long calculatePrice(Date d1, Date d2, int bp){
        long diffInMils = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMils, TimeUnit.MILLISECONDS);
        return diff * bp;
    }

    private int addSeasonPrice(int basePrice, Date d1, Date d2, Hotel hotel) throws ParseException {
        List<TakesPlaceIn> allTakes =  takesPlaceInRepository.findAll();
        int totalprice = basePrice;
        for (TakesPlaceIn takes : allTakes) {
            if (takes.getHotel() == hotel) {
                List<String> prices = Arrays.asList(takes.getWeekdayPrice().split(","));
                Calendar start = Calendar.getInstance();
                start.setTime(d1);
                Calendar end = Calendar.getInstance();
                end.setTime(d2);

                Date startSeason = new SimpleDateFormat("yyyy-MM-dd").parse(takes.getSeason().getStartDate());
                Date endSeason = new SimpleDateFormat("yyyy-MM-dd").parse(takes.getSeason().getEndDate());
                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    if (startSeason.getTime() <= date.getTime() && date.getTime() <= endSeason.getTime()) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                        totalprice += Integer.parseInt(prices.get(dayOfWeek - 1));
                    }
                }
            }
        }
        return totalprice;
    }

    @PostMapping("/book-room")
    public String bookRoomReal(@RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2, Model model) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        User u = userRepository.findByUsername(getUsername()).get();
        Room_type rt = room_typeRepository.findById(roomtype).get();
        int price = (int) calculatePrice(date1, date2, rt.getBase_price());
        int finalPrice = addSeasonPrice(price, date1, date2, rt.getHotel_id());
        Reservation r = new Reservation(date1, date2, finalPrice, 1, u, false);
        r.setRoom_type_id(rt);
        reservationRepository.save(r);
        return "Success";
    }

    @PostMapping("/book-room-price")
    public Long bookRoom(@RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2, Model model) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        Room_type rt = room_typeRepository.findById(roomtype).get();
        long price =  calculatePrice(date1, date2, rt.getBase_price());
        int finalPrice = addSeasonPrice( (int) price, date1, date2, rt.getHotel_id());
        return (long) finalPrice;
    }

    @GetMapping("/")
    public Iterable<Hotel> mainPage(Model model) {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        return hotels;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup");
        return "about";
    }

    @RequestMapping(value = "/image/{name}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void getImage(HttpServletResponse response, @PathVariable String name) throws IOException {

        var imgFile = new ClassPathResource("image/" + name + ".jpg");

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
}
