package de.wlinc.api;

import com.posthog.java.PostHog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
		private static final String POSTHOG_API_KEY = "<ph_project_api_key>";
		private static final String POSTHOG_HOST = "<ph_instance_address>";

		public static final PostHog posthog = new PostHog.Builder(POSTHOG_API_KEY).host(POSTHOG_HOST).build();
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		posthog.shutdown();
	}

}
