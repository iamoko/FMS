package org.pahappa.systems.core.settings;

import java.util.Arrays;
import java.util.HashSet;

import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.pahappa.systems.models.permissions.CustomPermissionInterpreter;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.migrations.Migration;
import org.sers.webutils.model.security.Permission;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.server.core.dao.PermissionDao;
import org.sers.webutils.server.core.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomPermissionMigrations {

	@Autowired
	PermissionDao permissionDao;

	@Autowired
	RoleDao roleDao;

	@Migration(orderNumber = 1)
	public void saveCustomPermissionsAndRoles() {
		try {
			for (Permission permission : CustomPermissionInterpreter
					.loadPermissionsFromFile(CustomPermissionConstants.class)) {
				try {
					Permission saved = permissionDao.mergeBG(permission);

					// Create a role that corresponds to this permission
					Role role = new Role();
					role.setRecordStatus(RecordStatus.ACTIVE);
					role.setDescription(permission.getDescription());
					role.setName(permission.getName());
					role.setPermissions(new HashSet<Permission>(Arrays.asList(new Permission[] { saved })));
					roleDao.mergeBG(role);
				} catch (Exception exe) {
					System.out.println("Permission already exists");
				}
			}
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
	}
}
