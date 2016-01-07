package pl.booking.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Szymon on 1/7/2016.
 */
@Controller
@RequestMapping("/api")
public class UploadController {

    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    @Autowired
    private Environment env;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("type") String type, @RequestParam("file") MultipartFile file) {

        String filepath;
        String filename = df.format(Calendar.getInstance().getTime()) + ".jpg";
        try {
            Path path = resolvePath(type);

            filepath = Paths.get(path.toString(), filename).toString();

            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(filename);
    }

    private Path resolvePath(String type) throws IOException {
        String directory = env.getProperty("booking.paths.uploadedFiles");
        String hotel = env.getProperty("booking.paths.hotel");
        String room = env.getProperty("booking.paths.room");
        Path path = Paths.get(directory, env.getProperty("booking.paths.other"));
        if ("HOTEL".equals(type)) {
            String hotelId = "/1"; //TODO
            path = Paths.get(directory, hotel, hotelId);
        } else if ("ROOM".equals(type)) {
            String hotelId = "/1"; //TODO
            String roomId = "/1"; //TODO
            path = Paths.get(directory, hotel, hotelId, room, roomId);
        }
        return createDir(path);
    }

    private Path createDir(Path path) throws IOException {
        if (!Files.exists(path)) {
            return Files.createDirectories(path);
        } else {
            return path;
        }
    }
}
