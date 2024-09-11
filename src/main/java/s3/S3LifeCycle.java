package s3;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ExpirationStatus;
import software.amazon.awssdk.services.s3.model.LifecycleRule;
import software.amazon.awssdk.services.s3.model.ListObjectVersionsRequest;
import software.amazon.awssdk.services.s3.model.ObjectVersion;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3LifeCycle {

    static {
        System.setProperty("aws.accessKeyId", "");
        System.setProperty("aws.secretAccessKey", "");
        System.setProperty("aws.sessionToken", "");
    }

    private static final String BUCKET_NAME = "my-bucket";
    private static final Random RANDOM = new Random();
    private static final int NUM_MESSAGES = 1000;
    private static final int MAX_KEYS = 100;
    private static final int MAX_MESSAGE_SIZE = 256;
    private static final int MAX_MESSAGE_UPDATES = 3;

    public static void main(String[] args) {
        System.out.println("Starting...");

        try (var client = S3Client.builder()
                                  .httpClientBuilder(ApacheHttpClient.builder())
                                  .build()) {
            //putObjects(client);

            //deleteObjects(client);

            //addLifeCycle(client);

            //deleteLifeCycle(client);

            printObjects(client);
        }

        System.out.println("The End!");
    }

    private static void putObjects(S3Client client) {
        System.out.println("Putting Objects...");
        var timer = System.currentTimeMillis();
        var count = 0;
        for (int i = 0; i < NUM_MESSAGES; i++) {
            var message = generateMessage();

            System.out.println("Putting: " + message.key + ", size=" + message.data.length);
            var putRequest = PutObjectRequest.builder().bucket(BUCKET_NAME).key(message.key).build();
            client.putObject(putRequest, RequestBody.fromBytes(message.data));
            count++;

            if (RANDOM.nextBoolean()) {
                for (int j = 0; j < RANDOM.nextInt(MAX_MESSAGE_UPDATES); j++) {
                    var update = generateMessage();
                    System.out.println("Updating: " + message.key + ", size=" + update.data.length);
                    putRequest = PutObjectRequest.builder().bucket(BUCKET_NAME).key(message.key).build();
                    client.putObject(putRequest, RequestBody.fromBytes(update.data));
                    count++;
                }
            }
        }
        System.out.println("Putting Objects Done (" + count + " in " + (System.currentTimeMillis() - timer) + " ms)");
    }

    private static Message generateMessage() {
        var key = UUID.randomUUID();
        var data = RandomStringUtils.randomAlphanumeric(RANDOM.nextInt(1, MAX_MESSAGE_SIZE));
        return new Message(key.toString(), data.getBytes(StandardCharsets.UTF_8));
    }

    private static void printObjects(S3Client client) {
        String keyMarker = null;
        String versionIdMarker = null;
        var count = 0;
        while (true) {
            var listRequest = ListObjectVersionsRequest.builder().bucket(BUCKET_NAME).maxKeys(MAX_KEYS).keyMarker(keyMarker).versionIdMarker(versionIdMarker).build();
            var listResponse = client.listObjectVersions(listRequest);
            if (listResponse.isTruncated()) {
                keyMarker = listResponse.nextKeyMarker();
                versionIdMarker = listResponse.nextVersionIdMarker();
            }
            else {
                break;
            }
            for (ObjectVersion version : listResponse.versions()) {
                System.out.println("Object: " + version.key() + ", " + version.versionId() + ", " + version.isLatest());
                count++;
            }
        }
        System.out.println("Total: " + count);
    }

    private static void deleteObjects(S3Client client) {
        System.out.println("Deleting Objects...");
        var timer = System.currentTimeMillis();
        String keyMarker = null;
        String versionIdMarker = null;
        var count = 0;
        while (true) {
            var listRequest = ListObjectVersionsRequest.builder().bucket(BUCKET_NAME).maxKeys(MAX_KEYS).keyMarker(keyMarker).versionIdMarker(versionIdMarker).build();
            var listResponse = client.listObjectVersions(listRequest);
            if (listResponse.isTruncated()) {
                keyMarker = listResponse.nextKeyMarker();
                versionIdMarker = listResponse.nextVersionIdMarker();
            }
            else {
                break;
            }
            for (ObjectVersion version : listResponse.versions()) {
                System.out.println("Deleting: " + version.key() + ", " + version.versionId());
                var deleteRequest = DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(version.key()).versionId(version.versionId()).build();
                client.deleteObject(deleteRequest);
                count++;
            }
        }
        System.out.println("Deleting Objects Done (" + count + " in " + (System.currentTimeMillis() - timer) + " ms)");
    }

    private static void addLifeCycle(S3Client client) {
        var expireObjectsRule = LifecycleRule.builder()
                                             .id("expire-objects-rule")
                                             .filter(f -> f.prefix(""))
                                             .expiration(e -> e.days(1))
                                             .status(ExpirationStatus.ENABLED)
                                             .build();

        var deleteObjectsRule = LifecycleRule.builder()
                                             .id("delete-objects-rule")
                                             .filter(f -> f.prefix(""))
                                             .noncurrentVersionExpiration(e -> e.noncurrentDays(1).build())
                                             .status(ExpirationStatus.ENABLED)
                                             .build();

        var deleteMarkersRule = LifecycleRule.builder()
                                             .id("delete-markers-rule")
                                             .filter(f -> f.prefix(""))
                                             .expiration(e -> e.expiredObjectDeleteMarker(true))
                                             .abortIncompleteMultipartUpload(a -> a.daysAfterInitiation(1))
                                             .status(ExpirationStatus.ENABLED)
                                             .build();

        final Map<String, LifecycleRule> rules = new HashMap<>();
        try {
            var configuration = client.getBucketLifecycleConfiguration(r -> r.bucket(BUCKET_NAME));
            if (configuration.hasRules()) {
                rules.putAll(configuration.rules().stream().collect(Collectors.toMap(LifecycleRule::id, Function.identity())));
            }
        }
        catch (S3Exception e) {
            // ignore
        }

        rules.put(expireObjectsRule.id(), expireObjectsRule);
        rules.put(deleteObjectsRule.id(), deleteObjectsRule);
        rules.put(deleteMarkersRule.id(), deleteMarkersRule);

        client.putBucketLifecycleConfiguration(r -> r.bucket(BUCKET_NAME)
                                                     .lifecycleConfiguration(c -> c.rules(rules.values())));
    }

    private static void deleteLifeCycle(S3Client client) {
        client.deleteBucketLifecycle(r -> r.bucket(BUCKET_NAME));
    }

    private record Message(String key, byte[] data) { }
}
