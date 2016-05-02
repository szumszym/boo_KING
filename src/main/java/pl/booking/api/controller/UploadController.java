package pl.booking.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pl.booking.api.service.UploadService;

/**
 * Created by Szymon on 1/7/2016.
 */
@Controller
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    UploadService uploadService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("type") String type, @RequestParam("file") MultipartFile file) {
        return uploadService.uploadFile(type, file);
    }

}
