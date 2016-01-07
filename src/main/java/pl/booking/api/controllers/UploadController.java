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

/**
 * Created by Szymon on 1/7/2016.
 */
@Controller
public class UploadController {

    @Autowired
    private Environment env;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("name") String name,
                                        @RequestParam("uploadfile") MultipartFile uploadfile) {

        String filepath;
        String filename = uploadfile.getOriginalFilename();
        try {
            if (!name.isEmpty()) {
                filename = name;
            }
            String directory = env.getProperty("booking.paths.uploadedFiles");
            Path path = createDir(Paths.get(directory));

            filepath = Paths.get(path.toString(), filename).toString();

            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(filename);
    }

    private Path createDir(Path path) throws IOException {
        if (!Files.exists(path)) {
            return Files.createDirectories(path);
        } else {
            return path;
        }
    }
}
