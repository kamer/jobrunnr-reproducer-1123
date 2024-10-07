package dev.kamer;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.InMemoryStorageProvider;

public class Application {

	public static void main(String[] args) {
		Micronaut.run(Application.class, args);
	}
}

@Singleton
class Runner implements ApplicationEventListener<ServerStartupEvent> {

	private final JobScheduler jobScheduler;

	private final SomeApi someApi;

	Runner(JobScheduler jobScheduler, SomeApi someApi) {
		this.jobScheduler = jobScheduler;
		this.someApi = someApi;
	}

	@Override
	public void onApplicationEvent(ServerStartupEvent event) {
		final String str1 = "foo";
		final String str2 = "bar";
		jobScheduler.enqueue(() -> someApi.doSomething(str1, str2));
	}
}

@Factory
class JobRunnrFactory {

	@Bean
	public JobScheduler jobScheduler() {
		return JobRunr.configure()
				.useStorageProvider(new InMemoryStorageProvider())
				.useBackgroundJobServer()
				.useDashboard(8000)
				.initialize()
				.getJobScheduler();
	}
}