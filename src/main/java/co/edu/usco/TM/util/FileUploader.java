package co.edu.usco.TM.util;

import co.edu.usco.TM.persistence.entity.shared.ImageEntity;
import co.edu.usco.TM.persistence.entity.user.Veterinarian;
import co.edu.usco.TM.s3.S3Service;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@NoArgsConstructor
public class FileUploader {

    @Autowired
    S3Service s3Service;

    public void uploadImage(ImageEntity entity, MultipartFile image, boolean deleteImg) throws IOException {

        boolean haveImgToUpload = (image != null && !image.isEmpty());
        boolean haveImg = (entity.getImgURL() != null);

        if (!haveImg && haveImgToUpload) { // Crear Imágen
            entity.setImgURL(s3Service.uploadFile(image));

        } else if (haveImg && haveImgToUpload) { // Actualizar Imágen
            s3Service.deleteFile(entity.getImgURL());
            entity.setImgURL(s3Service.uploadFile(image));


        } else if (haveImg && deleteImg && !haveImgToUpload) { // Eliminar Imágen
            s3Service.deleteFile(entity.getImgURL());
            entity.setImgURL(null);
        }
    }

    public void uploadDegree(Veterinarian vet, MultipartFile degree) throws IOException {
        if (degree != null && !degree.isEmpty()) {
            if (vet.getDegreeURL() != null) {
                s3Service.deleteFile(vet.getDegreeURL());
            }
            vet.setDegreeURL(s3Service.uploadFile(degree));
        }
    }

}
