package ua.kiev.prog;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/")
public class MyController {

    private List<Photo> photos = new ArrayList<>();

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam MultipartFile inputPhoto) {
        if (inputPhoto.isEmpty())
            throw new PhotoErrorException();

        try {
            long id = System.currentTimeMillis();
            Photo objectPhoto = new Photo(id, inputPhoto.getBytes(), inputPhoto.getOriginalFilename());
            photos.add(objectPhoto);
            model.addAttribute("photo_id", id);
            return "result";
        } catch (IOException e) {
            throw new PhotoErrorException();
        }
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        Photo photo = findBy(id);
        if (photo != null){
            photos.remove(photo);
            return "index";
        }
        throw new PhotoNotFoundException();

    }

    private ResponseEntity<byte[]> photoById(long id) {
        Photo photo = findBy(id);

        if (photo == null)
            throw new PhotoNotFoundException();

        byte[] bytes = photo.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    @RequestMapping("/list")
    public String viewList(Model model){
        model.addAttribute("photos", photos);
        return "list";
    }

    @RequestMapping("/multidel")
    public String multiDelete(Model model, @RequestParam("check") long[] ids){
        for (long id : ids){
            Photo photo = findBy(id);
            if(photo == null)
                throw new PhotoNotFoundException();
            photos.remove(photo);
        }
        return viewList(model);
    }

    private Photo findBy(long id){
        for (Photo photo : photos) {
            if (photo.getId().equals(id)){
                return photo;
            }
        }
        return null;
    }
}
