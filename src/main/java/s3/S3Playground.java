package s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectVersionsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectVersionsResponse;
import software.amazon.awssdk.services.s3.model.ObjectVersion;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class S3Playground {
    public static void main(String... args) {
        System.out.println("Starting application");

        //regularBucket();
        versionedBucket();

        System.out.println("Ending application");
    }

    private static void regularBucket() {
        try (S3Client s3Client = S3Client.builder().httpClientBuilder(ApacheHttpClient.builder()).build()) {
            String bucket = "afrancisco-bucket";
            String key = "key";

            System.out.println("Putting object...");
            PutObjectRequest putRequest = PutObjectRequest.builder().bucket(bucket).key(key).build();
            PutObjectResponse putResponse = s3Client.putObject(putRequest, RequestBody.fromString("content"));

            System.out.println("Getting object...");
            GetObjectRequest getRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
            byte[] getResponse = s3Client.getObjectAsBytes(getRequest).asByteArray();
            System.out.println(new String(getResponse));

            // without versioning, subsequent put requests unceremoniously overrides the objects
            // no logs or trace (by default)
            System.out.println("Overriding object...");
            putRequest = PutObjectRequest.builder().bucket(bucket).key(key).build();
            putResponse = s3Client.putObject(putRequest, RequestBody.fromString("content updated"));

            // if versioning is enabled now, subsequent puts will create a new version for each put,
            // the original object will have a versionId of "null"

            // without versioning, delete is straightforward
            System.out.println("Deleting object...");
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
            DeleteObjectResponse deleteResponse = s3Client.deleteObject(deleteRequest);
        }
    }

    private static void versionedBucket() {
        try (S3Client s3Client = S3Client.builder().httpClientBuilder(ApacheHttpClient.builder()).build()) {
            String bucket = "afrancisco-bucket-versioned";
            String key = "key";

            // the same api for put requests, except now it is not idempotent
            System.out.println("Putting object...");
            PutObjectRequest putRequest = PutObjectRequest.builder().bucket(bucket).key(key).build();
            PutObjectResponse putResponse = s3Client.putObject(putRequest, RequestBody.fromString("content"));
            String versionId = putResponse.versionId(); // put returns a versionId (ex. "APdy5.mlsoSE8UM.qW07FOwl8.2_wVL7")

            // the same api for get requests, you do not need to specify a versionId, it will fetch the latest
            System.out.println("Getting object...");
            GetObjectRequest getRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
            byte[] getResponse = s3Client.getObjectAsBytes(getRequest).asByteArray();
            System.out.println(new String(getResponse));

            // additional api for reading the specific version
            System.out.println("Reading specific version object...");
            getRequest = GetObjectRequest.builder().bucket(bucket).key(key).versionId(versionId).build();
            getResponse = s3Client.getObjectAsBytes(getRequest).asByteArray();
            System.out.println(new String(getResponse));

            // reading a null versionId
            System.out.println("Reading null version object...");
            getRequest = GetObjectRequest.builder().bucket(bucket).key(key).versionId(null).build();
            getResponse = s3Client.getObjectAsBytes(getRequest).asByteArray();
            System.out.println(new String(getResponse));

            // subsequent puts will create a new version
            System.out.println("Putting object...");
            putRequest = PutObjectRequest.builder().bucket(bucket).key(key).build();
            putResponse = s3Client.putObject(putRequest, RequestBody.fromString("content updated"));

            // reading a null versionId after update
            System.out.println("Reading null version object after updated...");
            getRequest = GetObjectRequest.builder().bucket(bucket).key(key).versionId(null).build();
            getResponse = s3Client.getObjectAsBytes(getRequest).asByteArray();
            System.out.println(new String(getResponse));

            // can look up versions of an object
            System.out.println("Listing object versions...");
            ListObjectVersionsRequest listRequest = ListObjectVersionsRequest.builder().bucket(bucket).prefix(key).maxKeys(20).build();
            ListObjectVersionsResponse listResponse = s3Client.listObjectVersions(listRequest);
            for (ObjectVersion version : listResponse.versions()) {
                System.out.println(version.key() + ", " + version.versionId() + ", " + version.isLatest());
            }

            // delete requires more steps depending on hard or soft deletes
            System.out.println("Deleting object (with delete marker, soft delete)...");
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
            DeleteObjectResponse deleteResponse = s3Client.deleteObject(deleteRequest);

            // will need to delete each version individually
            // can delete the versions in any order
            System.out.println("Deleting all object versions (hard delete)...");
            listRequest = ListObjectVersionsRequest.builder().bucket(bucket).prefix(key).maxKeys(20).build();
            listResponse = s3Client.listObjectVersions(listRequest);
            for (ObjectVersion version : listResponse.versions()) {
                deleteRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).versionId(version.versionId()).build();
                deleteResponse = s3Client.deleteObject(deleteRequest);
            }
        }
    }
}
