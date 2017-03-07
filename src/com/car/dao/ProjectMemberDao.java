package com.car.dao;

import com.car.models.ProjectMember;

public interface ProjectMemberDao {
	boolean addProjectMember(ProjectMember projectMember);
	boolean deleteProjectMember(Long userId,Long projectId);
	ProjectMember getByUserIdProjectId(Long userId,Long projectId);

}
