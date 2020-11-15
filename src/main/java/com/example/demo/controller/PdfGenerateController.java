package com.example.demo.controller;

import com.example.demo.model.Reservation;
import com.example.demo.model.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.StyleConstants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class PdfGenerateController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private User getUser(){
        return userRepository.findByUsername(getUsername()).get();
    }

    private String getFilename(Reservation r){
        long id = r.getId();
        String username = getUsername();
        return username + "+" + Long.toString(id) + ".pdf";
    }

    private Text getAsHelvetica(String text) throws IOException {
        Text t = new Text(text);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        t.setFont(font);
        return t;
    }

    private Text getAsHelveticaBold(String text) throws IOException {
        Text t = new Text(text);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        t.setFont(font);
        return t;
    }

    private String getFullname(){
        String username = getUsername();
        User u = userRepository.findByUsername(username).get();
        String res = u.getName() + " " + u.getSurname();
        return res;
    }

    private Document fillInDetailsIntoPdfDocument(PdfDocument doc, Reservation r) throws IOException {
        // new page
        Document document = new Document(doc);

        Paragraph p = new Paragraph();
        Text intro = getAsHelvetica("This is an autogenerated PDF file. It includes information on reservation.\n");
        p.add(intro);
        p.add("\n");

        Text owner_template = getAsHelvetica("The owner of the reservation: ");
        p.add(owner_template);

        Text owner = getAsHelveticaBold(getFullname());
        p.add(owner);
        p.add("\n");

        User u = getUser();

        Text username_template = getAsHelvetica("Owner's username: ");
        p.add(username_template);

        Text username = getAsHelveticaBold(getUsername());
        p.add(username);
        p.add("\n");

        Text phone_number_template = getAsHelvetica("Owner's phone number: ");
        p.add(phone_number_template);

        Text phone = getAsHelveticaBold(u.getMobilePhone());
        p.add(phone);
        p.add("\n");

        Text email_template = getAsHelvetica("Owner's email: ");
        p.add(email_template);

        Text email = getAsHelveticaBold(u.getEmail());
        p.add(email);
        p.add("\n");

        Text hotel_template = getAsHelvetica("Hotel: ");
        p.add(hotel_template);

        Text hotel = getAsHelveticaBold(r.getRoom_type_id().getHotel_id().getName());
        p.add(hotel);
        p.add("\n");

        Text hotel_address_template = getAsHelvetica("Hotel's address: ");
        p.add(hotel_address_template);

        Text hotel_address = getAsHelveticaBold(r.getRoom_type_id().getHotel_id().getAddress());
        p.add(hotel_address);
        p.add("\n");

        Text hotel_phone_template = getAsHelvetica("Hotel's phone number: ");
        p.add(hotel_phone_template);

        Text hotel_phone = getAsHelveticaBold(r.getRoom_type_id().getHotel_id().getPhone());
        p.add(hotel_phone);
        p.add("\n");

        Text hotel_room_type_template = getAsHelvetica("Guest's reserved room type: ");
        p.add(hotel_room_type_template);

        Text hotel_room_type = getAsHelveticaBold(r.getRoom_type_id().getName());
        p.add(hotel_room_type);
        p.add("\n");

        Text checkin_date_template = getAsHelvetica("Check-in date: ");
        p.add(checkin_date_template);

        Text checkin_date = getAsHelveticaBold(r.getCheckinDateToString());
        p.add(checkin_date);
        p.add("\n");

        Text checkout_date_template = getAsHelvetica("Check-out date: ");
        p.add(checkout_date_template);

        Text checkout_date = getAsHelveticaBold(r.getCheckoutDateToString());
        p.add(checkout_date);
        p.add("\n");

        Text price_template = getAsHelvetica("Price: ");
        p.add(price_template);

        Text price = getAsHelveticaBold(Integer.toString(r.getPrepaid_price()) + " KZT");
        p.add(price);
        p.add("\n\n\n\n\n\n\nPlease enjoy your stay with us!");

        document.add(p);
        return document;
    }

    private void generatePdf(Reservation r) throws IOException {
        String filename = getFilename(r);
        PdfWriter writer = new PdfWriter(filename);
        PdfDocument document = new PdfDocument(writer);
        Document doc2 = fillInDetailsIntoPdfDocument(document, r);
        doc2.close();
    }

    private byte[] getPdfContentsAsByte(Reservation r) throws IOException {
        String filename = getFilename(r);
        Path path = Paths.get(filename);
        byte[] res = Files.readAllBytes(path);
        return res;
    }

    private Document fillInErrorDetailsIntoPdfDocument(PdfDocument pdf) throws IOException {
        Document document = new Document(pdf);
        Text error = getAsHelveticaBold("You don't have access to this reservation or the reservation doesn't exist!");
        Paragraph p = new Paragraph();
        p.add(error);
        document.add(p);
        return document;
    }

    private void generateErrorPdf() throws IOException {
        String filename = "error.pdf";
        PdfWriter writer = new PdfWriter(filename);
        PdfDocument document = new PdfDocument(writer);
        Document doc2 = fillInErrorDetailsIntoPdfDocument(document);
        doc2.close();
    }

    private byte[] getErrorPdf() throws IOException {
        generateErrorPdf();
        String filename = "error.pdf";
        Path path = Paths.get(filename);
        byte[] res = Files.readAllBytes(path);
        return res;
    }

    @RequestMapping(value="/generate-pdf/{id}", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> servePdfInResponse(@PathVariable Long id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        byte[] contents;

        String filename;

        if(!reservationRepository.existsById(id)){
            contents = getErrorPdf();
            filename = "error.pdf";
        } else {
            Reservation r = reservationRepository.findById(id).get();
            if (!r.getUser_id().getUsername().equals(getUsername())) {
                contents = getErrorPdf();
                filename = "error.pdf";
            } else {
                generatePdf(r);
                contents = getPdfContentsAsByte(r);
                filename = getFilename(r);
            }
        }

        headers.add("content-disposition", "inline;filename=" + filename);
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
}
