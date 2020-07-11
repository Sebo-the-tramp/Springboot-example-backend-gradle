package it.refill.backend.controllers.general;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.refill.backend.repository.users.SupplierRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/image")
public class ImageController {
    @Autowired
    SupplierRepository supplierRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uplaodImage(@RequestParam("imageFile") MultipartFile file, HttpServletRequest req)
            throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);

        try {
            UUID fileName = UUID.randomUUID();
			String path = saveImage(file, (Long) req.getAttribute("user_id"), fileName);
			System.out.println(path);
            return new ResponseEntity<>(path, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }
    
    private String saveImage(MultipartFile file, Long supplierId, UUID randomName) throws Exception {
		String folder = System.getProperty("user.dir");
		
		System.out.println(folder);

		byte[] bytes = file.getBytes();
		
		String relativePath = "/bin/main/static/images/uploaded/";

		String imageName = randomName.toString() + "." + file.getContentType().substring(file.getContentType().indexOf("/")+1);

        Path path = Paths.get(folder + relativePath + imageName);

		Files.write(path, bytes);
				
		//return the name of the image to be saved in the database
		return imageName;
    }

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
}