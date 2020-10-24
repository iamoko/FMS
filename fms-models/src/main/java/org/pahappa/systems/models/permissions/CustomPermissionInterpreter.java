package org.pahappa.systems.models.permissions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.security.Permission;

public final class CustomPermissionInterpreter {

	public static List<Permission> loadPermissionsFromFile(Class<?> permissionsFile) throws OperationFailedException {
		if (permissionsFile == null)
			throw new OperationFailedException("No permissions file specified!");

		List<Permission> permissions = new ArrayList<Permission>();
		Field[] declaredFields = CustomPermissionConstants.class.getDeclaredFields();
		for (Field f : declaredFields) {

			if (f.isAnnotationPresent(CustomPermAnnotation.class)) {
				Permission permission = new Permission();
				permission.setName(f.getAnnotation(CustomPermAnnotation.class).name());
				permission.setDescription(f.getAnnotation(CustomPermAnnotation.class).description());
				permission.setDateCreated(new Date());
				permission.setDateChanged(new Date());
				permission.setRecordStatus(RecordStatus.ACTIVE);
				permissions.add(permission);
			}
		}
		return permissions;
	}
}