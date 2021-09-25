package com.mprog.springsecurityamigo.security;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

import static com.mprog.springsecurityamigo.security.ApplicationUserPermission.*;

@Getter
public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private final Set<ApplicationUserPermission> permissionSet;

    ApplicationUserRole(Set<ApplicationUserPermission> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
