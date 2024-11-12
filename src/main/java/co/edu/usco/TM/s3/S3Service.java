package co.edu.usco.TM.s3;

import co.edu.usco.TM.service.noImpl.IS3Service;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service implements IS3Service {

    private final S3Client s3client;

    @Autowired
    public S3Service(S3Client s3client) {
        this.s3client = s3client;
    }

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

}
