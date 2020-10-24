package org.pahappa.systems.core.settings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.sers.webutils.server.core.dao.MigrationDao;
import org.sers.webutils.server.core.service.migrations.MigrationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomMigrationService extends MigrationTemplate {

	@Autowired
	MigrationDao migrationDao;

	@Autowired
	SessionFactory sessionFactory;

	@CustomMigration
	@Autowired
	public CustomPermissionMigrations permissionMigrations;

	public static boolean EXECUTED_MIGRATIONS = false;
	
	/*
	 * @Autowired MailContentScheduler mailContentSchedular;
	 */

	@PostConstruct
	public void execute() {
		try {
			System.out.println("Executing migrations...");
			List<Class<?>> migrationClasses = new ArrayList<Class<?>>();
			/* migrationClasses.add(MailContentScheduler.class); */
			for (Field field : CustomMigrationService.class.getDeclaredFields()) {
				if (field.isAnnotationPresent(CustomMigration.class))
					migrationClasses.add(field.getType());
			}
			EXECUTED_MIGRATIONS = super.executeMigrations(EXECUTED_MIGRATIONS, migrationClasses, migrationDao,
					sessionFactory);
			System.out.println(String.format("Executed migrations from %d migration classes", migrationClasses.size()));
		} catch (Exception e) {
			System.out.println(String.format("Failed to execute migrations. Reason: %s", e.getMessage()));
		}
	}

	public Object getBean(Class<?> klass) throws Exception {
		for (Field field : CustomMigrationService.class.getDeclaredFields())
			if (field.getType() == klass)
				return field.get(CustomMigrationService.this);
		throw new Exception("No bean declared for class " + klass + " in this class");
	}
}
