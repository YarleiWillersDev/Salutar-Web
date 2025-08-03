package br.com.isiflix.salutar.service.upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadServiceImplementacao implements IUploadService{

    @Override
    public String uploadFile(MultipartFile arquivo) {
        try {
            System.out.println("DEBUG - Realizando Upload do arquivo: " + arquivo.getOriginalFilename());
            String pastaDestino = "C:\\Users\\Yarlei\\Desktop\\Angular_Project\\salutar-front\\src\\assets\\media";
            Path path = Paths.get(pastaDestino + File.separator + arquivo.getOriginalFilename());
            Files.copy(arquivo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return arquivo.getOriginalFilename();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
