package co.edu.usco.TM.s3;

import co.edu.usco.TM.service.noImpl.IS3Service;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * Este servicio se encarga de toda la lógica de subir archivos
 * al bucket tm-pet-buckets mediante el servicio S3 de AWS Amazon
 * Web Services, utilizando el ACCESS KEY y SECRET KEY proporcionados
 * por el IAM User kadanarpa.
 *
 * @author Kaleth Daniel Narváez Paredes
 * @see <a src="https://aws.amazon.com/es/s3/">Obten más información</a>
 */
@Service
public class S3Service implements IS3Service {

    private final S3Client s3client;

    @Autowired
    public S3Service(S3Client s3client) {
        this.s3client = s3client;
    }

    /**
     * Este método administra la subida de un archivo al bucket tomas-pet-bucket proporcionado
     * por AWS mediante las claves de acceso del IAM User kadanarpa
     *
     * @param file Archivo necesario para subir al bucket, debe pesar menos de 5MB
     * @return String URL del archivo subido al bucket, accessible desde cualquier lugar donde se introduzca gracias al a política del bucket
     * @throws IOException Excepción devuelta cuando ocurre un error al subir el archivo al bucket
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        try {

            String fileName = file.getOriginalFilename();
            String keyName = UUID.randomUUID() + "_" + fileName;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("tomas-pet-bucket")
                    .key(keyName)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();
            s3client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return s3client.utilities().getUrl(builder -> {
                builder.bucket("tomas-pet-bucket").key(keyName);
            }).toExternalForm();

        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public String deleteFile(String filePath) throws IOException {
        try {

            String fileName = filePath.replace("https://tomas-pet-bucket.s3.us-east-2.amazonaws.com/", "");
            fileName = fileName.replace("%20", " ");

            if(!this.doesObjectExist(fileName)) {
                return "File doesn't exist";
            }

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket("tomas-pet-bucket")
                    .key(fileName)
                    .build();

            s3client.deleteObject(deleteObjectRequest);

            return "File deleted successfully";

        } catch (S3Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    private boolean doesObjectExist(String objectKey) throws IOException {
        try {
            // Creamos la cabecera de la petición a realizar al bucket
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket("tomas-pet-bucket")
                    .key(objectKey)
                    .build();

            // Realizamos la petición al bucket
            s3client.headObject(headObjectRequest);

            // Retornará verdadero si no hay excepciones y en efecto el archivo existe
            return true;
        } catch(S3Exception ex) {
            if (ex.statusCode() == 404) {
                // El objeto no existe
                return false;
            } else if (ex.statusCode() == 403) {
                // Problema de permisos
                throw new RuntimeException("No tienes permisos suficientes para acceder al objeto", ex);
            } else {
                // Otros errores
                throw new RuntimeException("Error al verificar la existencia del objeto: " + ex.getMessage(), ex);
            }
        }
    }

}
