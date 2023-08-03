package de.wlinc.api.util;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
public class PermissionChecker {

    public void hasUserOneOfRoles(Jwt jwt, String[] roleNames){
        var roles = (JSONArray) jwt.getClaimAsMap("realm_access").get("roles");
        if(roles.contains("admin")){
            return;
        }
        if (roles.stream().anyMatch(role -> {
            for (var permissionRole : roleNames) {
                if (permissionRole.equals(role)) {
                    return true;
                }
            }
            return false;
        })) {
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied. You don't have the required role(s): " + Arrays.toString(roleNames));
        }
    }
}
