package com.example.oderapp.utils;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AccessToken {
    private static final String firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() {
        try {
            String jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"oderapp-f3e3f\",\n" +
                    "  \"private_key_id\": \"5c0184d73cbe05e24b1f120f2b2a9be23dbafd53\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDUHIuse3vCwdUV\\nHQ2n/walmy2fazw7CJafJ+DtlvBQildCIh6Tvy+4XhvDvqxnnH+dqAsYuTPkpuSa\\nb3C1mL2pje7vEhcFOWaGfGoLU1hGgjx+jXEPlL7O8edaAr5KD2k8I6WOgokS2uCZ\\nMQLyNosEHL9YBnekYf9ETUafTAc6KP25JdSEX1xDwhcSLd6scH2fqLIOBVKMYsc7\\nRNebjKiTGFWjL9StbxFWrSSikpZudjGBvLVczQU7ix6UviERyWRb5Cu0DcpWHdbU\\nWy8tC3+q19tsXlKIgBEtWCuraL05N55jkGfzrYpLbMoDJazxWjHhu99RSt+Bba/N\\n62Q/PHbTAgMBAAECggEAA9iQQcYSNhnpbd+s4tBrEIkLq6A4PQ77tPjcouibN+Ki\\nQ802X7hv0/h4D88LwjjItmH/I+lSKXTk06LUpaSwDHx9ChgT+ccskDpLE/FLBvao\\n94rdM3EF53h9/0uDo5Hu9wuwa0qVOkj+TIv8Df2mL9XNNGCw16njnoHsl2PVN7bR\\nG5Voe8V9nCZ60F/VpRyVAEZk5c7xgsZR4qRQS4E7iEWnTySKnJyZ3xgX0KB9/sEA\\nFaUJcWHYhqJD2uaBgJb9xhPxSh0TGePDdZ5HcOVK8GJwfrnPE60qTtWDcGOZgru3\\nfmgOMyw/Z6G3w4E0cbZE5UFx98pRCJjZE/mebja86QKBgQDqH4Vn1zJuuVBBZamB\\no9vpvaXWIRZORZtBMQEbyGMaXNvyEduCQEIOjJHQitCLZ/19OImHbJUmPA2qSTBB\\nvcMOw+4MQiDR1DFWbVoGOGplJITZ7ywYNqCMxHF9V/vCT3caFaxmvcD9NZMFPuVu\\nS1Qs0iubp5v1PeJ7jXoRgGKUOQKBgQDn7nuMoTP0fZBejIPBi4PmfUwBN3GANi8y\\n7xk+Ae1Vca1YTSI4zMhXoNvcF4T7/+bY5mW5D7eUGJcM2evYEawpXyXkO1IoJcpY\\nP9oWzMnQKW8PSMfChS4s7Bh+vGt43sJMkQffgrzLVCCwv3mgMX4UVGhL13dfASaq\\noK14nJqbawKBgGEGnyrhrCzm77Oqhg0L819zEP8kxzG49xaVUHhLG4cZZK11aRsZ\\ntkaBSwy5+qNLfWofvB87iLRHvwILQsx78BJ2cHj0DtXmsmu29dAOIUm5ULVgwU1n\\nljHAV6gyhkuI0zskMEaD7S8CvOM3pa2H2mKY1ymy1JdWBj5d63DFXBVBAoGBANN8\\ns0xY8ndOyAZvOvWRsoTKXDP/yo+O2Rsoy2uvdisjrV2HeF0y3ryJAfJ/v9PKVUYT\\nUU6e9lck1Rfy3wv3kXVoPbUQIqpZS1mNX+DKfHxES/Ez/HNz3dc4sl1ekuF0vtfy\\np6s4G8+MtwJ0lwNsHfuLvE7r+v22nvuX6JbCtnhhAoGAQIDLoLA1Qb10qXNqCz15\\npOa9GH3W3WM9djssnYTekDSC0FanBtOYi/0Mxx0Fby+HudttrsJ0cj9phhk8UkyT\\nqLY5a/BlcAEXC8SFLZ06r3SnHJvZBxrSr8Ec+ijGQ9/aj2GSFiSN6YFve1isaVlc\\n0rEKNcHc5YdmgKaR3xa/OQU=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-fbsvc@oderapp-f3e3f.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"112364240102271248218\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fbsvc%40oderapp-f3e3f.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}\n";
            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(firebaseMessagingScope);
            googleCredentials.refresh();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception e) {
            Log.e("AccessToken", "getAccessToken: " + e.getLocalizedMessage());
            return null;
        }
    }
}